package com.ryancho.matt3.AI;

import java.util.ArrayList;
import java.util.List;

// c stands input for child or children
// p stands for parent
// d stands for data
public class Node<T> {

	private T data = null;

	private List<Node<T>> children = new ArrayList<>();

	private Node<T> parent = null;

	

	// constructors
	public Node(T d) {
		this.data = d;
	}

	public Node(T d, Node<T> p) {
		this.data = d;
		this.parent = p;
	}

	public Node(T d, Node<T> p, Node<T> c) {
		this.data = d;
		this.parent = p;
		this.addChild(c);
	}
	
	public Node(T d, Node<T> p, List<Node<T>> c) {
		this.data = d;
		this.parent = p;
		this.addChildren(c);
	}

	// accessors
	public List<Node<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public Node<T> getParent() {
		return parent;
	}
	
	public Node<T> getRoot() {
		 if(parent == null){
		  return this;
		 }
		 return parent.getRoot();
		}

	public Node<T> getFinal(int i) {
		if (children == null) {
			return this;
		}
		return children.get(i).getFinal(i);
	}
	// mutators
	public Node<T> addChild(Node<T> c) {
		c.setParent(this);
		children.add(c);
		return c;
	}

	public void addChildren(List<Node<T>> c) {
		for (int i = 0; i < c.size(); i++) {
			c.get(i).setParent(this);
			this.children.add(c.get(i));
		}
	}
	
	public void addChildTo(Node<T> c, int x) {
		
	}

	public void setData(T d) {
		this.data = d;
	}

	public void setParent(Node<T> p) {
		this.parent = p;
	}
	
	public void flushChildren() {
		children.clear();
	}
	
} 
