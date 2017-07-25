package com.example.example;



import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Dialog dialog;
	private boolean isLightOn;
	int selectedColor;
	int selectedArc;//白点的角度
	private GradientDrawable myGrad;
	private TextView mLight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void BtnColor(View v) {
		popupColorPicker(v);
	}

	public void popupColorPicker(View v) {

		final int side = CommonAPI.getInstance().dip2px(this, 250);

		dialog = new Dialog(this, R.style.SeekArcDialogTheme);
		LayoutInflater inflater  = getLayoutInflater();
		View colorView = inflater.inflate(R.layout.card_light_dialog_color, null);
		mLight=(TextView) colorView.findViewById(R.id.tv_dialog_lightName);

		//颜色选择背景图片
		final ColorPickerView colorPickerView = (ColorPickerView) colorView.findViewById(R.id.color_picker_container);
		CommonAPI.PrintLog("popupColor", 0);
		final ImageView imgColorPicker = (ImageView) colorView.findViewById(R.id.img_color_picker);
		final View viewColor = colorView.findViewById(R.id.view_color);
		myGrad = (GradientDrawable)viewColor.getBackground();  

		final RelativeLayout rlColor = (RelativeLayout) colorView.findViewById(R.id.rl_color);
		final RelativeLayout rlColorIllumination = (RelativeLayout) colorView.findViewById(R.id.rl_color_illumination);
		final int[] location = new int[2];  
		// SeekArc Dialog添加了点击可关闭自身的按键


		rlColor.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					//根据角度来更改中心灯泡背景色
					if(isLightOn){
						myGrad.setColor(getResources().getColor(R.color.card_light_off));
						mLight.setTextColor(getResources().getColor(R.color.card_light_off));
						isLightOn = false;
					}else{
						if(selectedColor!=0){
							myGrad.setColor(selectedColor);
							mLight.setTextColor(selectedColor);
							rlColorIllumination.setVisibility(View.GONE);
							rlColor.setVisibility(View.VISIBLE);
						}else{
							rlColor.setVisibility(View.GONE);
							mLight.setTextColor(Color.BLACK);
							rlColorIllumination.setVisibility(View.VISIBLE);
						}
						isLightOn = true;
					}
				}
				return true;
			}
		});
		rlColorIllumination.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isLightOn){
					rlColorIllumination.setVisibility(View.GONE);
					rlColor.setVisibility(View.VISIBLE);
					myGrad.setColor(getResources().getColor(R.color.card_light_off));
					mLight.setTextColor(getResources().getColor(R.color.card_light_off));
					isLightOn=false;
				}else{
					isLightOn=true;
				}
			}
		});
		imgColorPicker.setOnTouchListener(new OnTouchListener() {

			private Point dpCenter;
			private float xAdd;
			private float yAdd;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction() == MotionEvent.ACTION_MOVE){
					setColorByTouch(event);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){
					setColorByTouch(event);
				}
				return true;
			}
			//根据移动位置来设置颜色
			private void setColorByTouch(MotionEvent event) {
				//得到右上角初始位置,可以用来计算中心点
				imgColorPicker.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				System.out.println("x:"+x+"y:"+y);  
				//计算得出中心点
				x=x+(side/2);
				y=y+(side/2);

				dpCenter = new Point(x, y);

				double x_offset = (event.getRawX()-dpCenter.x);
				double y_offset = (event.getRawY()-dpCenter.y);
				//初计算角度值
				double arc = 0;
				//实际夹角值
				double finalArc = 0;

				//计算两点夹角值
				if(Math.abs(y_offset)>0){
					arc = Math.atan(Math.abs(x_offset)/Math.abs(y_offset))
							/Math.PI*180;//初始arc值
				}
				//根据触击位置计算出实际夹角值
				//触击位置点击在圆点右边
				if(x_offset>0){
					if(y_offset<0){
						//触击位置在圆点右上
						finalArc = arc;
					}else if(y_offset>0){
						//触击位置在圆点右下
						finalArc = 180 - arc;
					}else if(y_offset==0){
						finalArc=90;
					}
				}else if ((x_offset<0)){
					//
					if(y_offset<0){
						//触击位置在圆点左上
						finalArc = 360 -arc;
					}else if(y_offset>0){
						//触击位置在圆点右下
						finalArc = 180 + arc;
					}else if(y_offset==0){
						finalArc=270;
					}
				}

				//根据最终夹角值来算得颜色值
				int pickerMagentaColor = getResources().getColor(R.color.picker_magenta);
				int pickerRedColor = getResources().getColor(R.color.picker_red);
				int pickerOrangeColor = getResources().getColor(R.color.picker_orange);
				int pickerCitrusColor = getResources().getColor(R.color.picker_citrus);
				int pickerLemonColor = getResources().getColor(R.color.picker_lemon);
				int pickerYellowColor = getResources().getColor(R.color.picker_yellow);
				int pickerGrassColor = getResources().getColor(R.color.picker_grass);
				int pickerTreeColor = getResources().getColor(R.color.picker_tree);
				int pickerSkyColor = getResources().getColor(R.color.picker_sky);
				int pickerSeaColor = getResources().getColor(R.color.picker_sea);

				int pickerPurpleColor = getResources().getColor(R.color.picker_purple);


				if(0<=finalArc&&finalArc<30){
					selectedColor = pickerMagentaColor;
					selectedArc=15;
				}else if(30<=finalArc&&finalArc<60){
					selectedColor = pickerRedColor;
					selectedArc=45;
				}else if(60<=finalArc&&finalArc<90){
					selectedColor = pickerOrangeColor;
					selectedArc=75;
				}else if(90<=finalArc&&finalArc<120){
					selectedColor = pickerCitrusColor;
					selectedArc=105;
				}else if(120<=finalArc&&finalArc<150){
					selectedColor = pickerLemonColor;
					selectedArc=135;
				}else if(150<=finalArc&&finalArc<180){
					selectedColor = pickerYellowColor;
					selectedArc=165;
				}else if(180<=finalArc&&finalArc<210){
					selectedColor = pickerGrassColor;
					selectedArc=195;
				}else if(210<=finalArc&&finalArc<240){
					selectedColor = pickerTreeColor;
					selectedArc=225;
				}else if(240<=finalArc&&finalArc<270){
					selectedColor = pickerSkyColor;
					selectedArc=255;
				}else if(270<=finalArc&&finalArc<300){
					selectedColor = pickerSeaColor;
					selectedArc=285;
				}else if(300<=finalArc&&finalArc<330){
					selectedColor = 0;//彩色区间
					selectedArc=315;
				}else if(330<=finalArc&&finalArc<360){
					selectedColor = pickerPurpleColor;
					selectedArc=345;
				}
				isLightOn=true;
				if(isLightOn){
					if(selectedColor!=0){
						myGrad.setColor(selectedColor);
						mLight.setTextColor(selectedColor);
						rlColorIllumination.setVisibility(View.GONE);
						rlColor.setVisibility(View.VISIBLE);
					}else{
						rlColor.setVisibility(View.GONE);
						mLight.setTextColor(Color.BLACK);
						rlColorIllumination.setVisibility(View.VISIBLE);
					}
				}
				/*
				 * 根据选择角度计算出,位置点,然后发送给自定义控件来重绘
				 */


				int bottom = imgColorPicker.getBottom();
				int top = imgColorPicker.getTop();
				int left = imgColorPicker.getLeft();
				int right = imgColorPicker.getRight();


				Point inParentPoint = new Point((right+left)/2, (bottom+top)/2);

				if(0<selectedArc&&selectedArc<90){

					xAdd = (float) ((side+90)/2*Math.sin(Math.toRadians(selectedArc)));
					yAdd = (float) ((side+90)/2*Math.cos(Math.toRadians(selectedArc)));

					xAdd = inParentPoint.x+xAdd;
					yAdd = inParentPoint.y-yAdd;
				}else if(90<selectedArc&&selectedArc<180){
					xAdd = (float) ((side+90)/2*Math.sin(Math.toRadians(180-selectedArc)));
					yAdd = (float) ((side+90)/2*Math.cos(Math.toRadians(180-selectedArc)));
					
					xAdd = inParentPoint.x+xAdd;
					yAdd = inParentPoint.y+yAdd;
				}else if(180<selectedArc&&selectedArc<270){
					yAdd = (float) ((side+90)/2*Math.sin(Math.toRadians(270-selectedArc)));
					xAdd = (float) ((side+90)/2*Math.cos(Math.toRadians(270-selectedArc)));
					
					xAdd = inParentPoint.x-xAdd;
					yAdd = inParentPoint.y+yAdd;
				}else if(270<selectedArc&&selectedArc<360){
					xAdd = (float) ((side+90)/2*Math.sin(Math.toRadians(360-selectedArc)));
					yAdd = (float) ((side+90)/2*Math.cos(Math.toRadians(360-selectedArc)));
					
					xAdd = inParentPoint.x-xAdd;
					yAdd = inParentPoint.y-yAdd;
				}
				
				colorPickerView.setPoint(xAdd , yAdd);
				colorPickerView.invalidate();
				mLight.invalidate();
			}
		});



		colorView.findViewById(R.id.tv_dialog_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		}); 

		dialog.setContentView(colorView);
		dialog.show();

	}
}
