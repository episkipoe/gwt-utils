package com.episkipoe.common.interact;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.google.gwt.canvas.dom.client.Context2d;

public class BackgroundClickable implements Clickable, Drawable {
	float x1, x2, y1, y2;
	public BackgroundClickable(Point first, Point second) {
		x1 = (first.x < second.x ? first.x : second.x);
		x2 = (first.x > second.x ? first.x : second.x);
		y1 = (first.y < second.y ? first.y : second.y);
		y2 = (first.y > second.y ? first.y : second.y);
	}
	
	public BackgroundClickable(Point center, float width, float height) {
		x1 = center.x - width;
		y1 = center.y - height;
		x2 = center.x + width;
		y2 = center.y + height;
	}

	@Override
	public boolean intersectsWith(Point point) {
		if(point.x < x1) return false;
		if(point.x > x2) return false;
		if(point.y < y1) return false;
		if(point.y > y2) return false;		
		return true;
	}

	Runnable action;
	public void setAction(Runnable action) {
		this.action = action;
	}
	
	@Override
	public void click() {
		if(action==null) {
			System.out.println("WARNING: clickable with null action");
			return;
		}
		action.run();
	}

	@Override
	public void draw(Context2d context) {
		context.setStrokeStyle("rgba(255,255,255,0.75)");
		context.strokeRect(x1, y1, x2-x1, y2-y1);
	}

	@Override
	public boolean continueProcessing() { return true; }


}
