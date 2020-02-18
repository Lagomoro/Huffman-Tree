package pers.lagomoro.huffman;

import java.awt.Color;

public class TreeSceneNode {

	public Character element = null;
	public int weight = 0;
	public double originx = 0;
	public double originy = 0;
	public double x = 0;
	public double y = 0;
	public Color origincolor = null;
	public Color color = null;
	public boolean hide = false;
	public int leftNode = -1;
	public int rightNode = -1;
	
	public TreeSceneNode(Character element, int weight, double x, double y, int leftNode, int rightNode) {
		this.element = element;
		this.weight = weight;
		this.originx = x;
		this.originy = y;
		this.x = x;
		this.y = y;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	
	public void resetColor() {
		this.color = this.origincolor;
	}
	
	public void resetPlace() {
		this.x = this.originx;
		this.y = this.originy;
	}

}
