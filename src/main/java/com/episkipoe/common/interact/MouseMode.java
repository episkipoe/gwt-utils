package com.episkipoe.common.interact;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;

public abstract class MouseMode {

	public MouseMoveHandler mouseMoveHandler() {
		return null;
	}

	public MouseUpHandler mouseUpHandler() {
		return null;
	}

	public MouseDownHandler mouseDownHandler() {
		return null;
	}
	
}
