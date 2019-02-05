package com.david.barbaran.sortingalgorithms.task

import android.os.AsyncTask
import com.david.barbaran.sortingalgorithms.model.User

class LoadTask : AsyncTask<Void, Void, Void>() {

    lateinit var onFinishTask: OnTaskExecute
    var list = mutableListOf<User>()

    override fun doInBackground(vararg p0: Void?): Void? {
        onFinishTask.onLoadTask()
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        onFinishTask.onFinish(list)
    }
}

interface OnTaskExecute {
    fun onLoadTask()
    fun onFinish(list: MutableList<User>)
}