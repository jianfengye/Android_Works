package com.example.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.database.Cursor;
import android.app.LoaderManager;

@SuppressLint("NewApi")
public class ToDoListActivity extends Activity
	implements  LoaderManager.LoaderCallbacks<Cursor>{
	
	//获得对UI小组件的引用
    private ArrayList<String> todoItems;
    private ArrayAdapter<ToDoItem> aa;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView myListView = (ListView)findViewById(R.id.myListView);
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);
        
        todoItems = new ArrayList<String>();
        
        aa = new ArrayAdapter<ToDoItem>(this, resID, todoItems);
        
        myListView.setAdapter(aa);
        
        myEditText.setOnKeyListener(new View.OnKeyListener() {
		
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN)
					if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
							(keyCode == KeyEvent.KEYCODE_ENTER)) {
						todoItems.add(0, myEditText.getText().toString());
						aa.notifyDataSetChanged();
						myEditText.setText("");
						return true;
					}
				return false;
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.manu_reference:
    		Intent intent = new Intent();
    		intent.setClass(this, ReferenceActivity.class);
    		startActivity(intent);
    	}
    	return true;
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, ToDoContentProvider.CONTENT_URI,
				null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// 当loader查询完成的时候，Cursor会返回到onLoadFinished处理程序。
		int keyTaskIndex = cursor.getColumnIndexOrThrow(MySQLiteOpenHelper.KEY_TASK);
		
		todoItems.clear();
		while (cursor.moveToNext()) {
			ToDoItem newItem = new ToDoItem(cursor.getString(keyTaskIndex));
			//TODO
			//todoItems.add(newItem);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
