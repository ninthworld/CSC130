package org.ninthworld.binarytrees;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JFrame;

import org.ninthworld.binarytrees.BSTest.BSTestArrayType;

public class Main {
	
	public Main(){
		
		displayGraph();
		
		/*
		AVLTree avlTree = new AVLTree();
		RBTree rbTree = new RBTree();
		for(int i=0; i<10; i++){
			avlTree.insert(i);
			rbTree.insert(i);
		}
		displayTree(avlTree);
		displayTree(rbTree);
		*/
		
		/*
		int[] incArray = BSTest.generateArray(BSTestArrayType.INCREASING, 1000);
		for(int i=0; i<4; i++){
			double avlMS = BSTest.insertTest(new AVLTree(), incArray);		
			System.out.printf("%5.1fms ", avlMS);	
		}
		*/
	}
	
	public void displayGraph(){
		int width = 640, height = 480;
		JFrame frame = new JFrame("Binary Search Tree");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setBackground(Color.decode("#3F3F49"));
		
		int maxValue = 1000000;
		int[] incArray = BSTest.generateArray(BSTestArrayType.INCREASING, maxValue);
		int[] decArray = BSTest.generateArray(BSTestArrayType.DECREASING, maxValue);
		int[] rndArray = BSTest.generateArray(BSTestArrayType.RANDOMIZED, maxValue);
		
		Rectangle rect = new Rectangle(10, 10, 80, 80);
		DisplayGraph graph = new DisplayGraph(rect, maxValue, 100);
		frame.add(graph);
		
		new Thread(new Runnable(){
			public void run(){
				int intervals = 10, trials = 1;
				for(int i=0; i<intervals; i++){
					for(int j=0; j<trials; j++){
						double x = i*(maxValue/intervals);
						double yInc = BSTest.insertTest(new AVLTree(), incArray, (int) x);
						double yDec = BSTest.insertTest(new AVLTree(), decArray, (int) x);
						double yRnd = BSTest.insertTest(new AVLTree(), rndArray, (int) x);
						
						graph.addData(new DisplayGraphData(x, yInc, Color.BLUE));
						graph.addData(new DisplayGraphData(x, yDec, Color.GREEN));
						graph.addData(new DisplayGraphData(x, yRnd, Color.RED));
						frame.repaint();
						
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
	}
	
	public void displayTree(BSTree tree){
		JFrame frame = new JFrame("Binary Search Tree");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setBackground(Color.decode("#3F3F49"));
		
		DisplayBSTree bsPanel = new DisplayBSTree(tree);
		frame.add(bsPanel);
		
		new Thread(new Runnable(){
			public void run(){
				frame.repaint();
			}
		}).start();
	}
	
	public static void main(String[] args){
		new Main();
	}
}
