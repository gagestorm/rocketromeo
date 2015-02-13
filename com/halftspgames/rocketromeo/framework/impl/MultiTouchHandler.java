package com.halftspgames.rocketromeo.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.halftspgames.rocketromeo.framework.Input;
import com.halftspgames.rocketromeo.framework.Input.TouchEvent;
import com.halftspgames.rocketromeo.framework.impl.Pool.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler{

	private static final int MAX_TOUCHPOINTS = 10;
	boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];
	Pool<TouchEvent> touchEventPool;
	ArrayList<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	
	
	public MultiTouchHandler(View view, float scaleX, float scaleY){
		PoolObjectFactory<Input.TouchEvent> factory = new PoolObjectFactory<Input.TouchEvent>() {

			@Override
			public Input.TouchEvent createObject() {
				// TODO Auto-generated method stub
				return new TouchEvent();
			}
		};	
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;	
	}
	
	
	
	
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)
					>> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent touchEvent;
			for(int i=0;i<MAX_TOUCHPOINTS;i++){
				if(i>=pointerCount){
					isTouched[i] = false;
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if(event.getAction()!=MotionEvent.ACTION_MOVE && i!=pointerIndex){
					continue;
				}
				switch(action){
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					//touchEvent.type = TouchEvent.TOUCH_DOWN;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int)(event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int)(event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_UP;    //bug
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int)(event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int)(event.getY(i) * scaleY);
					isTouched[i] = false;
					id[i] = -1;
					touchEventsBuffer.add(touchEvent);
					break;
				case MotionEvent.ACTION_MOVE:
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerId;
					touchEvent.x = touchX[i] = (int)(event.getX(i) * scaleX);
					touchEvent.y = touchY[i] = (int)(event.getY(i) * scaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					touchEventsBuffer.add(touchEvent);
					break;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int index = getIndex(pointer);
			if(index<0 || index>=MAX_TOUCHPOINTS)
				return false;
			else
				return isTouched[index];
		}
	}

	private int getIndex(int pointerId) {
		// TODO Auto-generated method stub
		for(int i=0;i<MAX_TOUCHPOINTS;i++){
			if(id[i]==pointerId){
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int index = getIndex(pointer);
			if(index<0 || index>=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		synchronized (this) {
			int index = getIndex(pointer);
			if(index<0 || index>=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		synchronized (this) {
			int len = touchEvents.size();
			for(int i=0;i<len;i++)
				touchEventPool.free(touchEvents.get(i));
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

}
