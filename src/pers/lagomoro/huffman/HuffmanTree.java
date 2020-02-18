package pers.lagomoro.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

public class HuffmanTree<T> {

	protected HuffmanTreeNode<T> head = null;
	protected HuffmanTreeNode<T> root = null;
	protected int treeSize = 0;
	
	public HuffmanTree() {
		
	}
	
	public int size() { 
		return this.treeSize; 
	}

	public boolean isEmpty() { 
		return this.treeSize == 0; 
	}

	public T get(int index){
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;
		return point.element;
	}
	
	public int indexOf(T element) {
		HuffmanTreeNode<T> point = this.head;
		int index = 0;
		while (point != null && point.element != element) {
			point = point.next;
			index++;
		}
		return point == null ? -1 : index;
	}
	
	public T erase(int index) {
		HuffmanTreeNode<T> target;
		if (index == 0) {
			target = this.head;
			this.head = this.head.next;
		}
		else {
			HuffmanTreeNode<T> point = this.head;
			for (int i = 0; i < index - 1; i++)
				point = point.next;
			target = point.next;
			point.next = point.next.next;
		}
		this.treeSize--;
		return target.element;
	}
	
	public void insert(int index, T element, int weight, int leftNode, int rightNode) {
		if (index == 0)
			this.head = new HuffmanTreeNode<T>(element, weight, leftNode, rightNode, this.head);
		else {
			HuffmanTreeNode<T> point = this.head;
			for (int i = 0; i < index - 1; i++)
				point = point.next;
			point.next = new HuffmanTreeNode<T>(element, weight, leftNode, rightNode, point.next);
		}
		this.treeSize++;
	}
	
	public void insert(int index, T element, int weight) {
		this.insert(index, element, weight, -1, -1);
	}
	
	public void push(T element, int weight, int leftNode, int rightNode) {
		this.insert(this.treeSize, element, weight, leftNode, rightNode);
	}
	
	public void push(T element, int weight) {
		this.insert(this.treeSize, element, weight);
	}
	
	public void output(PrintStream out) {
		int i = 0;
		for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
			if(point.element != null)
				out.print(point.element.toString() + ',' + point.weight);
			else
				out.print("[0]," + this.weight(i));
			if (point.next != null)
				out.print(';');
			i++;
		}
		out.println();
	}
	
	// ================================================================================

	public int topCount() {
		int i = 0;
		for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
			if (point.onTop == true) {
				i++;
			}
		}
		return i;
	}
	
	public void makeTreeFromMap(HashMap<T, Integer> textMap) {
		for (T key : textMap.keySet()) {
			this.push(key, textMap.get(key));
		}
		if (this.treeSize == 0) {
			return;
		}
		if (this.treeSize == 1) {
			this.root = this.head;
			return;
		}
		while (this.topCount() >= 2) {
			int min1 = -1, min2 = -1;
			int index = 0;
			for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next, index++) {
				if (point.onTop == true) {
					if (min1 < 0) { min1 = index;continue; }
					if (min2 < 0) { min2 = index;continue; }
					if (this.weight(index) < this.weight(min1)) {
						min2 = min1;
						min1 = index;
					}else if (this.weight(index) < this.weight(min2)) {
						min2 = index;
					}
				}
			}

			HuffmanTreeNode<T> point = this.head;
			for (int i = 0; i < min1; i++)
				point = point.next;
			point.onTop = false;

			point = this.head;
			for (int i = 0; i < min2; i++)
				point = point.next;
			point.onTop = false;

			this.push(null, 0, min1, min2);
		}
		HuffmanTreeNode<T> point = this.head;
		while(point.next != null) {
			point = point.next;
		}
		this.root = point;
	}
	
	public void saveTreeToFile(String filename) {
		try {
			FileWriter fileWriter = new FileWriter(filename);
			int i = 0;
			for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
				if (point.weight > 0) {
					i++;
				}
			}
			fileWriter.write(i + "\n");
			for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
				if (point.weight > 0) {
					fileWriter.write(point.element.toString() + "\n");
				}
			}
			for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
				if (point.weight > 0) {
					fileWriter.write(point.weight + "\n");
				}
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int weight(int index) {
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;
		int i = point.weight;
		if (point.leftNode >= 0)
			i += this.weight(point.leftNode);
		if (point.rightNode >= 0)
			i += this.weight(point.rightNode);
		return i;
	}

	public int weight() {
		return this.weight(this.treeSize - 1);
	}

	public void inOrderOutput(PrintStream out){
		if (this.weight() > 0)
			this.inOrderOutput(out, this.treeSize - 1);
		out.println();
	}

	public void inOrderOutput(PrintStream out, int index) {
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if(point.element != null)
			out.print(point.element.toString() + ',' + point.weight + ';');
		if (point.leftNode >= 0)
			this.inOrderOutput(out, point.leftNode);
		if (point.rightNode >= 0)
			this.inOrderOutput(out, point.rightNode);
	}
	
	public void print(PrintStream out) {
		if (this.weight() > 0)
			this.print(out, this.treeSize - 1, "");
		out.println();
	}

	public void print(PrintStream out, int index, String str){
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0)
			this.print(out, point.leftNode, str + "   ");
		out.print(str + "¡¤");
		if (point.weight > 0)
			out.print('[' + point.element.toString() + ']');
		else
			out.print("< >");
		out.println();
		if (point.rightNode >= 0)
			this.print(out, point.rightNode, str + "   ");
	}
	
	public void printFile(String filename){
		try {
			FileWriter fileWriter = new FileWriter(filename);
			if (this.weight() > 0)
				this.printFile(fileWriter, this.treeSize - 1, "");
			fileWriter.write("\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printFile(FileWriter out, int index, String str) throws IOException{
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0)
			this.printFile(out, point.leftNode, str + "   ");
		out.write(str + "¡¤");
		if (point.weight > 0)
			out.write('[' + point.element.toString() + ']');
		else
			out.write("< >");
		out.write("\n");
		if (point.rightNode >= 0)
			this.printFile(out, point.rightNode, str + "   ");
	}
	
	public void getCodeMap(HashMap<T, String> codeMap) {
		if (this.weight() > 0)
			this.getCodeMap(codeMap, this.treeSize - 1, "");
	}

	public void getCodeMap(HashMap<T, String> codeMap, int index, String code) {
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0)
			this.getCodeMap(codeMap, point.leftNode, code + "0");
		if (point.element != null)
			codeMap.put(point.element, code);
		if (point.rightNode >= 0)
			this.getCodeMap(codeMap, point.rightNode, code + "1");
	}
	
	public void Decoding(String fromFile, String toFile) {
		String code = "";
		int input, temp;
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(fromFile));
			int buffer;
			while((buffer = fileInputStream.read()) != -1) {
				input = (byte)buffer + 128;
				temp = 128;
				for (int i = 0; i < 8; i++) {
					if (input >= temp) {
						input -= temp;
						code += "1";
					} else {
						code += "0";
					}
					temp = temp / 2;
				}
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String text = "";
		int target = this.treeSize - 1;
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < code.length(); i++) {
			point = this.head;
			for (int j = 0; j < target; j++)
				point = point.next;

			if (code.charAt(i) == '0') {
				if (point.leftNode >= 0) {
					target = point.leftNode;
				}
			} else {
				if (point.rightNode >= 0) {
					target = point.rightNode;
				}
			}

			point = this.head;
			for (int j = 0; j < target; j++)
				point = point.next;

			if (point.element != null) {
				target = this.treeSize - 1;
				text += point.element;
			}
		}

		try {
			FileWriter fileWriter = new FileWriter(toFile);
			fileWriter.write(text);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
