package com.episkipoe.common.sound;

import com.episkipoe.common.Game;

public class SoundUtils {
	public static void play(String fileName) {
		Game.sounds.play(fileName);
	}
}
