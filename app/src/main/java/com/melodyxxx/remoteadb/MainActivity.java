package com.melodyxxx.remoteadb;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "bingo";

    private TextView mTips;

    private boolean mIsConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTips(getWifiIpAdress());
    }

    private void initViews() {
        mTips = (TextView) findViewById(R.id.tv_tips);
    }

    private void initTips(String wifiIpAddress) {
        if (!TextUtils.isEmpty(wifiIpAddress)) {
            mTips.setText("PC Exec: adb connect " + wifiIpAddress);
        } else {
            mTips.setText("Please check your connection!");
        }
    }

    private String getWifiIpAdress() {
        String wifiIpAddress = null;
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        if (wifiInfo != null) {
            int address = wifiInfo.getIpAddress();
            if (address != 0) {
                wifiIpAddress = Formatter.formatIpAddress(address);
            }
        }
        return wifiIpAddress;
    }

    public void start(View view) {
        if (setAdbTcpPort(5555)) {
            Toast.makeText(MainActivity.this, "Start Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Start Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View view) {
        if (setAdbTcpPort(-1)) {
            Toast.makeText(MainActivity.this, "Stop Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Stop Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String wifiIpAddress = getWifiIpAdress();
        if (!TextUtils.isEmpty(wifiIpAddress)) {
            mIsConnected = true;
        } else {
            mIsConnected = false;
        }
    }

    private boolean setAdbTcpPort(int port) {
        if (!mIsConnected) {
            Toast.makeText(MainActivity.this, "Please check your connection!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Runtime.getRuntime().exec("setprop service.adb.tcp.port " + port);
            Runtime.getRuntime().exec("stop adbd");
            Runtime.getRuntime().exec("start adbd");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
