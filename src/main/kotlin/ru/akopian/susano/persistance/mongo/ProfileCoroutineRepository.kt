package ru.akopian.susano.persistance.mongo

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import ru.akopian.susano.model.mongo.Profile

@Repository
class ProfileCoroutineRepository(
    private val database: MongoDatabase
) {
    suspend fun findOne(id: String): Profile {
        val collection = database.getCollection<Profile>("profile")
        return collection.withDocumentClass<Profile>().find(eq("_id", id)).first()
    }
}