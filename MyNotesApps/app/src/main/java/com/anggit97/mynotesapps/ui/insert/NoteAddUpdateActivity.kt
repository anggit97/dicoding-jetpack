package com.anggit97.mynotesapps.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.anggit97.mynotesapps.R
import com.anggit97.mynotesapps.database.Note
import com.anggit97.mynotesapps.helper.ViewModelFactory
import kotlinx.android.synthetic.main.activity_note_add_update.*
import com.anggit97.mynotesapps.helper.DateHelper
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog


const val EXTRA_NOTE = "extra_note"
const val EXTRA_POSITION = "extra_position"

const val REQUEST_ADD = 100
const val RESULT_ADD = 101
const val REQUEST_UPDATE = 200
const val RESULT_UPDATE = 201
const val RESULT_DELETE = 301

class NoteAddUpdateActivity : AppCompatActivity() {

    private var isEdit = false

    private val ALERT_DIALOG_CLOSE = 10
    private val ALERT_DIALOG_DELETE = 20

    private var note: Note? = null
    private var position: Int = 0

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)

        noteAddUpdateViewModel = obtainViewModel(this)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            if (note != null) {
                edt_title.setText(note?.title)
                edt_description.setText(note?.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        if (supportActionBar != null) {
            supportActionBar?.title = actionBarTitle
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        btn_submit.text = btnTitle

        onClickListener()
    }

    private fun onClickListener() {

        btn_submit.setOnClickListener {
            val title = edt_title.text.toString().trim()
            val description = edt_description.text.toString().trim()

            if (title.isEmpty()) {
                edt_title.error = getString(R.string.empty)
            } else if (description.isEmpty()) {
                edt_description.error = getString(R.string.empty)
            } else {
                note?.title = title
                note?.description = description

                val intent = Intent()
                intent.putExtra(EXTRA_NOTE, note)
                intent.putExtra(EXTRA_POSITION, position)

                if (isEdit) {
                    note?.let { it1 -> noteAddUpdateViewModel.update(it1) }
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note?.date = DateHelper.getCurrentDate()
                    note?.let { it1 -> noteAddUpdateViewModel.insert(it1) }
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                showAlertDialog(ALERT_DIALOG_DELETE)
            }
            android.R.id.home -> {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    note?.let { noteAddUpdateViewModel.delete(it) }

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                    finish()

                }
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }
}
