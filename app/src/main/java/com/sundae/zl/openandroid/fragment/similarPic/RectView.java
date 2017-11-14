package com.sundae.zl.openandroid.fragment.similarPic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by @author hzzhoulong
 * on 2017-10-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public class RectView extends View {

	float lastX;

	float lastY;

	float initX;

	float initY;

	Paint paint;

	public interface Listener{
		void onRect(Rect rect);
	}
	Listener listener;

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public RectView(Context context) {
		super(context);
		inits();
	}

	Rect rect;
	private void inits() {

		rect = new Rect();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);

	}

	public RectView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inits();

	}

	public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inits();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:

				int x,y;
				lastX = event.getRawX();
				lastY = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				float curX = event.getRawX();
				float curY = event.getRawY();
				int dx = (int) (curX - lastX);
				int dy = (int) (curY - lastY);

				x = getLeft() + dx;
				y = getTop() + dy;
				layout(x, y, x + getWidth(), y + getHeight());
				lastX = curX;
				lastY = curY;
				if (listener != null) {
					getGlobalVisibleRect(rect);
					listener.onRect(rect);
				}
				break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}

	public Rect getCor(){
		Rect r = new Rect();
		getGlobalVisibleRect(r);
		return r;
	}
}
