package com.yejianfeng.switchhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ModifyHostActivity extends Activity {
	private String curHostName;
	private HostFileOperator operator;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyhost);
		
		Intent curIntent = getIntent();
		this.curHostName = curIntent.getStringExtra("curHostName");
		
		TextView hostName = (TextView) findViewById(R.id.curHostName);
		hostName.setText(this.curHostName);
		
		this.operator = new HostFileOperator(this.getApplicationContext());
	}
	
	// 保存按钮触发的操作
	public void modifyHost(View v)
	{
		TextView hostContent = (TextView) findViewById(R.id.modifyHostContent);
		
		operator.ModifyHost(this.curHostName, hostContent.getText().toString());
		
		// 提示保存成功
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("保存成功");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
				
		// 返回到SwithHostActivity
		Intent intent = new Intent();
		intent.setClass(this, SwitchHostActivity.class);
		startActivity(intent);
	}
}
