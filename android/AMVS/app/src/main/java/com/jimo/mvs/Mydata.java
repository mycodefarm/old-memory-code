package com.jimo.mvs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by TR on 2016/8/3.
 */
public class Mydata {
    private  Context ctn;
    public Mydata(Context context){
        ctn=context;
    }
    public void insert(int year, int month, int monthday,String file)// 往数据库里放数据，注意放入的顺序
    {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(ctn,
                file);
        SQLiteDatabase sqlite = helper.getWritableDatabase();
        //sqlite.execSQL("CREATE TABLE signinfo2 (year varchar(20), month varchar(20),monthday varchar(20),sign varchar(20))");
        ContentValues values = new ContentValues();

        values.put("year", String.valueOf(year));
        values.put("month", String.valueOf(month));
        values.put("monthday", String.valueOf(monthday));
        sqlite.insert(file, null, values);
    }

    public String getData(String file)// 从数据库取出数据，一条数据放一行，最后由字符串返回表中所有内容，注意返回数据顺序和插入数据顺序相同
    {
        StringBuilder str = new StringBuilder();
        DatabaseOpenHelper helper = new DatabaseOpenHelper(ctn,
                file);
        SQLiteDatabase sqlite = helper.getWritableDatabase();
        Cursor cursor = sqlite.query(file, new String[]{"year", "month",
                "monthday", "sign"}, null, new String[]{}, null, null, null);
        while (cursor.moveToNext()) {
            String v1 = cursor.getString(cursor.getColumnIndex("year"));
            String a = cursor.getString(cursor.getColumnIndex("month"));
            String pa = cursor.getString(cursor.getColumnIndex("monthday"));
            // System.out.println("V-->"+v1+",A-->"+a+",Pa-->"+pa+",P0-->"+p0);
            str.append(v1 + "," + a + "," + pa+ ";");
        }
        return str.toString();
    }
    public void clearsql(String file){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(ctn,
                file);
        SQLiteDatabase sqlite = helper.getWritableDatabase();
        sqlite.execSQL("DELETE FROM "+file);

    }
}
