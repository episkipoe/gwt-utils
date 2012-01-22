package com.episkipoe.common.draw;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Anything that has a location and can be drawn on a canvas
 */
public interface Drawable {
	public void draw(Context2d context);
}
