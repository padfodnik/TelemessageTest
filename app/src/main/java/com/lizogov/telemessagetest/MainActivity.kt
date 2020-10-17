package com.lizogov.telemessagetest

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random.Default.nextInt


class MainActivity : AppCompatActivity() {

    private val newStudentActivityRequestCode = 1
    private lateinit var studentViewModel: StudentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = StudentListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)


        studentViewModel.allStudents.observe(this, { Students ->

            Students?.let {
                adapter.setStudents(it)
            }
        })
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position: Student = adapter.getItem(viewHolder.adapterPosition)
                    if (adapter.itemCount > 1) {
                        studentViewModel.delete(position)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "At least one element must remain",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewStudentActivity::class.java)
            startActivityForResult(intent, newStudentActivityRequestCode)
        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newStudentActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var name = ""
            var score = 0

            intentData?.getStringExtra(NewStudentActivity.EXTRA_REPLY_NAME)?.let {
                name = it
            }
            intentData?.getIntExtra(NewStudentActivity.EXTRA_REPLY_SCORE, -1)?.let { it ->
                score = it
            }

            val color =
                Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
            studentViewModel.insert(Student(0, name, color, score))

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
