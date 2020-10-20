package com.bigstep.launchmodes

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

const val RC_HINT = 2

class MainActivity : AppCompatActivity() {

    lateinit var textView: EditText
    private lateinit var workerThread: WorkerThread
    private lateinit var handlerThread: HandlerThread
    private lateinit var looper: Looper
    private lateinit var handler: Handler
    lateinit var text: TextView
    lateinit var resendOtp: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity2", "onCreate()")
        textView = findViewById(R.id.textView)
        text = findViewById(R.id.text)
        resendOtp = findViewById(R.id.resendOtp)

        resendOtp.setOnClickListener {
//call BD Cast receiver again to get otp
            // or send a request to server again

        }

        requestHint()
        startSmsListener()


        /*  textView.setOnClickListener {
              startActivity(Intent(this, MainActivity2::class.java))
          }*/
    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val client = Credentials.getClient(this)
        val intent = client.getHintPickerIntent(
            hintRequest
        )
        try {
            startIntentSenderForResult(
                intent.intentSender,
                RC_HINT, null, 0, 0, 0
            )
        } catch (e: Exception) {
            Log.e("Error In  Message", e.message)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HINT && resultCode == Activity.RESULT_OK) {

            //You will receive user selected phone number here if selected and send it to the server for request the otp
            val credential: Credential = data?.getParcelableExtra(Credential.EXTRA_KEY)!!
            //  textView.setText(credential.id.substring(3))

        }
    }


    private fun startSmsListener() {
        val client = SmsRetriever.getClient(this /* context */)
        val task = client.startSmsRetriever()
        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener {
            // Successfully started retriever, expect broadcast intent
            // ...
            Log.e("Waiting for the OTP", "Success")
        }

        task.addOnFailureListener {
            // Failed to start retriever, inspect Exception for more details
            // ...
            Log.e("SMS Retriever", "Failed")
        }
    }


    //  workerThread = WorkerThread()

    /*  handler = object : Handler(Looper.getMainLooper()) {
          override fun handleMessage(msg: Message) {
              Log.d("FirstRequestData", Thread.currentThread().name)

          }
      }*/


    /* handlerThread = HandlerThread("Name")
     handlerThread.start()
     looper = handlerThread.looper*/
    /* workerThread.invokeWork {
         Log.d("FirstRequest", Thread.currentThread().name)
         try {
             Thread.sleep(2000)
         } catch (e: Exception) {
             Log.d("TAggg", e.message.toString())
         }
         val mas = Message.obtain()
         mas.obj = "HIIIII"
         handler.sendMessage(mas)
     }.invokeWork {
         Log.d("SecondRequest", Thread.currentThread().name)
         try {
             Thread.sleep(5000)
         } catch (e: Exception) {
             Log.d("TAggg", e.message.toString())
         }

         val mas = Message.obtain()
         mas.obj = "HIIIII REEEEEE"
         handler.sendMessage(mas)

     }.invokeWork {
         Log.d("ThirdRequest", Thread.currentThread().name)
         try {
             Thread.sleep(5000)
         } catch (e: Exception) {
             Log.d("TAggg", e.message.toString())
         }

         val mas = Message.obtain()
         mas.obj = "HIIIII REEEEEE BABABABABA"
         handler.sendMessage(mas)
     }*/


//        workerThread.invokeWork(object : Runnable {
//            override fun run() {
//
//
//
//            }
//
//        })
    /*   workerThread.invokeWork("b")
       workerThread.invokeWork("c")
       workerThread.invokeWork("d")
       workerThread.invokeWork("f")*/
    //}

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("MainActivity2", "onNewIntent()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart()")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", "onRestart()")

    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume()")
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsVerificationReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause()")
        unregisterReceiver(smsVerificationReceiver)
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy()")
    }

    //this will fired uo after 5 min .if you want to fire this before that means want to get otp before 5 min then have register again after clicking on resend otp button
    private val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        try {
                            // Get SMS message contents
                            val message = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                            // {  <#> your code is 2345 yourHashcode    }

                            val subString = message?.substringAfter("is ")
                            val code = subString?.substring(0, 4)
                            textView.setText(code)
                            // Extract one-time code from the message and complete verification
                            // by sending the code back to your server.
                            // parseAndFillOTP(message)
                        } catch (e: ActivityNotFoundException) {
                            // Handle the exception ...
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Time out occurred, handle the error.
                    }
                }
            }
        }
    }
}