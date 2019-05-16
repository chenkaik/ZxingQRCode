package com.example.chenkai.zxingqrcode;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenkai.zxingqrcode.zxing.BeepManager;
import com.example.chenkai.zxingqrcode.zxing.ZXingScannerViewNew;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

public class QrScanActivity extends AppCompatActivity implements ZXingScannerViewNew.ResultHandler, ZXingScannerViewNew.QrSize {
    ZXingScannerViewNew scanView;
    private TextView result;
    private BeepManager beepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化自己定制的扫描界面
        scanView = new ZXingScannerViewNew(this);
        scanView.setContentView(R.layout.logistics_scan_qr);
        scanView.setQrSize(this);
        setContentView(scanView);
        beepManager = new BeepManager(this);
        setupFormats();
        initViews();
        result= (TextView) findViewById(R.id.editText);
    }

    private void initViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 设置处理扫描结果的回调方法
        scanView.setResultHandler(this);
        // 设置camera
        scanView.startCamera(-1);
        scanView.setFlash(false);
        scanView.setAutoFocus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopCamera();
        beepManager.close();
    }

    @Override
    public void handleResult(Result rawResult) {
        beepManager.playBeepSoundAndVibrate();
        // 回调方法中接收扫描结果并且设置到textview中  可判断扫描结果是否为null
        result.setText(rawResult.toString());
    }

    // 可以解析二维码和条形码等，我们这里只添加二维码格式，让他只处理二维码
    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        /**
        formats.add(BarcodeFormat.UPC_A);
        formats.add(BarcodeFormat.UPC_E);
        formats.add(BarcodeFormat.EAN_13);
        formats.add(BarcodeFormat.EAN_8);
        formats.add(BarcodeFormat.CODE_39);
        formats.add(BarcodeFormat.CODE_93);
        formats.add(BarcodeFormat.CODE_128);
        formats.add(BarcodeFormat.ITF);
         */
        if (scanView != null) {
            scanView.setFormats(formats);
        }
    }

    @Override
    public Rect getDetectRect() {
        View view = findViewById(R.id.scan_window);
        int top = ((View) view.getParent()).getTop() + view.getTop();
        int left = view.getLeft();
        int width = view.getWidth();
        int height = view.getHeight();
        Rect rect = null;
        if (width != 0 && height != 0) {
            rect = new Rect(left, top, left + width, top + height);
            addLineAnim(rect);
        }
        return rect;
    }

    // 给扫描框内的绿线设置移动动画，让它在扫描区域内循环移动
    private void addLineAnim(Rect rect) {
        ImageView imageView = (ImageView) findViewById(R.id.scanner_line);
        imageView.setVisibility(View.VISIBLE);
        if (imageView.getAnimation() == null) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, rect.height());
            anim.setDuration(2000);
            anim.setRepeatCount(Animation.INFINITE);
            imageView.startAnimation(anim);
        }
    }

}
