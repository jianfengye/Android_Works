package com.example.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class ToDoListItemView extends TextView {
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;
	
	public ToDoListItemView(Context context, AttributeSet ats, int ds) {
		super(context, ats, ds);
		init();
	}
	
	public ToDoListItemView (Context context) {
		super(context);
		init();
	}
	
	public ToDoListItemView (Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		// 获得对资源表的引用
		Resources myResources = getResources();
		
		// 创建将在onDraw方法中使用的画刷
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResources.getColor(R.color.notepad_lines));
		
		// 获得页面背景色和边缘宽度
		paperColor = myResources.getColor(R.color.notepad_paper);
		margin = myResources.getDimension(R.dimen.notepad_margin);
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
}
