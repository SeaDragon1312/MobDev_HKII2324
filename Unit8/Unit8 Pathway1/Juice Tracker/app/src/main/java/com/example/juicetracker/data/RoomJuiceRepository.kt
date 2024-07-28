package com.example.juicetracker.data

import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [JuiceRepository] interface
 * which allow access and modification of Juice items through [JuiceDao]
 */
class RoomJuiceRepository(private val juiceDao: JuiceDao) : JuiceRepository {
    override val juiceStream: Flow<List<Juice>> = juiceDao.getAll()

    override suspend fun addJuice(juice: Juice) = juiceDao.insert(juice)
    override suspend fun deleteJuice(juice: Juice) = juiceDao.delete(juice)
    override suspend fun updateJuice(juice: Juice) = juiceDao.update(juice)
}
