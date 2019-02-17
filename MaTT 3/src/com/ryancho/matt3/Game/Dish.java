package com.ryancho.matt3.Game;

public class Dish {
	// Class Variables
	private int player;
	private int index;
	private int stones;

	// Constructor

	public Dish() { // default constructor
		player = 0;
		index = 0;
		stones = 4;
	}

	public Dish(int i) {
		stones = 4;
		index = i;
		player = 0;
	}
	
	public Dish(int x, int y) {
		player = x;
		index = y;
		stones = 4;
	}

	public Dish(int x, int y, int z) {
		player = x;
		index = y;
		stones = z;
	}

	// Accessors

	public int getPlayer() {
		return player;
	}

	public int getIndex() {
		return index;
	}

	public int getStones() {
		return stones;
	}

	public boolean isEmpty() {

		if (stones == 0) {
			return true;
		}

		return false;
	}

	// Mutators

	public void setPlayer(int x) {
		player = x;
	}

	public void setIndex(int y) {
		index = y;
	}

	public void setStones(int z) {
		stones = z;
	}
	
	public void increment() {
		stones ++;
	}

	// functionality

	public int oppIndex() {

		return Math.abs(index - 11);

	}
	

}
