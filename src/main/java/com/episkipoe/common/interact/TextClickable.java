package com.episkipoe.common.interact;

import java.util.List;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.TextUtils;
import com.google.gwt.canvas.dom.client.Context2d;

public class TextClickable implements Clickable, Drawable {
	private List<String> message;
	private Point location; 
	Runnable action;	
	
	public TextClickable(List<String> message, Point location, Runnable action) {
		this.message=message;
		this.location=location; 
		this.action=action;	
	}

	private String backgroundStyle="rgba(255,255,255,0.8)";
	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}
	private String textStyle="rgba(0,0,0,1)";
	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}
	
	
	@Override
	public void draw(Context2d context) {
		TextUtils.drawText(context, message, location, backgroundStyle, textStyle);
	}

	@Override
	public boolean intersectsWith(Point point) {
		return TextUtils.overlaps(Game.context, location, message, point);
	}

	@Override
	public void click() throws Exception {
		if(action != null) action.run();
	}

	private boolean continueProcessing=true;
	public void stopProcessing() { continueProcessing=false; }
	@Override
	public boolean continueProcessing() { return continueProcessing; }
}
