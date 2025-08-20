package draylar.identity.util;

public class TimeUtils {

	public static int ticksToSeconds(int ticks) {
		int seconds = 0;
		if(ticks % 20 == 0) seconds = ticks / 20;
		return seconds;
	}
	
}
