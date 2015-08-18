package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.IPrivSensorService;
import android.util.Log;

public class PrivSensorService extends IPrivSensorService.Stub {
    private static final String TAG = "PrivSensorService";
    private Context mContext;
    private PrivSensorServiceThread mThread;
    private PrivSensorServiceHandler mHandler;

   
    public PrivSensorService(Context context) {
        super();
        Log.d(TAG, "Initializing PrivateSensing Service");
        mContext = context;
        mThread = new PrivSensorServiceThread("PrivSensorService Thread");
        mThread.start();
        Log.d(TAG, "Spawned PrivSensorService worker thread");
    }

    private class PrivSensorServiceThread extends Thread {
        public PrivSensorServiceThread(String name) {
            super(name);
        }

        public void run() {
            Looper.prepare();
            mHandler = new PrivSensorServiceHandler();
            Looper.loop();
        }
    }

    private class PrivSensorServiceHandler extends Handler {
        private static final int MESSAGE_HELLO = 0;

        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == MESSAGE_HELLO) {
                    Log.i(TAG, "recieved message hello ");
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in handleMessage : " + e.getMessage());
            }
        }
    }

    public String helloWorld() {
        Message msg = Message.obtain();
        msg.what = PrivSensorServiceHandler.MESSAGE_HELLO;
        mHandler.sendMessage(msg);
        return "Hello PrivateSensing World ";
    }

}
