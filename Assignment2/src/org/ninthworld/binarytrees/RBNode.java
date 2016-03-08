package org.ninthworld.binarytrees;

public class RBNode extends BSNode {
	enum RBColor { RED, BLACK };
	
	private int value;
	private RBColor color;
	private RBNode left, right;
	
	public RBNode(int value) {
		super(value);
		this.value = value;
		this.color = RBColor.RED;
		this.left = null;
		this.right = null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public RBColor getColor() {
		return color;
	}

	public void setColor(RBColor color) {
		this.color = color;
	}

	public RBNode getLeft() {
		return left;
	}

	public void setLeft(RBNode left) {
		this.left = left;
	}

	public RBNode getRight() {
		return right;
	}

	public void setRight(RBNode right) {
		this.right = right;
	}

}