package com.episkipoe.common.interact;

import com.episkipoe.common.Point;

public class BackgroundAction extends BackgroundClickable {
	public BackgroundAction(Point first, Point second, Runnable action) {
		super(first, second);
		setAction(action);
	}
}
