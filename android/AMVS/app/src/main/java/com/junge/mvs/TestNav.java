package com.junge.mvs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.jimo.mvs.R;

/**
 * Created by TR on 2016/9/6.
 */

public class TestNav extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testnav);
        //webView=new WebView(this);
        webView= (WebView) findViewById(R.id.webid);
        webView.getSettings().setJavaScriptEnabled(true);

        try {
            //设置打开的页面地址
            //webView.loadUrl("file:///android_asset/index.html");
            webView.loadUrl("http://www.isoftstone.com/");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       // setContentView(webView);

}
}
