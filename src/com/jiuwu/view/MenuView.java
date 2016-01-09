package com.jiuwu.view;

import com.jiuwu.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * 该自定义View主要是画出三个点的自定义View，多用于菜单之用
 * @author xiaojunLi
 *其中还可以设置这三个点的颜色，以及被点击时的背景颜色
 *
 */
public class MenuView extends View {

	private Paint p;
	//被按下时的背景颜色
	private String pressBackgroundColor;
	private Context context;
	
	public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public MenuView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void init(){
		//初始化画笔，并设置相关属性
		p = new Paint();
		p.setStrokeWidth(7f);
		p.setColor(Color.WHITE);
		pressBackgroundColor = "#50000000";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//获取此View相对于父容器的X坐标
		int x = getLeft();
		//获取此View自身的宽度
		int selfWidth = getWidth();
		//获取此View自身的高度
		int selfHeight = getHeight();
		//在给定的画布上画出来，，其中的x参数是已经加上了父容器给定的x坐标，所以不用再加上getLeft()的值了
		canvas.drawPoint((selfWidth/2), (int)(selfHeight*(2.5/7.0)), p);
		canvas.drawPoint((selfWidth/2), (int)(selfHeight*(3.5/7.0)), p);
		canvas.drawPoint((selfWidth/2), (int)(selfHeight*(4.5/7.0)), p);
	}

	private int width;
	private int height;
	//重写onMeasure方法，主要是设置width
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//获取宽度的模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		//获取父容器给这个View的高度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		//获得此View的高度
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		//获得此View的高度模式
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		//如果父容器给此View的值为WrapContent，那就采用自己设置的值
		if(widthMode == MeasureSpec.AT_MOST){
			//取父容器给的最大宽度和80中较小的值设置为此View的宽度
			width = Math.min(widthSize, Utils.getInstance().getWindowWidth(context)/8);
		}
		//否则如果父容器传入的是精确值（matchParent或者具体值）
		else if(widthMode == MeasureSpec.EXACTLY){
			width = widthSize;
		}
		
		if(heightMode == MeasureSpec.AT_MOST){
			height = Math.min(heightSize, 83);
		}else if(heightMode == MeasureSpec.EXACTLY){
			height = heightSize;
		}
		setMeasuredDimension(width, height);
	}

	
	//重写onTouch实现点击的时候改变背景颜色以及透明度
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			this.setBackgroundColor(Color.parseColor(pressBackgroundColor));
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			this.setBackgroundColor(Color.parseColor("#00000000"));
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 提供设置画笔颜色的方法供外部调用
	 * @param color 16进制的颜色值String类型
	 */
	public void setColor(String color){
		try{
		if(p!=null){
			p.setColor(Color.parseColor(color));
			invalidate();
		}
		}catch(Exception e){
			return;
		}
	}
	
	/**
	 * 提供外部方法设置按下的时候的背景颜色
	 * @param color 16进制的颜色值String类型
	 */
	public void setPressBackgroundColor(String color){
		pressBackgroundColor = color;
		invalidate();
	}
	
}
