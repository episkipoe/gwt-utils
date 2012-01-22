package com.episkipoe.common.rooms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;

public abstract class SlideshowRoom extends Room {
	/**
	 * @return  the room to return to after the last slide
	 */
	abstract protected Class<? extends Room> getExitRoom();
	/**
	 * called after changing slides
	 */
	protected void loadSlide() { }

	final public void onLoad() {
		addExit(new NextButton());
		addExit(new BackButton());
	}

	final public void onEnter() {
		currentSlide=0;
		roomChanged();
	}
	
	final private void roomChanged() {
		setBackground(slides.get(currentSlide));
		clearDrawables();
		loadSlide();
	}
	
	private List<String> slides = new ArrayList<String>();
	protected final void addSlide(String fileName) { 
		slides.add(fileName);
	}
	
	@Override
	public final Collection<String> getRequiredImages() {
		return slides;
	}
	
	protected int currentSlide=0;

	private void next() {
		currentSlide++;
		if(currentSlide >= slides.size()) {
			Game.switchRoom(getExitRoom());
			return;
		}
		roomChanged();
	}
	private void back() {
		currentSlide--;
		if(currentSlide < 0) {
			Game.switchRoom(getExitRoom());
			return;
		}
		roomChanged();
	}
	private class NextButton extends ImageDrawable {
		private NextButton() { 
			setFilename("RightArrow.png");
			setLocation(new Point(700, 500));
		}
		@Override
		public void click() throws Exception { next(); }
	}
	
	private class BackButton extends ImageDrawable {
		private BackButton() { 
			setFilename("LeftArrow.png");
			setLocation(new Point(100, 500));
		}
		@Override
		public void click() throws Exception { back(); }
	}
}
