package com.getto.cats.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.getto.cats.data.entity.Cat
import com.getto.cats.data.net.CatsApi
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class CatsRepository {

    private val MEDIA_TYPE = "multipart/form-data"
    private val REQUEST_BODY_KEY = "image"

    private val catsApi by lazy {
        CatsApi.create()
    }

     fun getCats() : Single<ArrayList<Cat>>{
        return catsApi.getCats()
    }

     fun  addCat(name : String ,file : File) : Single<String> {
         val bmp = BitmapFactory.decodeFile(file.path)
         val bos = ByteArrayOutputStream()
         bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos)
         val requestFile = RequestBody.create(MediaType.parse(MEDIA_TYPE), bos.toByteArray())
         val fileR = MultipartBody.Part.createFormData(REQUEST_BODY_KEY, file.name, requestFile)

         return catsApi.addCat(name, fileR)
     }

}