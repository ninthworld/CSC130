package org.ninthworld.binarytrees;

public class BSNode {
	
	private int value;
	private BSNode left, right;
	
	public BSNode(int value){
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public BSNode getLeft() {
		return left;
	}

	public void setLeft(BSNode left) {
		this.left = left;
	}

	public BSNode getRight() {
		return right;
	}

	public void setRight(BSNode right) {
		this.right = right;
	}
}
