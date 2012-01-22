package com.episkipoe.common.dialog;

import java.util.Arrays;
import java.util.Collection;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.Clickable;
import com.google.gwt.canvas.dom.client.Context2d;

public class DialogElement implements Drawable, Clickable {
	private Collection<String> message;
	private Point location; 
	private int duration;
	Runnable postAction;
	
	public DialogElement(Collection<String> message, Point location, int duration) {
		this.message=message;
		this.location=location; 
		this.duration=duration;	
	}
	public DialogElement(String message, Point location, int duration) {
		this.message=Arrays.asList(message);
		this.location=location; 
		this.duration=duration;	
	}
	public DialogElement(Collection<String> msg, Point location) {
		this.message=msg;
		this.location=location; 
		this.duration=-1;	
	}
	
	public void setPostAction(Runnable postAction) {
		this.postAction = postAction;
	}
	
	public boolean expired() {
		return duration==0;
	}

	@Override
	public void draw(Context2d context) {
		if(expired()) return; 
		duration--;
		if(duration == 0 && postAction != null) { postAction.run(); }
		TextUtils.drawText(context, message, location, "rgba(0,0,0,0.75)", "rgba(255,255,255,1)");
	}
	@Override
	public boolean intersectsWith(Point point) {
		return TextUtils.overlaps(Game.context, location, message, point);
	}
	@Override public void click() throws Exception { }
	@Override public boolean continueProcessing() { return true; }
}
