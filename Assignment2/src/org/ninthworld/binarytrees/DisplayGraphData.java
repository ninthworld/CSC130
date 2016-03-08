package org.ninthworld.binarytrees;

import java.awt.Color;

public class DisplayGraphData {

	private double x, y;
	private Color color;
	
	public DisplayGraphData(double x, double y) {
		this(x, y, Color.BLACK);
	}
	
	public DisplayGraphData(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
