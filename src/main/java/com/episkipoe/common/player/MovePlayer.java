package com.episkipoe.common.player;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.MouseMode;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

public class MovePlayer extends MouseMode {
	public MouseDownHandler mouseDownHandler() {
		return new MouseDownHandler() { 
			@Override
			public void onMouseDown(MouseDownEvent event) {
				Point loc = Game.getPointFromEvent(event);
				try {
					Game.click(loc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};	
	}
	public MouseMoveHandler mouseMoveHandler() {
		return new MouseMoveHandler() { 
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				Point point = Game.getPointFromEvent(event);
				Game.player.setLocation(point);
				if(Game.pointIsClickable(point)) {
					Game.player.setAlpha(0.1);
				} else {
					Game.player.setAlpha(1.0);
				}
			}
		};	
	}
}
