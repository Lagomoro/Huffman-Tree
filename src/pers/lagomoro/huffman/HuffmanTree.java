package pers.lagomoro.huffman;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
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
		ArrayList<AnimateNode> animateList = new ArrayList<AnimateNode>();//*Animate
		AnimateNode animate;
		int animateIndex = 0;
		animate = new AnimateNode("info");
		animate.info = "动画初始化 ...";
		animateList.add(animate);//*Animate
		for (T key : textMap.keySet()) {
			this.push(key, textMap.get(key));
			animate = new AnimateNode("move");//*Animate
			animate.nodeID = animateIndex;
			animate.x = (double)animateIndex/textMap.size();
			animate.y = 1.2;
			animateList.add(animate);
			animateIndex ++;//*Animate
		}
		animate = new AnimateNode("sleep");//*Animate
		animate.sleepTime = 400;
		animateList.add(animate);//*Animate
		if (this.treeSize == 0) {
			return;
		}
		if (this.treeSize == 1) {
			this.root = this.head;
			animate = new AnimateNode("resetPlace");//*Animate
			animate.nodeID = 0;
			animateList.add(animate);//*Animate
			return;
		}
		while (this.topCount() >= 2) {
			int min1 = -1, min2 = -1;
			int index = 0;
			for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next, index++) {
				if (point.onTop == true) {
					animate = new AnimateNode("color");//*Animate
					animate.nodeID = index;
					animate.color = new Color(140, 200, 244);
					animate.info = "比对 " + index + " 号根节点 ...";
					animate.sleepTime = 30;
					animateList.add(animate);//*Animate
					if (min1 < 0) {
						animate = new AnimateNode("color");//*Animate
						animate.nodeID = index;
						animate.color = new Color(253, 111, 111);
						animate.info = "设置最小根节点为 " + index + " 号节点 ...";
						animate.sleepTime = 100;
						animateList.add(animate);//*Animate
						min1 = index;
						continue;
					}
					if (min2 < 0) {
						if(this.weight(index) < this.weight(min1)) {
							animate = new AnimateNode("color");//*Animate
							animate.nodeID = min1;
							animate.color = new Color(255, 156, 132);
							animateList.add(animate);
							animate = new AnimateNode("color");
							animate.nodeID = index;
							animate.color = new Color(253, 111, 111);
							animate.info = "设置最小根节点为 " + index + " 号节点，次小根节点为 " + min1 + " 号节点 ...";
							animate.sleepTime = 100;
							animateList.add(animate);//*Animate
							min2 = min1;
							min1 = index;
						} else {
							animate = new AnimateNode("color");//*Animate
							animate.nodeID = index;
							animate.color = new Color(255, 156, 132);
							animate.info = "设置次小根节点为 " + index + " 号节点 ...";
							animate.sleepTime = 100;
							animateList.add(animate);//*Animate
							min2 = index;
						}
						continue;
					}
					if (this.weight(index) < this.weight(min1)) {
						animate = new AnimateNode("color");//*Animate
						animate.nodeID = min2;
						animate.color = new Color(140, 200, 244);
						animateList.add(animate);
						animate = new AnimateNode("color");
						animate.nodeID = min1;
						animate.color = new Color(255, 156, 132);
						animateList.add(animate);
						animate = new AnimateNode("color");
						animate.nodeID = index;
						animate.color = new Color(253, 111, 111);
						animate.info = "设置最小根节点为 " + index + " 号节点，次小根节点为 " + min1 + " 号节点 ...";
						animate.sleepTime = 100;
						animateList.add(animate);//*Animate
						min2 = min1;
						min1 = index;
					}else if (this.weight(index) < this.weight(min2)) {
						animate = new AnimateNode("color");//*Animate
						animate.nodeID = min2;
						animate.color = new Color(140, 200, 244);
						animateList.add(animate);
						animate = new AnimateNode("color");
						animate.nodeID = index;
						animate.color = new Color(255, 156, 132);
						animate.info = "设置次小根节点为 " + index + " 号节点 ...";
						animate.sleepTime = 100;
						animateList.add(animate);//*Animate
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

			animate = new AnimateNode("sleep");//*Animate
			animate.sleepTime = 400;
			animateList.add(animate);
			animate = new AnimateNode("resetPlace");
			animate.nodeID = min1;
			animateList.add(animate);
			animate = new AnimateNode("resetPlace");
			animate.nodeID = min2;
			animateList.add(animate);
			animate = new AnimateNode("show");
			animate.nodeID = this.treeSize;
			animate.info = "合并根节点 " + min1 + " 号和 " + min2 + " 号，生成 " + this.treeSize + " 号节点 ...";
			animate.sleepTime = 600;
			animateList.add(animate);
			for (int i = 0; i < this.treeSize; i++) {
				animate = new AnimateNode("resetColor");
				animate.nodeID = i;
				animateList.add(animate);
			}//*Animate
			this.push(null, 0, min1, min2);
		}
		HuffmanTreeNode<T> point = this.head;
		while(point.next != null) {
			point = point.next;
		}
		this.root = point;
		
		for (int i = textMap.size();i < this.treeSize; i++) {//*Animate
			animate = new AnimateNode("hide");
			animate.nodeID = i;
			animateList.add(0, animate);
		}
		
		animate = new AnimateNode("info");//*Animate
		animate.info = "哈夫曼树生成动画完成。";
		animate.sleepTime = 5000;
		animateList.add(animate);
		Main.treeScene.setAnimate(animateList.toArray(new AnimateNode[1]), "哈夫曼树生成动画");//Animate
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
		out.print(str + "·");
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
		out.write(str + "·");
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
		ArrayList<AnimateNode> animateList = new ArrayList<AnimateNode>();//*Animate
		AnimateNode animate;
		animate = new AnimateNode("info");
		animate.info = "动画初始化 ...";
		animate.sleepTime = 400;
		animateList.add(animate);//*Animate
		
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
		animate = new AnimateNode("color");//*Animate
		animate.nodeID = target;
		animate.color = new Color(253, 111, 111);
		animate.info = "回到原点 ..." + this.getDecodeAnimateText(code, 0, text);
		animate.sleepTime = 200;
		animateList.add(animate);//*Animate
		for (int i = 0; i < code.length(); i++) {
			point = this.head;
			for (int j = 0; j < target; j++)
				point = point.next;

			if (code.charAt(i) == '0') {
				if (point.leftNode >= 0) {
					animate = new AnimateNode("resetColor");//*Animate
					animate.nodeID = target;
					animateList.add(animate);//*Animate
					target = point.leftNode;
					animate = new AnimateNode("color");//*Animate
					animate.nodeID = target;
					animate.color = new Color(253, 111, 111);
					animate.info = "输入0，左移 ..." + this.getDecodeAnimateText(code, i, text);
					animate.sleepTime = 100;
					animateList.add(animate);//*Animate
				}
			} else {
				if (point.rightNode >= 0) {
					animate = new AnimateNode("resetColor");//*Animate
					animate.nodeID = target;
					animateList.add(animate);//*Animate
					target = point.rightNode;
					animate = new AnimateNode("color");//*Animate
					animate.nodeID = target;
					animate.color = new Color(253, 111, 111);
					animate.info = "输入1，右移 ..." + this.getDecodeAnimateText(code, i, text);
					animate.sleepTime = 100;
					animateList.add(animate);//*Animate
				}
			}

			point = this.head;
			for (int j = 0; j < target; j++)
				point = point.next;

			if (point.element != null) {
				text += point.element;
				animate = new AnimateNode("info");//*Animate
				animate.info = "输出字符：" + point.element + " ..." + this.getDecodeAnimateText(code, i, text);
				animate.sleepTime = 400;
				animateList.add(animate);//*Animate
				
				animate = new AnimateNode("resetColor");//*Animate
				animate.nodeID = target;
				animateList.add(animate);//*Animate
				target = this.treeSize - 1;
				animate = new AnimateNode("color");//*Animate
				animate.nodeID = target;
				animate.color = new Color(253, 111, 111);
				animate.info = "回到原点 ..." + this.getDecodeAnimateText(code, i, text);
				animate.sleepTime = 200;
				animateList.add(animate);//*Animate
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
		
		animate = new AnimateNode("info");//*Animate
		animate.info = "解码动画完成。" + this.getDecodeAnimateText(code, code.length(), text);
		animate.sleepTime = 5000;
		animateList.add(animate);
		Main.treeScene.setAnimate(animateList.toArray(new AnimateNode[1]), "解码动画");//Animate
	}
	
	protected String getDecodeAnimateText(String code, int index, String text) {
		String temp = "\n当前编码序列：";
		if(index < code.length()) {
			temp += code.charAt(index) + " ";
			temp += code.substring(index + 1, Math.min(code.length(), index + 80));
		}
		if(code.length() >  index + 80)
			temp += " ...";
		temp += "\n当前字符序列：";
		if(text.length() > 80)
			temp += "... ";
		temp += text.substring(Math.max(text.length() - 80, 0), text.length());
		return temp;
	}
	// ================================================================================

	public void outputTreeScene(TreeScene scene) {
		TreeSceneNode[] nodes = new TreeSceneNode[this.treeSize];
		int i = 0;
		for (HuffmanTreeNode<T> point = this.head; point != null; point = point.next) {
			int weight = this.weight(i);
			double x = (double)this.getX(i) / (this.treeSize - 1);
			double y = (double)this.getY(i) / (this.treeHeight() - 1);
			nodes[i] = new TreeSceneNode((Character)point.element, weight, x, y, point.leftNode, point.rightNode);
			i++;
		}
		scene.setNodes(nodes);
	}
	
	public int getX(int target) {
		int[] x = {0};
		if (this.weight() > 0)
			getX(target, this.treeSize - 1, x);
		return x[0];
	}

	public boolean getX(int target, int index, int[] x){
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0) {
		    if(this.getX(target, point.leftNode, x)) {
		    	return true;
		    }
		}
		if(target == index)
			return true;
		x[0]++;
		if (point.rightNode >= 0) {
			if(this.getX(target, point.rightNode, x)) {
				return true;
			}
		}
		return false;
	}
	
	public int getY(int target) {
		int[] y = {0};
		if (this.weight() > 0)
			getY(target, this.treeSize - 1, y, 0);
		return y[0];
	}

	public boolean getY(int target, int index, int[] y, int deep){
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0) {
		    if(this.getY(target, point.leftNode, y, deep + 1)) {
		    	return true;
		    }
		}
		if(target == index) {
			y[0] = deep;
			return true;
		}
		if (point.rightNode >= 0) {
			if(this.getY(target, point.rightNode, y, deep + 1)) {
				return true;
			}
		}
		return false;
	}
	
	public int treeHeight() {
		int[] height = {0};
		if (this.weight() > 0)
			treeHeight(this.treeSize - 1, height, 1);
		return height[0];
	}

	public void treeHeight(int index, int[] height, int deep){
		HuffmanTreeNode<T> point = this.head;
		for (int i = 0; i < index; i++)
			point = point.next;

		if (point.leftNode >= 0)
		    this.treeHeight(point.leftNode, height, deep + 1);
		if(deep >= height[0])
			height[0] = deep;
		if (point.rightNode >= 0)
			this.treeHeight(point.rightNode, height, deep + 1);
	}
}
