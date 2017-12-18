package com.junge.mvs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.jimo.mvs.R;

/**
 * Created by TR on 2016/9/6.
 */

public class ComplyInfo extends AppCompatActivity {
    private WebView webView;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testnav);
        //webView=new WebView(this);
        title= (TextView) findViewById(R.id.title_info);
        title.setText("公司资讯");
        webView= (WebView) findViewById(R.id.webid);
        webView.getSettings().setJavaScriptEnabled(true);

        try {
            //设置打开的页面地址
            webView.loadUrl("http://www.isoftstone.com/");
            //webView.loadUrl(" http://www.chnsourcing.com.cn/outsourcing-com/com/com/24.html");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        // setContentView(webView);

    }
}
