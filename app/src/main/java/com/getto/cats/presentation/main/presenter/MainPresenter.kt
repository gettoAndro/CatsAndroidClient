package com.getto.cats.presentation.main.presenter

import android.util.Log
import com.getto.cats.data.entity.Cat
import com.getto.cats.presentation.main.view.MainView
import com.getto.cats.repository.CatsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class MainPresenter(val repository: CatsRepository,val view : MainView) {

    fun getCats() {
        repository.getCats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showResult)
    }

    private fun showResult(cats : ArrayList<Cat>){
        view.showCat(cats)
    }

    fun showAddDialog(){
        view.showAddDialog()
    }

    fun addAvatar(){
        view.onAddAvatar()
    }

    fun addCat(name : String, path : String){
        val file = null
        if (path != null) {
          //TODO  file = File(path)
        }
        repository.addCat(name, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess)
    }

    private fun handleSuccess(response : String) {
        Log.d("Add CAT", response)
    }
}