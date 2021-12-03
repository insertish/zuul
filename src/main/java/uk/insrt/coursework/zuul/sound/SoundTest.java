package uk.insrt.coursework.zuul.sound;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundTest {
    public static void main(String[] args) {
        TinySound lib = TinySound.init();
		//load a sound and music
		//note: you can also load with Files, URLs and InputStreams
		Music song = lib.loadMusic("sounds/crashing-waves.wav");
		Sound coin = lib.loadSound("sounds/money-bag.wav");
		//start playing the music on loop
		song.play(true);
		//play the sound a few times in a loop
		for (int i = 0; i < 20; i++) {
			coin.play();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		//be sure to shutdown TinySound when done
		lib.shutdown();
    }
}
