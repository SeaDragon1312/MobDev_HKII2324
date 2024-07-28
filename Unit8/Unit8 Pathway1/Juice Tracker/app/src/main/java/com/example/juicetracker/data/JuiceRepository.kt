package com.example.juicetracker.data

import kotlinx.coroutines.flow.Flow

/**
 * Interface for [JuiceRepository] which contains method to access and modify juice items
 */
interface JuiceRepository {
    val juiceStream: Flow<List<Juice>>
    suspend fun addJuice(juice: Juice)
    suspend fun deleteJuice(juice: Juice)
    suspend fun updateJuice(juice: Juice)
}
