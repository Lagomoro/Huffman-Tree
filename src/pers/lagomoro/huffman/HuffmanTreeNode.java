package pers.lagomoro.huffman;

public class HuffmanTreeNode<T> {

	public T element = null;
	public int weight = 0;
	public int leftNode = -1;
	public int rightNode = -1;
	public boolean onTop = true;
	public HuffmanTreeNode<T> next;

	HuffmanTreeNode(T element, int weight) {
	    this.element = element;
	    this.weight = weight;
	}

	HuffmanTreeNode(T element, int weight, HuffmanTreeNode<T> next) {
	    this.element = element;
	    this.weight = weight;
	    this.next = next;
	}

	HuffmanTreeNode(T element, int weight, int leftNode, int rightNode) {
	    this.element = element;
	    this.weight = weight;
	    this.leftNode = leftNode;
	    this.rightNode = rightNode;
	}

	HuffmanTreeNode(T element, int weight, int leftNode, int rightNode, HuffmanTreeNode<T> next) {
	    this.element = element;
	    this.weight = weight;
	    this.leftNode = leftNode;
	    this.rightNode = rightNode;
	    this.next = next;
	}

}
