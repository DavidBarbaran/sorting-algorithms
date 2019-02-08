package com.david.barbaran.sortingalgorithms.presenter

import android.content.Context
import com.david.barbaran.sortingalgorithms.*
import com.david.barbaran.sortingalgorithms.controller.MainController
import com.david.barbaran.sortingalgorithms.model.User
import com.david.barbaran.sortingalgorithms.task.LoadTask
import com.david.barbaran.sortingalgorithms.task.OnTaskExecute
import com.david.barbaran.sortingalgorithms.util.OrderUtil

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class MainPresenter constructor(private val context: Context) :
    OnTaskExecute, OrderUtil.Interactor {

    var orderUtil = OrderUtil()
    lateinit var controller: MainController
    lateinit var loadTask: LoadTask
    var orderType: Int = 0
    var orderList = mutableListOf<User>()
    var originList = mutableListOf<User>()
    var rawData = R.raw.data_100
    var iterationCount = 0
    var recursiveCount = 0

    init {
        orderUtil.interactor = this
    }

    fun load(orderType: Int) {
        this.orderType = orderType
        loadTask = LoadTask()
        loadTask.onFinishTask = this
        loadTask.execute()
    }

    private fun loadData() {
        val mRaw = context.resources.openRawResource(rawData)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        var read: Int

        try {
            val reader = BufferedReader(InputStreamReader(mRaw, "UTF-8"))
            do {
                read = reader.read(buffer)
                try {
                    writer!!.write(buffer, 0, read)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } while (read != -1)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mRaw.close()
            writer.flush()
        }

        val jsonString = writer.toString()
        val jsonArray: JsonArray = JsonParser().parse(jsonString) as JsonArray

        val listType = object : TypeToken<MutableList<User>>() {}.type
        val yourList = Gson().fromJson<MutableList<User>>(jsonArray, listType)
        loadTask.list.clear()
        loadTask.list.addAll(yourList)
        orderList.clear()
        orderList.addAll(yourList)
        originList.clear()
        originList.addAll(yourList)
    }

    private fun orderSelection() {
        iterationCount = 0
        recursiveCount = 0
        loadTask.list.clear()
        loadTask.list.addAll(orderUtil.selectionsort(orderList))
    }

    private fun orderQuickSot() {
        iterationCount = 0
        recursiveCount = 0
        loadTask.list.clear()
        loadTask.list.addAll(orderUtil.auxQuickSort(orderList))
    }

    private fun defaultList() {
        iterationCount = 0
        recursiveCount = 0
        loadTask.list.clear()
        loadTask.list.addAll(originList)
    }

    private fun orderInsertion() {
        iterationCount = 0
        recursiveCount = 0
        loadTask.list.clear()
        loadTask.list.addAll(orderUtil.insertionsort(orderList))
    }

    override fun onLoadTask() {
        when (orderType) {
            0 -> {
                loadData()
            }
            1 -> {
                defaultList()
            }
            2 -> {
                orderSelection()
            }
            3 -> {
                orderQuickSot()
            }
            4 -> {
                orderInsertion()
            }
        }

    }

    override fun onFinish(list: MutableList<User>) {
        controller.onLoadUserSuccessful(list)
    }

    override fun onInteracting() {
        iterationCount++
        controller.onIteration(iterationCount)
    }

    override fun onRecursive() {
        recursiveCount++
        controller.onRecursive(recursiveCount)
    }
}