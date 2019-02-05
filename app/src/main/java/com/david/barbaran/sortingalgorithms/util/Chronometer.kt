package com.david.barbaran.sortingalgorithms.util

class Chronometer {

    lateinit var onTimerExecute: OnTimerExecute
    lateinit var customThread: CustomThread

    var isRun = true
    var minute = 0
    var second = 0
    var milliSecond = 0

    fun resetCount() {
        minute = 0
        second = 0
        milliSecond = 0
    }

    fun count(): String {
        milliSecond+=1
        if (milliSecond == 1000) {
            milliSecond = 0
            second++
        }
        if (second == 60) {
            second = 0
            minute++
        }
        return String.format(
            "%02d:%02d:%03d",
            minute,
            second,
            milliSecond
        )
    }

    fun start() {
        resetCount()
        isRun = true
        customThread = CustomThread()
        customThread.start()
    }

    fun pause() {
        isRun = false
        if (::customThread.isInitialized) {
            // customThread.interrupt()
        }
    }

    inner class CustomThread : Thread() {

       // @Synchronized
        override fun run() {
            super.run()
            while (isRun) {
                Thread.sleep(1)
                onTimerExecute.onExecute(count())
            }
        }
    }

    interface OnTimerExecute {
        fun onExecute(time: String)
    }
}