package ru.akopian.susano.input.http

import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomNumeric
import org.slf4j.LoggerFactory.getLogger
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.akopian.susano.config.CacheConfiguration.Companion.PROFILE_LIKE_CACHE_NAME
import ru.akopian.susano.model.dto.ProfileDto
import ru.akopian.susano.model.mapper.ProfileMapper.Companion.fillProfile
import ru.akopian.susano.model.mapper.ProfileMapper.Companion.mapProfile
import ru.akopian.susano.model.mapper.ProfileMapper.Companion.newProfile
import ru.akopian.susano.model.mongo.Profile
import ru.akopian.susano.output.exception.NotFoundException
import ru.akopian.susano.persistance.mongo.ProfileRepository

@RestController
@RequestMapping("/profile")
@EnableScheduling
class ProfileController(
    private val profileRepository: ProfileRepository
) {

    companion object {
        private val log = getLogger(ProfileController::class.java)
    }

    @GetMapping
    fun find(@RequestParam id: String): Mono<ProfileDto> =
        profileRepository.findById(id)
            .map { mapProfile(it) }
            .switchIfEmpty(Mono.error(NotFoundException("not found profile with id = $id")))

    @GetMapping("/like")
    @Cacheable(key = "#like", cacheNames = [PROFILE_LIKE_CACHE_NAME])
    fun findByNameLike(@RequestParam like: String): Mono<MutableList<ProfileDto>> {
        log.info("Mongo was used!")
        return profileRepository.findByNameContaining(like, PageRequest.of(0, 50))
            .map { mapProfile(it) }.collectList().
//            //кажется для веблафкса это все ломает, потом убрать
    }

    @PostMapping
    fun create(@RequestBody profileDto: ProfileDto): Mono<ProfileDto> =
        profileRepository.save(newProfile(profileDto)).map { mapProfile(it) }

    @PutMapping("/{id}")
    fun update(@RequestBody dto: ProfileDto, @PathVariable id: String): Mono<ProfileDto> =
        profileRepository.findById(id)
            .flatMap { profileRepository.save(fillProfile(it, dto)) }
            .map { mapProfile(it) }
            .switchIfEmpty(Mono.error(NotFoundException("not found profile with id ${dto.id}")))

    @DeleteMapping("/all")
    fun deleteAll() =
        profileRepository.deleteAll()


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) =
        profileRepository.deleteById(id)

    @PostMapping("/createMany")
    fun create50_000_profiles() =
        Flux.range(0, 50_000).flatMap {
            profileRepository.save(Profile(name = randomAlphabetic(12), phone = randomNumeric(10)))
        }.subscribeOn(Schedulers.boundedElastic())


}
