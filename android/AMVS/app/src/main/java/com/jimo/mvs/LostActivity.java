package com.jimo.mvs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LostActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
        , View.OnClickListener {

    private final int GETDATA = 0x1010;//获取数据代码
    private final int UPLOADDATA = 0x2020;//上传数据

    private TextView tv_nodata;
    private TextView tv_getlost;
    private TextView tv_setlost;
    private TextView tv_addlost;
    private SwipeRefreshLayout rf_lost;
    private ListView lv_lost;
    private ArrayList<LostDataBean> allLostData;
    private ArrayList<LostDataBean> getLostData;
    private ArrayList<LostDataBean> setLostData;
    private LostDataAdapter adapter = null;
    private int startPage = 1;
    private int endCnt = 0;
    private int getStartPage = 1;
    private int getEndCount = 0;
    private int setStartPage = 1;
    private int setEndCount = 0;
    private int postType = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETDATA:
                    //取消刷新
                    rf_lost.setRefreshing(false);
                    //更新list
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT).setContentView(R.layout.activity_lost)
                .setSwipeBackView(R.layout.swipeback_default);

        initViews();

        initEvents();
    }

    private void initEvents() {
        tv_addlost.setOnClickListener(this);
        tv_setlost.setOnClickListener(this);
        tv_getlost.setOnClickListener(this);

    }

    private void initViews() {
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        tv_getlost = (TextView) findViewById(R.id.tv_getlost);
        tv_setlost = (TextView) findViewById(R.id.tv_setlost);
        tv_addlost = (TextView) findViewById(R.id.tv_addlost);
        rf_lost = (SwipeRefreshLayout) findViewById(R.id.rf_lost);
        lv_lost = (ListView) findViewById(R.id.lv_lost);
        rf_lost.setOnRefreshListener(this);
        rf_lost.setColorSchemeResources(R.color.colorPrimary, R.color.sb__defaultDivider, R.color.colorAccent);
        allLostData = new ArrayList<>();
        getLostData = new ArrayList<>();
        setLostData = new ArrayList<>();

        getAllLostData();

        adapter = new LostDataAdapter(allLostData, this);
        lv_lost.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }

    @Override
    public void onRefresh() {
//http://192.168.1.108:8080/MVS/servlet/APPLostServlet?type=1&name=%E5%AF%82%E5%AF%9E&content=2015-09-08
//http://192.168.1.108:8080/MVS/servlet/APPLostServlet?type=2&startPage=1
        /*
        这里访问服务器获取数据
         */
        if (0 == postType) {
            removeEnd(allLostData,endCnt);
            getAllLostData();
        } else if (1 == postType) {
            removeEnd(getLostData,getEndCount);
            getGetLostData();
        } else if (2 == postType) {
            removeEnd(setLostData,setEndCount);
            setGetLostData();
        }
    }

    boolean isFirst = true;
    boolean isSetFirst = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addlost:
                addLost();
                break;
            case R.id.tv_getlost:
                postType = 1;
                if (isFirst) {
                    isFirst = false;
                } else {
                    removeEnd(getLostData,getEndCount);
                }
                getGetLostData();
                adapter = new LostDataAdapter(getLostData, LostActivity.this);
                lv_lost.setAdapter(adapter);
                break;
            case R.id.tv_setlost:
                postType = 2;
                if (isSetFirst) {
                    isSetFirst = false;
                } else {
                    removeEnd(setLostData,setEndCount);
                }
                setGetLostData();
                adapter = new LostDataAdapter(setLostData, LostActivity.this);
                lv_lost.setAdapter(adapter);
                break;
        }
    }

    /**
     * 移出尾部的元素
     *
     * @param list
     */
    public void removeEnd(ArrayList<LostDataBean> list,int count) {
        int size = list.size();
        for (int i = size - 1; i >= size - count; i--) {
            if (i >= 0) {
                list.remove(i);
            }
        }
    }

    public void getAllLostData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = APPConst.URL + "MVS/servlet/APPLostServlet?type=2&startPage=" + startPage;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("nomore")) {
                    Snackbar.make(findViewById(R.id.tv_nodata), "没有更多了", Snackbar.LENGTH_SHORT).show();
                    rf_lost.setRefreshing(false);
                } else {
                    //解析json数据
                    tv_nodata.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        endCnt = array.length();
                        for (int i = 0; i < endCnt; i++) {
                            JSONObject ob = array.getJSONObject(i);
                            allLostData.add(new LostDataBean(ob.getString("content"), ob.getString("date"),
                                    ob.getString("name"), ob.getString("contact")));
                        }
                        if (endCnt == 10) {
                            startPage++;
                            endCnt = 0;
                        }
                        handler.sendEmptyMessage(GETDATA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Snackbar.make(findViewById(R.id.tv_nodata), "解析数据出错", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.tv_nodata), "连接出错，请检查设置", Snackbar.LENGTH_SHORT).show();
                rf_lost.setRefreshing(false);
            }
        });

        queue.add(request);
    }

    public void getGetLostData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = APPConst.URL + "MVS/servlet/APPLostServlet?type=3&startPage=" + getStartPage;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("nomore")) {
                    Snackbar.make(findViewById(R.id.tv_nodata), "没有更多了", Snackbar.LENGTH_SHORT).show();
                } else {
                    //解析json数据
                    tv_nodata.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        getEndCount = array.length();
                        for (int i = 0; i < getEndCount; i++) {
                            JSONObject ob = array.getJSONObject(i);
                            getLostData.add(new LostDataBean(ob.getString("content"), ob.getString("date"),
                                    ob.getString("name"), ob.getString("contact")));
                        }
                        if (getEndCount == 10) {
                            getStartPage++;
                            getEndCount = 0;
                        }
                        handler.sendEmptyMessage(GETDATA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Snackbar.make(findViewById(R.id.tv_nodata), "解析数据出错", Snackbar.LENGTH_SHORT).show();
                    }
                }
                rf_lost.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.tv_nodata), "连接出错，请检查设置", Snackbar.LENGTH_SHORT).show();
                rf_lost.setRefreshing(false);
            }
        });

        queue.add(request);
    }

    public void setGetLostData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = APPConst.URL + "MVS/servlet/APPLostServlet?type=4&startPage=" + setStartPage;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("nomore")) {
                    Snackbar.make(findViewById(R.id.tv_nodata), "没有更多了", Snackbar.LENGTH_SHORT).show();
                } else {
                    //解析json数据
                    tv_nodata.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        setEndCount = array.length();
                        for (int i = 0; i < setEndCount; i++) {
                            JSONObject ob = array.getJSONObject(i);
                            setLostData.add(new LostDataBean(ob.getString("content"), ob.getString("date"),
                                    ob.getString("name"), ob.getString("contact")));
                        }
                        if (setEndCount == 10) {
                            setStartPage++;
                            setEndCount = 0;
                        }
                        handler.sendEmptyMessage(GETDATA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Snackbar.make(findViewById(R.id.tv_nodata), "解析数据出错", Snackbar.LENGTH_SHORT).show();
                    }
                }
                rf_lost.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.tv_nodata), "连接出错，请检查设置", Snackbar.LENGTH_SHORT).show();
                rf_lost.setRefreshing(false);
            }
        });

        queue.add(request);
    }

    public void addLost() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_addlost, null);

        EditText edt_add_content = (EditText) view.findViewById(R.id.edt_addlost_content);
        EditText edt_add_contact = (EditText) view.findViewById(R.id.edt_addlost_contact);
        setMultiLine(edt_add_content);
        setMultiLine(edt_add_contact);
        TextView tv_get = (TextView) view.findViewById(R.id.tv_addlost_get);
        TextView tv_set = (TextView) view.findViewById(R.id.tv_addlost_set);
        builder.setView(view);
        Dialog d = builder.create();
        mySetOnclick(tv_get, edt_add_content, edt_add_contact, d, "1");
        mySetOnclick(tv_set, edt_add_content, edt_add_contact, d, "0");

        builder.show();

    }

    //设置editText自动换行
    public void setMultiLine(EditText editText) {
        //设置EditText的显示方式为多行文本输入
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        editText.setGravity(Gravity.TOP);
        //改变默认的单行模式
        editText.setSingleLine(false);
        //水平滚动设置为False
        editText.setHorizontallyScrolling(false);
        editText.setMaxLines(10);
    }

    public void mySetOnclick(TextView tv, final EditText tent, final EditText tact, final Dialog d, final String type) {


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tent.getText()) || TextUtils.isEmpty(tact.getText())) {
                    Snackbar.make(findViewById(R.id.tv_nodata), "不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String content = tent.getText().toString();
                String contact = tact.getText().toString();
                String name = "无名氏";
                final String url = APPConst.URL + "MVS/servlet/APPLostServlet?type=1&name=" + name +
                        "&content=" + content + "&contact=" + contact + "&ltype=" + type;
                RequestQueue queue = Volley.newRequestQueue(LostActivity.this);

                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("no")) {
                            Snackbar.make(findViewById(R.id.tv_nodata), "服务器错误", Snackbar.LENGTH_SHORT).show();
                        } else if (response.equals("yes")) {
                            Snackbar.make(findViewById(R.id.tv_nodata), "提交成功", Snackbar.LENGTH_SHORT).show();
                            d.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(findViewById(R.id.tv_nodata), "连接出错，请检查设置", Snackbar.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);
            }
        });
    }
}
