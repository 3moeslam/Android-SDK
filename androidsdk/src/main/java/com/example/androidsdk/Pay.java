package com.example.androidsdk;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Pay  extends AppCompatActivity {
    public static String URL ="" ;
    public String PaymentKey;
    public int IframeID;
   public WebView mywebview;
    String success ="";
    String Id ="";
    String Endpoint= "";
    private List<String> urls = new ArrayList<String>();

    public void StartPayment(String paymentKey, int iframeID) {

        mywebview  = findViewById(R.id.webView);
        mywebview.setWebViewClient(new WebViewClient(){

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
              if(view.getOriginalUrl().contains(Endpoint)){

                  URL = view.getOriginalUrl();
                  Uri uri = Uri.parse(URL);

                success = uri.getQueryParameter("success");
                Id = uri.getQueryParameter("id");

                Intent result = new Intent();
                result.putExtra("success",success);
                result.putExtra("ID",Id);
                setResult(RESULT_OK,result);
                  finish();


              }

            }
        });
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setSupportZoom(true);
        mywebview.getSettings().setBuiltInZoomControls(false);
        mywebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mywebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mywebview.getSettings().setDomStorageEnabled(true);
        mywebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mywebview.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT < 18) {
            mywebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        }

        mywebview.loadUrl("https://accept.paymobsolutions.com/api/acceptance/iframes/" + IframeID + "?payment_token=" + PaymentKey);


    }

//    public void checkpay(){
//
//        Uri uri = Uri.parse(URL);
//        String success = uri.getQueryParameter("success");
//        String Id = uri.getQueryParameter("id");
//        Log.d(TAG, "Success:"+success+"ID:"+Id);
//        Log.d(TAG, "checkpay: "+URL);
//
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        PaymentKey = intent.getStringExtra(IntentKeys.PAYMENT_KEY);
        IframeID = intent.getIntExtra(String.valueOf(IntentKeys.IFRAMEID),1);

        Endpoint = intent.getStringExtra(IntentKeys.ENDPOINT_URL);

          StartPayment(PaymentKey,IframeID);




    }


}
