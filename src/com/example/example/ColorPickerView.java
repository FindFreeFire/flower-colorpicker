package com.example.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ColorPickerView extends RelativeLayout{

	private Paint paint;
	private float x;
	private float y;

	public ColorPickerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ColorPickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		if(x!=0){
			paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			canvas.drawCircle(x, y, 10, paint);
			CommonAPI.PrintLog("x="+x+"  y="+y, 1);
		}
	}
	
	public void  setPoint(float x,float y ){
		this.x=x;
		this.y=y;
	}
}
