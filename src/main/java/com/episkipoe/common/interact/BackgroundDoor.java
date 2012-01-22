package com.episkipoe.common.interact;

import com.episkipoe.common.Point;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.common.rooms.SwitchRoom;

public class BackgroundDoor extends BackgroundClickable {
	public BackgroundDoor(Point first, Point second, Class<? extends Room> destination) {
		super(first, second);
		setAction(new SwitchRoom(destination));	
	}
	public BackgroundDoor(Point first, Point second, Runnable goToDestination) {
		super(first, second);
		setAction(goToDestination);
	}
}
