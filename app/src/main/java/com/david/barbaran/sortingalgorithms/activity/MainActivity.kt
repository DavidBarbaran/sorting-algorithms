package com.david.barbaran.sortingalgorithms.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.barbaran.sortingalgorithms.*
import com.david.barbaran.sortingalgorithms.adapter.UserAdapter
import com.david.barbaran.sortingalgorithms.config.Setting
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
        initView()
        initClick()
    }

    private fun initView() {
        presenter.controller = this
        chronometer.onTimerExecute = this
        userRecycler.layoutManager = LinearLayoutManager(this)
        userRecycler.adapter = userAdapter
        presenter.load(0)
        secondarySwitch.setOnCheckedChangeListener { _, p1 -> Setting.isDetail = p1 }
    }

    private fun initClick() {
        oneButton.setOnClickListener {
            changeStyle(oneButton, true)
            changeStyle(twoButton, false)
            changeStyle(threeButton, false)
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.rawData = R.raw.data_100
            presenter.load(0)
        }

        twoButton.setOnClickListener {
            changeStyle(oneButton, false)
            changeStyle(twoButton, true)
            changeStyle(threeButton, false)
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.rawData = R.raw.data_1k
            presenter.load(0)
        }

        threeButton.setOnClickListener {
            changeStyle(oneButton, false)
            changeStyle(twoButton, false)
            changeStyle(threeButton, true)
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.rawData = R.raw.data_10k
            presenter.load(0)
        }

        defaultButton.setOnClickListener {
            chronometerText.text = "00:00:000"
            presenter.load(1)
        }

        selectionButton.setOnClickListener {
            resetCount()
            chronometer.start()
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.load(2)
        }
        quickSortButton.setOnClickListener {
            resetCount()
            chronometer.start()
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.load(3)
        }

        mergeSortButton.setOnClickListener {
            resetCount()
            chronometer.start()
            userRecycler.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.load(4)
        }
    }

    private fun resetCount(){
        interactorText.text = "0"
        recursiveText.text = "0"
    }

    private fun changeStyle(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.ic_background_green else 0)
        button.setTextColor(
            ContextCompat.getColor(
                this,
                if (isSelected) R.color.colorPrimary else android.R.color.white
            )
        )
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

    override fun onIteration(count: Int) {
        runOnUiThread {
            interactorText.text = "$count"
        }
    }

    override fun onRecursive(count: Int) {
        runOnUiThread {
            recursiveText.text = "$count"
        }
    }
}