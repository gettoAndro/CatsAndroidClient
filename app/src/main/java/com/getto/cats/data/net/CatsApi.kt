package com.getto.cats.data.net

import com.getto.cats.data.entity.Cat
import com.getto.cats.data.net.constants.Endpoints
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CatsApi {

    @GET(Endpoints.GET_CATS)
    fun getCats() : Single<ArrayList<Cat>>

//    @Multipart
//    @POST(Endpoints.ADD_CAT)
//    fun addCat(@Query("name") name: String, @Part file: MultipartBody.Part?) : Single<String>
//
    @GET(Endpoints.ADD_CAT)
    fun addCat(@Query("name") name: String) : Completable

    @GET(Endpoints.UPDATE_CAT)
    fun updateCat(@Header("id") id : Long, @Query("name") name: String) : Completable

    @GET(Endpoints.DELETE_CAT)
    fun deleteCat(@Query("id") id : Long) : Completable

    companion object {
        fun create() : CatsApi{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(Endpoints.API)
                    .build()

            return retrofit.create(CatsApi::class.java)
        }
    }

}