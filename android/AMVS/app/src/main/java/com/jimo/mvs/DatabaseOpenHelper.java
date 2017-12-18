package com.jimo.mvs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	private static final int VERSION =1;
	public DatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseOpenHelper(Context context, String name,
			 int version) {
		this(context, name, null, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseOpenHelper(Context context, String name
			) {
		this(context, name ,VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		    db.execSQL("CREATE TABLE signinfo (year varchar(20), month varchar(20),monthday varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
			System.out.println("���ݿ�汾����"+oldVersion+"-->"+newVersion);
	}
}
