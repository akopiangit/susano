package ru.akopian.susano.model.mongo

import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("profile")
data class Profile(
    @Id
    @NotNull
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    @Indexed
    var phone: String? = null
) {
    override fun toString(): String {
        return "Profile(id=$id, name=$name, phone=$phone)"
    }

}