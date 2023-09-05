package ru.akopian.susano.config

import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig(
    @Value("\${spring.data.mongodb.uri}")
    val url: String
) {
    @Bean
    fun getConnection(): MongoDatabase {
        val mongoClient = MongoClient.create(url)
        val pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(
                PojoCodecProvider.builder().automatic(true).build()
            )
        )
        return mongoClient.getDatabase("susano").withCodecRegistry(pojoCodecRegistry)
    }
}