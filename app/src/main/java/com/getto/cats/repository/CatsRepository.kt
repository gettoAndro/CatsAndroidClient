package com.getto.cats.repository

import com.getto.cats.data.entity.Cat
import com.getto.cats.data.net.CatsApi
import io.reactivex.Single

 class CatsRepository {

    private val catsApi by lazy {
        CatsApi.create()
    }

    public fun getCats() : Single<ArrayList<Cat>>{
        return catsApi.getCats()
    }

}