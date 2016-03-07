package org.ninthworld.redone;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class DisplayAVLTree extends JPanel {
	
	private AVLTree tree;
	
	public DisplayAVLTree(AVLTree tree){
		this.tree = tree;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("Avant Garde", Font.PLAIN, 12));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(tree.root != null){
			drawNode(g2d, tree.root, this.getWidth()/2, 1, tree.root.height);
		}
	}
	
	public void drawNode(Graphics2D g, AVLNode node, int center, int level, int maxDepth){		
		int radius = Math.min(this.getWidth(), this.getHeight()) / 32;
		int levelMargin = this.getHeight()/(maxDepth+1);
		
		int x = center;
		int y = level*levelMargin;
		
		String strValue = Integer.toString(node.value);
		int strValueW = g.getFontMetrics().stringWidth(strValue);
		int strValueH = g.getFontMetrics().getHeight();
		
		String strHeight = Integer.toString(node.height);
		int strHeightW = g.getFontMetrics().stringWidth(strHeight);
		int strHeightH = g.getFontMetrics().getHeight();

		g.setColor(Color.WHITE);
		g.fillOval(x - radius, y - radius, radius*2, radius*2);
		
		g.setColor(Color.decode("#3F3F49"));
		g.drawString(strValue, x - strValueW/2, y + strValueH/4);
		
		g.setColor(Color.WHITE);
		g.drawString(strHeight, x - strHeightW/2, y - radius - strHeightH/4);
		
		if(node.left != null){
			int newLevel = level + 1;
			int newCenter = center - this.getWidth()/(int)Math.pow(2, newLevel);
			double angle = Math.PI/2 - Math.atan2(newLevel*levelMargin - y, newCenter - x);

			g.setColor(Color.WHITE);	
			g.drawLine(
					x + (int)(Math.sin(angle)*radius),
					y + (int)(Math.cos(angle)*radius),
					newCenter + (int)(Math.sin(angle - Math.PI)*radius),
					newLevel*levelMargin + (int)(Math.cos(angle - Math.PI)*radius)
			);
			
			drawNode(g, node.left, newCenter, newLevel, maxDepth);
		}
		
		if(node.right != null){
			int newLevel = level + 1;
			int newCenter = center + this.getWidth()/(int)Math.pow(2, newLevel);
			double angle = Math.PI/2 - Math.atan2(newLevel*levelMargin - y, newCenter - x);

			g.setColor(Color.WHITE);	
			g.drawLine(
					x + (int)(Math.sin(angle)*radius),
					y + (int)(Math.cos(angle)*radius),
					newCenter + (int)(Math.sin(angle - Math.PI)*radius),
					newLevel*levelMargin + (int)(Math.cos(angle - Math.PI)*radius)
			);
			
			drawNode(g, node.right, newCenter, newLevel, maxDepth);
		}
	}	
}
