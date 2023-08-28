package ru.akopian.susano.model.mapper

import ru.akopian.susano.model.dto.ProfileDto
import ru.akopian.susano.model.mongo.Profile

class ProfileMapper {

    companion object {

        fun mapProfile(profile: Profile) =
            ProfileDto(
                profile.id,
                profile.name,
                profile.phone
            )

        fun newProfile(profileDto: ProfileDto) =
            profileDto.let { Profile(
                phone = it.phone,
                name = it.name
            ) }

        fun fillProfile(profile: Profile, profileDto: ProfileDto) : Profile =
            profile.apply {
                phone = profileDto.phone
                name = profileDto.name
            }

    }
}