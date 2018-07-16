package com.getto.cats.presentation.main.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.getto.cats.R
import com.getto.cats.data.entity.Cat
import com.getto.cats.presentation.adapter.CatsAdapter
import com.getto.cats.presentation.main.presenter.MainPresenter
import com.getto.cats.repository.CatsRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_main.*
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import java.io.FileNotFoundException
import android.provider.MediaStore
import android.view.*

class MainActivity : AppCompatActivity() , MainView {

    private val presenter : MainPresenter = MainPresenter(CatsRepository(), this)
    private var path : String = ""

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
                val filePath = getRealPathFromURI(selectedImage)
                Log.d("CHOOSE IMAGE", "PATH = $filePath")
                val fileExt = filePath.substring(filePath.lastIndexOf(".") + 1)
                try {
                    if (fileExt == ("img") || fileExt == ("jpg") || fileExt ==("jpeg") || fileExt ==("gif") || fileExt == "png") {
                        //FINE
                        path = filePath
                        createDialog(selectedImage, null)
                    } else {
                        //NOT IN REQUIRED FORMAT
                    }
                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            }
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    override fun showCat(cats : ArrayList<Cat>) {
        val adapter = CatsAdapter(cats, this)
        rc_cats.layoutManager = LinearLayoutManager(this)
        rc_cats.adapter = adapter
        registerForContextMenu(rc_cats)
    }

    override fun onAddAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 101)
    }

    private fun createDialog(data : Uri?, cat : Cat?){
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.dialog_add, null)
        val image = view.findViewById<ImageView>(R.id.add_avatar)
        image.setOnClickListener { v: View? -> presenter.addAvatar() }
        if (data != null)
            image.setImageURI(data)
        val edit = view.findViewById<EditText>(R.id.add_edit_name)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        if (cat != null){
            edit.setText(cat.name)
            builder.setTitle("Edit ${cat.name}")
        }
        else builder.setTitle("Add cat")

        builder.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
            if (cat == null)
                presenter.addCat(edit.text.toString())
            else presenter.updateCat(cat.id, edit.text.toString())
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
        builder.show()
    }

    override fun showAddDialog() {
       createDialog(null, null)
    }

    override fun showEditDialog(cat: Cat) {
        createDialog(null, cat)
    }

    override fun onDeleteCat(id: Long) {
        presenter.deleteCat(id)
    }
}
