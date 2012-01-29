package com.episkipoe.common.sound;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

public class SoundLibrary {
	SoundController soundController = new SoundController();
	Map<String,Sound> sounds = new HashMap<String,Sound>();
	public SoundLibrary() { }

	public void stopAll() {
		for(Entry<String,Sound> it : sounds.entrySet()) {
			it.getValue().stop();
		}
	}
	public Sound play(String fileName) {
		Sound sound = get(fileName);
		if(sound != null) {
			sound.play();
			return sound;
		}
		return load(fileName);
	}
	
	public Sound get(String fileName) {
		if(fileName == null || fileName.isEmpty()) return null;
		if(!sounds.containsKey(fileName)) {
			return null;
		}
		return sounds.get(fileName);	
	}
	
	public Sound load(String fileName) {
    	String type;
	    if(fileName.endsWith("mp3")) {
	    	type = Sound.MIME_TYPE_AUDIO_MPEG_MP3;
	    } else {
		    type = Sound.MIME_TYPE_AUDIO_WAV_UNKNOWN;
	    }
		Sound sound = soundController.createSound(type, "sound/"+fileName, false, false);	
	    sounds.put(fileName, sound);
	    return sound;
	}
	
	public void load(Collection<String> fileNames) {
		Set<String> fileSet = new HashSet<String>();
		for(String f: fileNames) fileSet.add(f);
		for(String f: fileSet) load(f);
	}
}
