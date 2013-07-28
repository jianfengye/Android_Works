package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ReferenceActivity extends Activity {
	
	public static final String IS_SAVE_LOCAL = "IS_SAVE_LOCAL";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);
		
		final CheckBox checkbox_saveLocal = (CheckBox) findViewById(R.id.saveLocal);
		
		Context context = getApplicationContext();
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isSaveLocal = prefs.getBoolean(IS_SAVE_LOCAL, false);
		checkbox_saveLocal.setChecked(isSaveLocal);
		
		Button saveReference = (Button)findViewById(R.id.save);
		saveReference.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View view) {
				
				boolean isChecked = checkbox_saveLocal.isChecked();
				
				Editor editor = prefs.edit();
				editor.putBoolean(IS_SAVE_LOCAL, isChecked);
				editor.commit();	
				ReferenceActivity.this.setResult(RESULT_OK);
			}
		});
		
		Button cancelReference = (Button)findViewById(R.id.cancel);
		cancelReference.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	    		intent.setClass(ReferenceActivity.this, ToDoListActivity.class);
	    		startActivity(intent);
			}
		});
	}

}
