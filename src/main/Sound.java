package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
		Clip clip;
		URL soundURL[] =new URL[30]; //used to store the file paths
		
		
		public Sound() {
			soundURL[0] = getClass().getResource("/sound/theme.wav");
			soundURL[1] = getClass().getResource("/sound/key.wav");
			soundURL[2] = getClass().getResource("/sound/door.wav");
			soundURL[3] = getClass().getResource("/sound/theme.wav");
			soundURL[4] = getClass().getResource("/sound/theme.wav");
			soundURL[5] = getClass().getResource("/sound/moveSlot.wav");
			soundURL[6] = getClass().getResource("/sound/shotgun.wav");
			
			
		}
		
		public void setFile(int i) {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);
				
			}catch(Exception e) {
				
			}
		}
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		public void start() {
			clip.start();
		}
		
		public void stop() {
			clip.stop();
		}

}
