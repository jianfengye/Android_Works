package com.yejianfeng.switchhost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddHostActivity extends Activity {
	private HostFileOperator operator;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addhost);
		
		this.operator = new HostFileOperator(this.getApplicationContext());
	}
	
	// 点击保存按钮触发操作
	public void addHost(View v)
	{
		// 获取host名字
		EditText hostNameText = (EditText) findViewById(R.id.hostName);
		String hostName = hostNameText.getText().toString();
		
		// TODO： 检验名字是否重复
		
		// 获取host内容
		EditText hostContentText = (EditText) findViewById(R.id.hostContent);
		String hostContent = hostContentText.getText().toString();
		
		// 保存host文件
		this.operator.AddHost(hostName, hostContent);
		Log.v("Add Host Save", hostName);
		
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
