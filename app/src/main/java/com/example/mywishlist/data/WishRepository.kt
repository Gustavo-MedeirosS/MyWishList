package com.example.mywishlist.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish = wish)
    }

    fun getAllWishes(): Flow<List<Wish>> {
        return wishDao.getAllWishes()
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishDao.getWishById(id = id)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish = wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish = wish)
    }
}