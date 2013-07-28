package com.example.todolist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ToDoContentProvider extends ContentProvider {
	private MySQLiteOpenHelper myOpenHelper;
	
	// 发布一个URI地址
	public static final Uri CONTENT_URI = 
			Uri.parse("content://com.example.todolist.todocontentprovider/todoitems");

	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// 构造底层的数据库
		this.myOpenHelper = new MySQLiteOpenHelper(getContext(), MySQLiteOpenHelper.DATABASE_NAME, null, 
				MySQLiteOpenHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
