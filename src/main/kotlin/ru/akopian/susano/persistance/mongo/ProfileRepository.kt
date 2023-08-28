package ru.akopian.susano.persistance.mongo

import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.akopian.susano.model.mongo.Profile

@Repository
interface ProfileRepository : ReactiveMongoRepository<Profile, String> {
    fun findByNameContaining(like: String, page: PageRequest) : Flux<Profile>
}
