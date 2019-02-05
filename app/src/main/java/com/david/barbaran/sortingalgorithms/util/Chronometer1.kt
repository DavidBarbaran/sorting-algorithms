package com.david.barbaran.sortingalgorithms.util

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.widget.TextView
import android.os.SystemClock
import android.view.View
import java.text.DecimalFormat


class Chronometer1 : TextView {

    private val TAG = "Chronometer"

    interface OnChronometerTickListener {

        fun onChronometerTick(chronometer: Chronometer1)
    }

    private var mBase: Long = 0
    private var mVisible: Boolean = false
    private var mStarted: Boolean = false
    private var mRunning: Boolean = false
    private var mOnChronometerTickListener: OnChronometerTickListener? = null

    private val TICK_WHAT = 2

    private var timeElapsed: Long = 0

    constructor(context: Context) : super(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    //

    private fun init() {
        mBase = 0
        updateText(mBase)
    }

    fun setBase(base: Long) {
        mBase = base
        dispatchChronometerTick()
        updateText(mBase)
    }

    fun getBase(): Long {
        return mBase
    }

    fun start() {
        mStarted = true
        updateRunning()
    }

    fun stop() {
        mStarted = false
        updateRunning()
    }


    fun setStarted(started: Boolean) {
        mStarted = started
        updateRunning()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mVisible = false
        updateRunning()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        mVisible = visibility == View.VISIBLE
        updateRunning()
    }

    @Synchronized
    private fun updateText(now: Long) {
        timeElapsed = now - mBase

        val df = DecimalFormat("00")

        val hours: Int = Math.round((timeElapsed / (3600 * 1000)).toDouble()).toInt()
        var remaining: Int = Math.round((timeElapsed % (3600 * 1000)).toDouble()).toInt()

        val minutes = remaining / (60 * 1000)
        remaining = remaining % (60 * 1000)

        val seconds = remaining / 1000
        remaining = remaining % 1000

        val d: Int = Math.round((timeElapsed % 1000).toDouble()).toInt()
        val milliseconds: Int = Math.round((d / 100).toDouble()).toInt()

        var text = ""

        if (hours > 0) {
            text += df.format(hours) + ":"
        }

        text += df.format(minutes) + ":"
        text += df.format(seconds) + ":"
        text += Integer.toString(milliseconds)

        setText(text)
    }

    private fun updateRunning() {
        val running = mVisible && mStarted
        if (running != mRunning) {
            if (running) {
                updateText(mBase)
                dispatchChronometerTick()
                mHandler.sendMessageDelayed(
                    Message.obtain(
                        mHandler,
                        TICK_WHAT
                    ), 100
                )
            } else {
                mHandler.removeMessages(TICK_WHAT)
            }
            mRunning = running
        }
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(m: Message) {
            if (mRunning) {
                updateText(mBase)
                dispatchChronometerTick()
                sendMessageDelayed(
                    Message.obtain(this, TICK_WHAT),
                    100
                )
            }
        }
    }

    fun dispatchChronometerTick() {
        if (mOnChronometerTickListener != null) {
            mOnChronometerTickListener!!.onChronometerTick(this)
        }
    }

    fun getTimeElapsed(): Long {
        return timeElapsed
    }
}