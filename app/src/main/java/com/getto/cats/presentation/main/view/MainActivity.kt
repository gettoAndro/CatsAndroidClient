package com.getto.cats.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.getto.cats.R
import com.getto.cats.data.entity.Cat
import com.getto.cats.presentation.adapter.CatsAdapter
import com.getto.cats.presentation.details.view.DetailsActivity
import com.getto.cats.presentation.main.presenter.MainPresenter
import com.getto.cats.repository.CatsRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() , MainView {

    private val presenter : MainPresenter = MainPresenter(CatsRepository(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        presenter.getCats()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showDetails(cat: Cat) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("cat" ,cat)
        startActivity(intent)
    }

    override fun showCat(cats : ArrayList<Cat>) {
        val adapter = CatsAdapter(cats, this)
        rc_cats.layoutManager = LinearLayoutManager(this)
        rc_cats.adapter = adapter
    }

}
