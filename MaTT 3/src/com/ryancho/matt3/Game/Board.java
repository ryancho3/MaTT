package com.ryancho.matt3.Game;

public class Board {

	private Dish[] dishes;
	private int hand;
	private int score0;
	private int score1;
	private int turn;

	private int deltaScore;

	public Board() { // constructor
		dishes = new Dish[12];

		turn = 1;
		for (int i = 0; i < 6; i++) {
			dishes[i] = new Dish(0, i);

		}

		for (int i = 6; i < 12; i++) {
			dishes[i] = new Dish(1, i);
		}

		deltaScore = 0;
	}

	// accessors

	public Dish[] getDishes() {
		return dishes;
	}

	public int getScore0() {
		return score0;
	}

	public int getScore1() {
		return score1;
	}

	public int getTurn() {
		return turn;
	}

	// mutators

	public void setDishes(Dish[] d) {
		dishes = d;
	}

	public void setTurn(int t) {
		turn = t;
	}

	public void nextTurn() {
		if (turn == 0) {
			turn = 1;
		} else if (turn == 1) {
			turn = 0;
		}
	}

	public void setScore(int p, int x) {
		if (p == 0) {
			score0 = x;
		} else if (p == 1) {
			score1 = x;
		}
	}

	// gameplay

	public int nextDishIndex(int i) {
		int ret;
		ret = i + 1;
		if (ret > 11) {
			ret -= 12;
		}

		return ret;

	}

	public void nextDishIncrement(int i) {
		dishes[nextDishIndex(i)].increment();
	}

	public void pick(int index) {
		/*
		 * hand = dishes[index].getStones(); int manual = index + 1;
		 * dishes[index].setStones(0); if (manual > 11) { manual = 0; } for (int i = 0;
		 * i < hand; i++) { dishes[manual].increment(); manual++; if (manual > 11) {
		 * manual = 0; } }
		 * 
		 * if (manual == 0) { manual = 12; }
		 * 
		 * if (dishes[manual - 1].getStones() == 1) {
		 * 
		 * take(manual - 1); nextTurn(); }
		 */

		deltaScore = 0;
		int indexFinal = 0;
		int adjustedIndex = 0;
		hand = dishes[index].getStones();
		dishes[index].setStones(0);
		for (int i = 0; i < hand; i++) {
			adjustedIndex = index + i;
			nextDishIncrement(adjustedIndex);
			indexFinal = nextDishIndex(adjustedIndex);
		}
		if (dishes[indexFinal].getStones() == 1) {
			take(indexFinal);
			if (deltaScore > 0) {
			nextTurn();
			}
			
		}
	}

	public void take(int index) {

		

		if (dishes[dishes[index].oppIndex()].getStones() != 0) {

			
			dishes[index].setStones(0);
			if (turn == 0) {
				deltaScore = dishes[dishes[index].oppIndex()].getStones() + 1;
				score0 += deltaScore;
				

			}
			if (turn == 1) {
				deltaScore = dishes[dishes[index].oppIndex()].getStones() + 1;
				score1 += deltaScore;
				

			}
			dishes[dishes[index].oppIndex()].setStones(0);
			debugPrint();
		}
	}

	public void print() {
		System.out.println();

		System.out.print("         ");
		for (int i = 0; i < 6; i++) {
			System.out.print(" " + dishes[i].getIndex() + "  ");
		}
		System.out.println();
		System.out.print("Player 0 ");
		for (int i = 0; i < 6; i++) {
			System.out.print("|" + dishes[i].getStones() + "|" + " ");
		}

		System.out.println();
		System.out.print("Player 1 ");

		for (int i = 11; i > 5; i--) {
			System.out.print("|" + dishes[i].getStones() + "|" + " ");
		}
		System.out.println();
		System.out.print("        ");
		for (int i = 11; i > 5; i--) {
			System.out.print(" " + dishes[i].getIndex() + "  ");
		}
		System.out.println();
		System.out.println("Score: ");
		System.out.println("Player 0: " + score0);
		System.out.println("Player 1: " + score1);
	}

	public void debugPrint() {
		System.out.println();

		System.out.print("Player 0 ");
		for (int i = 0; i < 6; i++) {
			System.out.print("|" + dishes[i].getStones() + "|" + " ");
		}

		System.out.println();
		System.out.print("Player 1 ");

		for (int i = 11; i > 5; i--) {
			System.out.print("|" + dishes[i].getStones() + "|" + " ");
		}
		System.out.println();

	}

	public boolean checkRun() {
		int sum = 0;

		for (int i = 0; i < 6; i++) {
			sum += dishes[i].getStones();
		}

		if (sum == 0) {
			return false;
		}

		sum = 0;

		for (int i = 6; i < 12; i++) {
			sum += dishes[i].getStones();
		}

		if (sum == 0) {
			return false;
		}

		return true;
	}

	// MaTT Assistance

	public int deltaScore() {

		return deltaScore;
	}

}
