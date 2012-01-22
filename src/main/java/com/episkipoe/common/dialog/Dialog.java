package com.episkipoe.common.dialog;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 *  A collection of {@link DialogElement}s
 *
 */
public class Dialog implements Drawable {
	public static final int DEFAULT_DURATION = 70;
	private Queue<DialogElement> elements;
	public Dialog() { 
		elements = new LinkedList<DialogElement>();
	}
	public Dialog(DialogElement element) { 
		elements = new LinkedList<DialogElement>();
		elements.add(element);
	}

	public void add(DialogElement element) { 
		elements.add(element); 
	}
	public void add(List<String> message, Point location) {
		elements.add(new DialogElement(message, location));
	}
	
	public void add(List<String> message, Speaker speaker) {
		elements.add(speaker.say(message, DEFAULT_DURATION));
	}
	
	public void add(List<String> message, Speaker speaker, int duration) {
		elements.add(speaker.say(message, duration));
	}	

	/**
	 * cancels any dialog in progress
	 * @param element
	 */
	public void say(DialogElement element) { 
		clear();
		elements.add(element); 
	}
	
	public void clear() {
		elements.clear();
	}
	
	@Override
	public void draw(Context2d context) {
		if(elements.peek() == null) return;
		DialogElement front = elements.peek();
		front.draw(context);
		if(front.expired()) { elements.remove(); }
	}
	
	public boolean isEmpty() {
		return elements.isEmpty();
	}


}
