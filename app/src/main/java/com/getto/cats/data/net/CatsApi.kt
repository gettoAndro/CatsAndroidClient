package com.getto.cats.data.net

import com.getto.cats.data.entity.Cat
import com.getto.cats.data.net.constants.Endpoints
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

public interface CatsApi {

    @GET(Endpoints.GET_CATS)
    fun getCats() : Single<ArrayList<Cat>>

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