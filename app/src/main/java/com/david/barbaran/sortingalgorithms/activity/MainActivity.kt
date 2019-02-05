package com.david.barbaran.sortingalgorithms.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.barbaran.sortingalgorithms.*
import com.david.barbaran.sortingalgorithms.adapter.UserAdapter
import com.david.barbaran.sortingalgorithms.controller.MainController
import com.david.barbaran.sortingalgorithms.model.User
import com.david.barbaran.sortingalgorithms.presenter.MainPresenter
import com.david.barbaran.sortingalgorithms.util.Chronometer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainController, Chronometer.OnTimerExecute {

    private val presenter: MainPresenter by inject()
    private val userAdapter: UserAdapter by inject()
    private val chronometer = Chronometer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.controller = this
        chronometer.onTimerExecute = this
        userRecycler.layoutManager = LinearLayoutManager(this)
        userRecycler.adapter = userAdapter
        presenter.load(-1)
        initClick()
    }

    private fun initClick() {
        defaultButton.setOnClickListener {
            chronometerText.text = "00:00:000"
            presenter.load(0)
        }

        selectionButton.setOnClickListener {
            chronometer.start()
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.load(1)
        }
        quickSortButton.setOnClickListener {
            chronometer.start()
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.load(2)
        }
    }

    override fun onLoadUserSuccessful(list: MutableList<User>) {
        chronometer.pause()
        userRecycler.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        userAdapter.list = list
        userAdapter.notifyDataSetChanged()
    }

    override fun onExecute(time: String) {
        runOnUiThread {
            chronometerText.text = time
        }
    }
}