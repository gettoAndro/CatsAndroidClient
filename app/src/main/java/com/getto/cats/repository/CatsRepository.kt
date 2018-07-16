package com.getto.cats.repository

import com.getto.cats.data.entity.Cat
import com.getto.cats.data.net.CatsApi
import io.reactivex.Completable
import io.reactivex.Single

class CatsRepository {

    private val MEDIA_TYPE = "multipart/form-data"
    private val REQUEST_BODY_KEY = "image"

    private val catsApi by lazy {
        CatsApi.create()
    }

     fun getCats() : Single<ArrayList<Cat>>{
        return catsApi.getCats()
    }

     fun  addCat(name : String) : Completable {
         return catsApi.addCat(name)
     }

    fun updateCat(id : Long, name: String) : Completable{
        return catsApi.updateCat(id, name)
    }

    fun deleteCat(id : Long) : Completable{
        return catsApi.deleteCat(id)
    }

}