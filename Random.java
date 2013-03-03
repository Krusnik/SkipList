package main;

public class Random implements Randomaze{

	public boolean newLevel() {
		return Math.random() < 0.25 ? true : false;
	}
}
