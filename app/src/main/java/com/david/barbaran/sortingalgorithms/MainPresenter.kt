package com.david.barbaran.sortingalgorithms

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class MainPresenter constructor(private val context: Context) {

    fun loadData() {
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
        //controller.onLoadExpositors(yourList)

    }
}