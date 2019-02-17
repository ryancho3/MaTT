/*MaTT: Mancala Tree Traversal Engine v3.1
 * By Ryan Cho
*/

package com.ryancho.matt3.AI;

import java.awt.List;
import java.util.ArrayList;

import com.ryancho.matt3.Game.*;


public class MaTT3_1 {
	private Board b;
	private Node<Board> root;
	private ArrayList<Integer> evalList;
	private int playerAlternation = 0;
	private int recurseLayer = 2;
	private int layersOfLookahead;

	// initialization
	public MaTT3_1(Board b, int x) {
		this.b = b;
		evalList = new ArrayList<Integer>();
		for (int i = 0; i<6; i++) {
			evalList.add(0);
		}

		layersOfLookahead = x;
	}

	// tree functionality
	public void clearList() {
		for (int i = 0; i<6; i++) {
			evalList.set(i,0);
		}
	}
	
	
	public Board cloneBoard(Board b) {
		Board tempBoard = new Board();
		Dish[] tempDishes = new Dish[12];
		Dish[] getDishes = b.getDishes();
		for (int i = 0; i < b.getDishes().length; i++) {
			tempDishes[i] = new Dish(i);
			tempDishes[i].setStones(getDishes[i].getStones());
		}
		tempBoard.setDishes(tempDishes);
		tempBoard.setTurn(b.getTurn());
		tempBoard.setScore(0, b.getScore0());
		tempBoard.setScore(1, b.getScore1());
		return tempBoard;
	}

	public Node<Board> getRoot() {
		return root;

	}

	public void buildTree(Board b) {
		playerAlternation = 0;
		root = new Node<>(b);
		recurseLayer = layersOfLookahead;
		recurseBuildTree(root, layersOfLookahead);
		
	}

	public void recurseBuildTree(Node<Board> parent, int layer) {
		recurseLayer = layer;
		int mod = 0;
		int player = determinePlayer(layer);

		if (layer > 0) {
			for (int i = 0; i < 6; i++) {

				if (player == 0) {
					parent.addChild(new Node<>(cloneBoard(parent.getData())));
					parent.getChildren().get(i).getData().pick(i);
					//System.out.println("layer= " + layer);
					//System.out.println("child= " + i);
					if (layer > 1) {
						recurseBuildTree(parent.getChildren().get(i), (layer - 1));
					}

				} else if (player == 1) {
					mod = i + 6;
					parent.addChild(new Node<>(cloneBoard(parent.getData())));
					parent.getChildren().get(i).getData().pick(mod);
					//System.out.println("layer= " + layer);
					//System.out.println("child= " + mod);
					if (layer > 1) {
						recurseBuildTree(parent.getChildren().get(mod), (layer - 1));
					}

				}

			}

		}
	}

	public void printTree(Node<Board> node, String appender) {
		
		System.out.println(appender + evaluateBoard(node.getData(), determinePlayer(recurseLayer)));
		node.getChildren().forEach(each -> printTree(each, appender + appender));
	}

	public int determinePlayer(int i) {
		return i % 2;
		
	}

	// Logic

	public int evaluateBoard(Board input, int player) {
		int eval = 0;

		if (input.deltaScore() != 0) {
			eval += (input.deltaScore() * 100);
		}
		if (player == 1) {
			return (-eval);
		}

		for (int i = 0; i < 6; i++) {
			if (input.getDishes()[i].getStones() != 0) {
				eval += 25;
			}
		}
		for (int i = 6; i < 12; i++) {
			if (input.getDishes()[i].getStones() == 0) {
				eval += 50;
			}
		}

		return eval;
	}
	
	
	public int getDepth(Node<Board> n, int i) {
		int depth = i;
		if (n.getChildren().isEmpty()) {
			return depth;
		} else {
			return -(getDepth(n.getChildren().get(0), depth - 1));
		}
	}
	
	public ArrayList<Node<Board>>[] depthList() {
		Node<Board> refrenceNode = root;
		ArrayList<Node<Board>>[] ret = null;
		for (int i = 0; i<getDepth(root,0); i++) {
			ret[i] = (ArrayList<Node<Board>>) refrenceNode.getChildren();
		}
		
		return ret;
		
	}

	public void traverseTree(Node<Board> root) {
		for (int i = 0; i < 6; i++) {
			recurseTree(root.getChildren().get(i), getDepth(root, 0));
		}
	}

	public void recurseTree(Node<Board> node, int layer) {
		int netGain = 0;
		netGain = evalList.get(layer) + evaluateBoard(node.getData(), alternate());
		
		if (node.getChildren().isEmpty()) {
			return;
		}

		for (int i = 0; i < 6; i++) {
			for (int y = 0; y<6; y++) {
				recurseTree(node.getChildren().get(i), y);
			}
			
		}
		
	}
	
	public int maximize(Node<Board> parent) {
		if (parent.getChildren().isEmpty()) {
			return (Integer) null;
		}
		ArrayList<Node<Board>> children = (ArrayList) parent.getChildren();
		int maxIndex = 0;
		int maxValue = Integer.MIN_VALUE;
		int evalScore;
				
		for (int i = 0; i<children.size(); i++) {
			evalScore = evaluateBoard(children.get(i).getData(), 0);
			if (evalScore > maxValue) {
				maxValue = evalScore;
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public int minimize(Node<Board> parent) {
		if (parent.getChildren().isEmpty()) {
			return (Integer) null;
		}
		
		ArrayList<Node<Board>> children = (ArrayList) parent.getChildren();
		int minIndex = 0;
		int minValue = Integer.MAX_VALUE;
		int evalScore;
				
		for (int i = 0; i<children.size(); i++) {
			evalScore = evaluateBoard(children.get(i).getData(), 1);
			if (evalScore < minValue) {
				minValue = evalScore;
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public int decision() {
		miniMax(root, getDepth(root, 0));
	}
	
	
	public ArrayList<Node<Board>>[] miniMax(Node<Board> n, int depth) {
		int player = determinePlayer(depth);
		ArrayList<Node<Board>>[] ret;
		if (player == 0) {
			maximize(n);
		}
		
		
		if (depth > 1) {
		for (int i = 0; i<n.getChildren().size(); i++) {
			miniMax(n.getChildren().get(i), depth-1);
		}
		}
	}
	
	
	
	
	
	
	
	public int decide(Board b) {
		clearList();
		playerAlternation = 0;
		traverseTree(root);
		int high = Integer.MIN_VALUE;
		int ret = 0;
		printTree(root,"   ");
		for (int i = 0; i<evalList.size(); i++) {
			if (evalList.get(i) > high && b.getDishes()[i].getStones() != 0) {
				high = evalList.get(i);
				ret = i;
			}
		}
		printArrayList(evalList);
		System.out.println("Choice: " + ret);
		return ret;
	}
	public int alternate() {
		if (playerAlternation == 0) {
			playerAlternation = 1;
		}
		if (playerAlternation == 1) {
			playerAlternation = 0;
		}
		return playerAlternation;
	}
	
	public static void printArrayList(ArrayList l) {
		for (int i = 0; i<l.size(); i++) {
			System.out.print(l.get(i)+ " ");
		}
	}
}


