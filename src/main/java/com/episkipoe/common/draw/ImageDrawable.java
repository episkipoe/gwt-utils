package com.episkipoe.common.draw;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.dialog.Dialog;
import com.episkipoe.common.dialog.DialogElement;
import com.episkipoe.common.dialog.Speaker;
import com.episkipoe.common.interact.Clickable;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public abstract class ImageDrawable implements Drawable, Clickable, Speaker {
	public ImageDrawable() { }
	public void postDraw(Context2d context) { }
	protected ImageAttributes attr = new ImageAttributes();
	@Override
	public boolean continueProcessing() { return true; }
	
	/*
	 * Final methods 
	 */
	
	/**
	 * the name of the current image file.  must be a valid entry in the {@link ImageLibrary}
	 */
	private String fileName;
	public final String getFilename() { return fileName; }
	public final void setFilename(String fileName) { this.fileName = fileName; }

	/**
	 * the current x,y position of the drawable on the canvas
	 */
	private Point location=null;
	public final Point getLocation() {
		if(location==null) location = new Point();
		return location;
	}
	public void move(int x, int y) {
		location.x += x;
		location.y += y;
	}
	public final void setLocation(Point location) { this.location = location; }
	
	private double alpha=1.0;
	public final void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public final ImageElement getImageElement() { 
		return Game.images.getImage(getFilename());	
	}
	
	public final void draw(Context2d context) {
		if(alpha<1.0) context.setGlobalAlpha(alpha);
		ImageUtils.draw(context, getFilename(), getLocation(), attr);
		if(alpha<1.0) context.setGlobalAlpha(1.0);
		postDraw(context);
	}	

	private class Rectangle {
		private Rectangle(double left, double right, double bottom, double top) {
			this.left = left;
			this.right = right;
			this.bottom = bottom;
			this.top = top;
		}
		public double left, right, bottom, top;
		public boolean intersectsWith(Rectangle other) {
			if(other.left > this.right) return false;
			if(other.right < this.left) return false;
			if(other.bottom > this.top) return false;
			if(other.top < this.bottom) return false;
			return true;
		}
	};

	public final boolean intersectsWith(Point point) {
		ImageElement img = getImageElement();
		if(img==null) return false;
		double left = getLocation().x-img.getWidth()*0.5;
		if(point.x < left) return false;
		double right = getLocation().x+img.getWidth()*0.5;
		if(point.x > right) return false;
		double top = getLocation().y+img.getHeight()*0.5;
		if(point.y > top) return false;
		double bottom = getLocation().y-img.getHeight()*0.5;
		if(point.y < bottom) return false;
		return true;
	}
	
	public Rectangle getBoundingBox() {
		ImageElement img = getImageElement();
		if(img==null) return null;
		double left = getLocation().x-img.getWidth()*0.5;
		double right = getLocation().x+img.getWidth()*0.5;
		double bottom = getLocation().y-img.getHeight()*0.5;
		double top = getLocation().y+img.getHeight()*0.5;
		return new Rectangle(left, right, bottom, top);
	}
	public final boolean intersectsWith(ImageDrawable other) {
		if(other==null) {
			System.out.println("WARNING: intersecting with a null object");
			return false;
		}
		return this.getBoundingBox().intersectsWith(other.getBoundingBox());
	}
	
	public final DialogElement say(String message) {
		return say(Arrays.asList(message), Dialog.DEFAULT_DURATION);
	}
	
	@Override
	public final DialogElement say(List<String> message, int duration) {
		ImageElement img = getImageElement();
		if(img==null) {
			System.out.println("WARNING:  Cannot say: " + message + " img is null");
			return null;
		}
		double x = getLocation().x+img.getWidth()*0.5;
		double y = getLocation().y-img.getHeight()*0.5;
		return new DialogElement(message, new Point(x,y), duration);
	}

	/**
	 * Say (at random) one out of the list of things that can be said
	 * @param message
	 * @return
	 */
	public final DialogElement say(List<List<String>> sayings) {
		Random rnd = new Random();
		int msgIdx = rnd.nextInt(sayings.size());
		return say(sayings.get(msgIdx), Dialog.DEFAULT_DURATION);
	}	
}
