package game.system;

import org.lwjgl.Sys;

public class Timing {
	
	private static long lastFrame = 0;
	
	/*
	 * Gets the system time and uses it to calculate time differences
	 */
	protected static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/*
	 * Gets the time difference between frames
	 */
	public int delta() {
		long currentTime = getTime();
		if (lastFrame == 0) {
			lastFrame = getTime();	
		}
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
}
