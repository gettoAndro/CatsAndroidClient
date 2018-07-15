package com.getto.cats.presentation.main.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
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
import kotlinx.android.synthetic.main.dialog_add.*
import android.provider.MediaStore.MediaColumns
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() , MainView {

    private val presenter : MainPresenter = MainPresenter(CatsRepository(), this)
    private var path : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            presenter.showAddDialog()
        }
        presenter.getCats()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101)
            if (resultCode == Activity.RESULT_OK){
                val selectedImage = data!!.data
                val filePath = getPath(selectedImage)
                Log.d("CHOOSE IMAGE", "PATH = $filePath")
                val fileExt = filePath.substring(filePath.lastIndexOf(".") + 1)
                try {
                    if (fileExt == ("img") || fileExt == ("jpg") || fileExt ==("jpeg") || fileExt ==("gif") || fileExt == "png") {
                        //FINE
                        path = filePath
                        add_avatar.setImageURI(selectedImage)
                    } else {
                        //NOT IN REQUIRED FORMAT
                    }
                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }


            }
    }

   private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor
                .getColumnIndexOrThrow(MediaColumns.DATA)
        cursor.moveToFirst()

        return cursor.getString(column_index)
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

    override fun onAddAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, 101)
    }

    override fun showAddDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.dialog_add, null)
        view.findViewById<ImageView>(R.id.add_avatar).setOnClickListener { v: View? -> presenter.addAvatar() }
        val edit = view.findViewById<EditText>(R.id.add_edit_name)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setTitle("Add cat")
        builder.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which -> presenter.addCat(edit.text.toString(), path) })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
        builder.show()
    }
}
