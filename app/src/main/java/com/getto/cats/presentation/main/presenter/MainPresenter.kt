package com.getto.cats.presentation.main.presenter

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
}