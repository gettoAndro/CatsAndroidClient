package com.getto.cats.presentation.main.view

import com.getto.cats.data.entity.Cat

interface MainView {

    fun showCat(cats : ArrayList<Cat>)
    fun showDetails(cat : Cat)
    fun showAddDialog()
    fun onAddAvatar()

}