package com.getto.cats.presentation.main.presenter

import android.util.Log
import com.getto.cats.data.entity.Cat
import com.getto.cats.presentation.main.view.MainView
import com.getto.cats.repository.CatsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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

    fun addCat(name : String){
        repository.addCat(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure)
    }

    fun updateCat(id : Long, name: String){
        repository.updateCat(id, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure)
    }

    fun deleteCat(id : Long){
        repository.deleteCat(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure)
    }

    private fun handleSuccess() {
        Log.d("CAT", "success")
        getCats()
    }

    private fun handleFailure(t : Throwable){
        Log.d("Server", t.message)
    }
}

