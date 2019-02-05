package com.david.barbaran.sortingalgorithms.presenter

import android.content.Context
import com.david.barbaran.sortingalgorithms.*
import com.david.barbaran.sortingalgorithms.controller.MainController
import com.david.barbaran.sortingalgorithms.model.User
import com.david.barbaran.sortingalgorithms.task.LoadTask
import com.david.barbaran.sortingalgorithms.task.OnTaskExecute
import com.david.barbaran.sortingalgorithms.util.quicksort
import com.david.barbaran.sortingalgorithms.util.selectionsort
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class MainPresenter constructor(private val context: Context) :
    OnTaskExecute {

    lateinit var controller: MainController
    lateinit var loadTask: LoadTask
    var orderType: Int = -1
    var orderList = mutableListOf<User>()
    var originList = mutableListOf<User>()

    init {
        // loadTask.onFinishTask = this
    }

    fun load(orderType : Int) {
        this.orderType = orderType
        loadTask = LoadTask()
        loadTask.onFinishTask = this
        loadTask.execute()
    }

    private fun loadData() {
        val mRaw = context.resources.openRawResource(R.raw.data)
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
        orderList.addAll(yourList)
        originList.addAll(yourList)
    }

    private fun orderSelection() {
        loadTask.list.clear()
        loadTask.list.addAll(selectionsort(orderList))
    }

    private fun orderQuickSot() {
        loadTask.list.clear()
        loadTask.list.addAll(quicksort(orderList))
    }

    private fun defaultList() {
        loadTask.list.clear()
        loadTask.list.addAll(originList)
    }

    override fun onLoadTask() {
        when (orderType) {
            -1 -> {
                loadData()
            }
            0 -> {
                defaultList()
            }
            1 -> {
                orderSelection()
            }
            2 -> {
                orderQuickSot()
            }
            3 -> {

            }

        }

    }

    override fun onFinish(list: MutableList<User>) {
        controller.onLoadUserSuccessful(list)
    }
}