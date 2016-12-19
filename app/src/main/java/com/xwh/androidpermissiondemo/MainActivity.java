package com.xwh.androidpermissiondemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final int REQ_PERMISSION_STORAGE = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStoragePermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        testToWriteAFile();
    }

    private void testToWriteAFile() {
        File newFile = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
        if (newFile.exists() && newFile.isFile()) {
            newFile.delete();
        }
        try {
            newFile.createNewFile();
            Toast.makeText(this, "文件创建成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件创建失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，请求授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "得到授权", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权取消", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
