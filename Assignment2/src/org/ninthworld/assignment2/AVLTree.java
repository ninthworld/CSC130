package org.ninthworld.assignment2;

public class AVLTree {
	
	private AVLNode root;
	
	public AVLTree(){
	}
	
	public AVLNode getRoot(){
		return root;
	}
	
	public void setRoot(AVLNode node){
		root = node;
	}
	
	public boolean hasRoot(){
		return (root != null);
	}
	
	public void insert(int value){
		if(getRoot() == null){
			setRoot(new AVLNode(value));
		}else{
			getRoot().insert(value);
		}
	}
}
