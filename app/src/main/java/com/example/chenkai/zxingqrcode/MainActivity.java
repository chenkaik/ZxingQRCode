package com.example.chenkai.zxingqrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private ImageView imageView;

    private CameraManager manager;
    public static boolean kaiguan = true; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态

//    private boolean status = false;
//    private Camera camera;
//    private Camera.Parameters parameters;
//    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_s).setOnClickListener(this);
        findViewById(R.id.shengcheng).setOnClickListener(this);
        findViewById(R.id.open).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageview);
        // 闪光灯
//        instance = this;
//        camera = Camera.open();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this,QrScanActivity.class));
                } else {
                    Toast.makeText(this, "请允许程序访问相机权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_s:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                }else {
                    startActivity(new Intent(this,QrScanActivity.class));
                }
                break;
            case R.id.open:
//                    if (!status) {
//                        status = true;
//                        new Thread(new TurnOnLight()).start();
//                    }else {
//                        status = false;
//                        //openLight.setText("打开");
//                        instance.parameters.setFlashMode("off");
//                        instance.camera.setParameters(instance.parameters);
//                    }
                break;
            case R.id.shengcheng:
                String info = editText.getText().toString().trim();
                if (TextUtils.isEmpty(info)){
                    Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Bitmap b = EncodingUtils.createQRCode(info,500,500, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launr));
                    if (b != null){
                        imageView.setImageBitmap(b);
                    }
                }
            default:
                break;
        }
    }


//    private class TurnOnLight implements Runnable{
//        @Override
//        public void run() {
//            instance.parameters = instance.camera.getParameters();
//            instance.parameters.setFlashMode("torch");
//            instance.camera.setParameters(instance.parameters);
//        }
//    }

    /**
     * 判断Android系统版本是否 >= LOLLIPOP(API21)
     *
     * @return boolean
     */
    private boolean isLOLLIPOP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

}
