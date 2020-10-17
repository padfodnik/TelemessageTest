package com.lizogov.telemessagetest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewStudentActivity : AppCompatActivity() {
    private lateinit var editStudNameView: EditText
    private lateinit var editStudScoreView: EditText


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)
        editStudNameView = findViewById(R.id.edit_name)
        editStudScoreView = findViewById(R.id.edit_score)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            when {
                TextUtils.isEmpty(editStudNameView.text) -> {
                    editStudNameView.error = "This data is required!"
                    return@setOnClickListener
                }
                editStudNameView.text.length < 2 -> {
                    editStudNameView.error = "Name must be contains 1 sign!"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(editStudScoreView.text) && TextUtils.isDigitsOnly(
                    editStudScoreView.text
                ) -> {
                    editStudScoreView.error = "This data is required!"
                    return@setOnClickListener
                }
                editStudScoreView.text.toString().toInt() > 100 -> {
                    editStudScoreView.text.clear()
                    editStudScoreView.error = "Score must be in the range from 1 to 100"

                    return@setOnClickListener

                }
                else -> {
                    val name = editStudNameView.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY_NAME, name)
                    val score = editStudScoreView.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY_SCORE, score.toInt())
                    setResult(Activity.RESULT_OK, replyIntent)
                }
            }
            finish()
        }
    }


    companion object {
        const val EXTRA_REPLY_NAME = "com.lizogov.telemessagetest.REPLYNAME"
        const val EXTRA_REPLY_SCORE = "com.lizogov.telemessagetest.REPLYSCORE"

    }
}