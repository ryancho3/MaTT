package com.ryancho.matt3.Terminal;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.ryancho.matt3.*;
import com.ryancho.matt3.AI.MaTT3;
import com.ryancho.matt3.AI.MaTT3_1;
import com.ryancho.matt3.Game.Board;



public class M3Interface {

	public static void main(String[] args) {
		Board b = new Board();
		MaTT3_1 MaTT = new MaTT3_1(b,2);
		int selection;
		
		while (b.checkRun() == true) {
		

			if (b.getTurn() == 1) {
				b.print();
				System.out.println("User Selection: ");
				selection = selector(1);
				b.pick(selection);
				
				b.checkRun();

			}

			if (b.getTurn() == 0) {
			
				MaTT.buildTree(b);
				System.out.println("Depth: " + MaTT.getDepth(MaTT.getRoot(), 0));
				//b.pick(MaTT.decide(b));
				
				
			}
		}
		
		
	}
	
	
	public static int selector(int i) {
		int ret = 0;
		Scanner console = new Scanner(System.in);
		boolean testing = true;

		while (testing) {
			try {
				ret = console.nextInt();
				if (i == 0 && ret < 6) {
					testing = false;
				} else if (i == 1 && ret > 5 && i < 12) {
					testing = false;
				} else {
					System.out.println("Please select a dish from your side.");
				}

			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please type in an index number.");
			} finally {
				console.nextLine();
			}
		}

		return ret;
	}

	public static void printArrayList(ArrayList l) {
		for (int i = 0; i<l.size(); i++) {
			System.out.print(l.get(i)+ " ");
		}
	}
	


	}

