package com.episkipoe.common.interact;

import com.episkipoe.common.Point;

public interface Clickable {
	public boolean intersectsWith(Point point);
	public void click() throws Exception;
	public boolean continueProcessing();
}
