package com.bigstep.launchmodes


import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log

class WorkerThread2(tag: String) : HandlerThread(tag) {

    private lateinit var handler: Handler

    constructor() : this("WorkerThread") {
        start()
        handler = object : Handler(looper) {
            override fun handleMessage(msg: Message) {
                Log.d("handleMessage", Thread.currentThread().name)
                onHandleIntent()
                looper.quit()
            }
        }
        Log.d("WorkerConstRequest", Thread.currentThread().name)
        onStart()
    }


    private fun onStart() {
        val msg = Message.obtain()
        msg.obj = "FirstRequest"
        handler.sendMessage(msg)
        Log.d("onStartRequest", Thread.currentThread().name)
    }


    fun invokeWork(task: Runnable): WorkerThread2 {
        if (this::handler.isInitialized) {
            handler.post(task)
        }
        Log.d("invokeWorkRequest", Thread.currentThread().name)
        return this
    }


    private fun onHandleIntent() {
        for (i in 0..10) {
            Thread.sleep(10000)
            Log.d("WonHandleIntent", Thread.currentThread().name)
        }
    }

}