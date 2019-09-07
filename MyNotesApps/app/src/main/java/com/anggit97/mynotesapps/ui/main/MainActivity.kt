package com.anggit97.mynotesapps.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.mynotesapps.R
import com.anggit97.mynotesapps.helper.ViewModelFactory
import com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivity
import com.anggit97.mynotesapps.ui.insert.REQUEST_ADD
import com.anggit97.mynotesapps.ui.insert.REQUEST_UPDATE
import com.anggit97.mynotesapps.ui.insert.RESULT_DELETE
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.snackbar.Snackbar


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = obtainViewModel(this)

        adapter = NoteAdapter(this)

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        rv_notes.adapter = adapter

        mainViewModel.getAllNotes().observe(this, Observer {
            if (it != null) {
                adapter.setListNotes(it)
            }
        })

        onClickListener()
    }

    private fun onClickListener() {
        fab_add.setOnClickListener {
            if (it.id === R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == REQUEST_ADD) {
                if (resultCode == REQUEST_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == REQUEST_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_notes, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(mainActivity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(mainActivity.application)
        return ViewModelProviders.of(mainActivity, factory)[MainViewModel::class.java]
    }
}
