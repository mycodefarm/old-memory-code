package myutil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import bean.MyMessage;
import bean.Result;

/**
 * Created by dell on 2016/5/2.
 */
public class HttpUtil {

    private final static String APIKEY = "your KEY";
    private final static String URL = "http://www.tuling123.com/openapi/api";


    /**
     * 发送一个消息，得到返回的消息并封装在MyMessage里
     * @param msg
     * @return
     */
    public static MyMessage sendMessage(String msg){

        MyMessage myMessage = new MyMessage();
        String jsonRes = doGet(msg);
        Gson gson = new Gson();
        Result result = null;

        try {
            result = gson.fromJson(jsonRes,Result.class);
            myMessage.setmsg(result.getText());
        } catch (JsonSyntaxException e) {
            myMessage.setmsg("服务器繁忙");
        }

        myMessage.setDate(new Date());
        myMessage.setType(MyMessage.Type.INCOMING);
        return myMessage;
    }

    /**
     * get 方式获取到恢复
     * @param msg
     * @return
     */
    public static String doGet(String msg){
        String result = "";

        String url = setParams(msg);
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
            java.net.URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5*1000);
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            is = conn.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();
            while(-1!=(len = is.read(buf))){
                baos.write(buf,0,len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=baos){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private static String setParams(String msg) {
        String url = "";
        try {
            url = URL+"?key="+APIKEY+"&info="+ URLEncoder.encode(msg,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
