package org.ninthworld.binarytrees;

import org.ninthworld.binarytrees.RBNode.RBColor;

public class RBTree extends BSTree {
	
	private RBNode root;
	
	public RBTree() {
		super();
		this.root = null;
	}

	public RBNode getRoot() {
		return root;
	}
	
	public void insert(int value){
		root = RBTree.insert(value, root);
		root.setColor(RBColor.BLACK);
	}
	
	public void remove(int value){
		root = RBTree.remove(value, root);
		root.setColor(RBColor.BLACK);
	}
	
	public RBNode find(int value){
		return RBTree.find(value, root);
	}
	
	public RBNode findMin(){
		return RBTree.findMin(root);
	}
	
	public RBNode findMax(){
		return RBTree.findMax(root);
	}
	
	private static RBNode insert(int value, RBNode node){
		if(node == null){
			node = new RBNode(value);
		}else{
			if(value == node.getValue()){
				return node;
			}else if(value > node.getValue()){
				node.setRight( insert(value, node.getRight()) );
			}else{
				node.setLeft( insert(value, node.getLeft()) );
			}
		}
		
		if(color(node.getLeft()) == RBColor.RED && color(node.getRight()) == RBColor.RED){
			RBColor ll = color(node.getLeft().getLeft());
			RBColor lr = color(node.getLeft().getRight());
			RBColor rr = color(node.getRight().getRight());
			RBColor rl = color(node.getRight().getLeft());
			
			if(ll == RBColor.RED || lr == RBColor.RED || rr == RBColor.RED || rl == RBColor.RED){
				node.getLeft().setColor(RBColor.BLACK);
				node.getRight().setColor(RBColor.BLACK);
				node.setColor(RBColor.RED);
			}
		}else if(color(node.getLeft()) == RBColor.RED){
			if(color(node.getLeft().getLeft()) == RBColor.RED){
				node = leftRotate(node);
			}else if(color(node.getLeft().getRight()) == RBColor.RED){
				node.setLeft( rightRotate(node.getLeft()) );
				node = leftRotate(node);				
			}
		}else if(color(node.getRight()) == RBColor.RED){
			if(color(node.getRight().getRight()) == RBColor.RED){
				node = rightRotate(node);				
			}else if(color(node.getRight().getLeft()) == RBColor.RED){	
				node.setRight( leftRotate(node.getRight()) );
				node = rightRotate(node);			
			}
		}
		
		return node;
	}
	
	private static RBNode remove(int value, RBNode node){
		if(node == null){
			return node;
		}else{
			if(value == node.getValue()){
				if(node.getLeft() == null && node.getRight() == null){
					return null;
				}else if(node.getLeft() == null){
					node.getRight().setColor(RBColor.BLACK);
					return node.getRight();
				}else if(node.getRight() == null){
					node.getLeft().setColor(RBColor.BLACK);
					return node.getLeft();
				}else{
					RBNode min = findMin(node.getRight());
					node.setValue(min.getValue());
					node.setRight( remove(min.getValue(), node.getRight()) );
				}
			}else if(value > node.getValue()){
				node.setRight( remove(value, node.getRight()) );
			}else{
				node.setLeft( remove(value, node.getLeft()) );
			}
		}
		
		if(color(node.getLeft()) == RBColor.RED && color(node.getRight()) == RBColor.RED){
			RBColor ll = color(node.getLeft().getLeft());
			RBColor lr = color(node.getLeft().getRight());
			RBColor rr = color(node.getRight().getRight());
			RBColor rl = color(node.getRight().getLeft());
			
			if(ll == RBColor.RED || lr == RBColor.RED || rr == RBColor.RED || rl == RBColor.RED){
				node.getLeft().setColor(RBColor.BLACK);
				node.getRight().setColor(RBColor.BLACK);
				node.setColor(RBColor.RED);
			}
		}else if(color(node.getLeft()) == RBColor.RED){
			if(color(node.getLeft().getLeft()) == RBColor.RED){
				node = leftRotate(node);
			}else if(color(node.getLeft().getRight()) == RBColor.RED){
				node.setLeft( rightRotate(node.getLeft()) );
				node = leftRotate(node);				
			}
		}else if(color(node.getRight()) == RBColor.RED){
			if(color(node.getRight().getRight()) == RBColor.RED){
				node = rightRotate(node);				
			}else if(color(node.getRight().getLeft()) == RBColor.RED){	
				node.setRight( leftRotate(node.getRight()) );
				node = rightRotate(node);			
			}
		}
		
		return node;
	}
	
	private static RBNode leftRotate(RBNode node){
		RBNode a = node;
		RBNode b = a.getLeft();
		RBNode bR = b.getRight();
		
		a.setLeft( bR );
		b.setRight( a );
		
		a.setColor(RBColor.RED);
		b.setColor(RBColor.BLACK);
		
		return b;
	}
	
	private static RBNode rightRotate(RBNode node){
		RBNode a = node;
		RBNode b = a.getRight();
		RBNode bL = b.getLeft();
		
		a.setRight( bL );
		b.setLeft( a );

		a.setColor(RBColor.RED);
		b.setColor(RBColor.BLACK);
		
		return b;
	}
	
	private static RBColor color(RBNode node){
		if(node == null){
			return RBColor.BLACK;
		}else{
			return node.getColor();
		}
	}
	
	private static RBNode find(int value, RBNode node){
		if(node == null){
			return node;
		}else{
			if(value == node.getValue()){
				return node;
			}else if(value > node.getValue()){
				return find(value, node.getRight());
			}else{
				return find(value, node.getLeft());
			}
		}
	}
	
	private static RBNode findMin(RBNode node){
		if(node == null){
			return node;
		}else{
			if(node.getLeft() == null){
				return node;
			}else{
				return findMin(node.getLeft());
			}
		}
	}
	
	private static RBNode findMax(RBNode node){
		if(node == null){
			return node;
		}else{
			if(node.getRight() == null){
				return node;
			}else{
				return findMax(node.getRight());
			}
		}
	}
}