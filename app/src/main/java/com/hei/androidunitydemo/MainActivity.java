package com.hei.androidunitydemo;

import android.os.Bundle;
import android.util.Log;
import android.os.SystemClock;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.unity3d.player.UnityPlayerActivity;

import android_serialport_api.SerialPort;

/**
 * Created by hasee on 2016/12/14.
 */

public class MainActivity extends UnityPlayerActivity {
    private static final String TAG = "MainActivity";
    private SerialPort ttyS3;
    BufferedInputStream in0;
    private byte[] readBuf;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void SerialPortInit(String SerialPortName) {
        try {
            ttyS3 = new SerialPort(new File(SerialPortName/*"/dev/ttyS3"*/), 115200, 0);
            in0 = new BufferedInputStream(ttyS3.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readBuf = new byte[8];
    }

    public void Write(byte[] data, int off, int len) {
        DataOutputStream os = null;
        os = new DataOutputStream(ttyS3.getOutputStream());
        try {
            os.write(data, off, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int Read(byte[] data, int off, int len) {
        //InputStream in = ttyS3.getFileInputStream();

        int r = 0;
        //int i = 0;
        //byte[] buffer=null;
        try {
            //while (in0.available() == 0 && i < 100)
            //i++;
            //buffer = new byte[in0.available()];
            r = in0.read(data, off, in0.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public byte ReadOneByte() {
        try {
            if (in0.available() > 0) in0.read(readBuf, 0, 1);
            //Log.d(TAG, "ReadOneByte: "+readBuf[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte b = readBuf[0];
        return b;
    }

    public int Available() {
        int r = 0;
        try {
            r = in0.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public boolean setDateTime(int year, int month, int day, int hour, int minute, int second) throws IOException, InterruptedException {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute, second);
        long when = cal.getTimeInMillis();
        Log.e("time", "settime: " + cal.getTime());
        SystemClock.setCurrentTimeMillis(when);
        return true;

    }

    public String Echo(String str){
        return str;
    }
}
