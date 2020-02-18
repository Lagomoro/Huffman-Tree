package pers.lagomoro.huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static HuffmanTree<Character> tree;
	public static Scanner scanner;
	
	public static void main(String[] args) {
		
		tree = null;
		scanner = new Scanner(System.in);
		
		MainWindow window = new MainWindow() {
			private static final long serialVersionUID = 1L;
			@Override
			public void func_btn_1() {
				super.func_btn_1();
				this.updateHelp("���ڿ���̨�а�����ʾ�������ݡ�");
				tree = new HuffmanTree<Character>();
				makeTreeFromInput(tree);
				this.updateHelp("��ʼ���ɹ���");
			}
			@Override
			public void func_btn_2() {
				super.func_btn_2();
				File file = new File("./tobetrans.txt");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("δ�ҵ��ļ���./tobetrans.txt��");
				} else if(tree == null) {
					this.updateHelp("����������û�г�ʼ�����޷����롣");
				} else {
					Coding("./tobetrans.txt", "./codefile");
					this.updateHelp("����ɹ������浽��./codefile��");
				}
			}
			@Override
			public void func_btn_3() {
				super.func_btn_3();
				File file = new File("./codefile");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("δ�ҵ��ļ���./codefile��");
				} else if(tree == null) {
					this.updateHelp("����������û�г�ʼ�����޷����롣");
				} else {
					tree.Decoding("./codefile", "./textfile.txt");
					this.updateHelp("����ɹ������浽��./textfile.txt��");
				}
			}
			@Override
			public void func_btn_4() {
				super.func_btn_4();
				File file = new File("./codefile");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("δ�ҵ��ļ���./codefile��");
				} else if(tree == null) {
					this.updateHelp("����������û�г�ʼ�����޷���ӡ���롣");
				} else {
					Print("./codefile", "./codeprint.txt");
					this.updateHelp("�����Ѿ���ӡ������̨��ͬʱ�ɹ����浽��./codeprint.txt��");
				}
			}
			@Override
			public void func_btn_5() {
				super.func_btn_5();
				if(tree == null) {
					this.updateHelp("����������û�г�ʼ�����޷���ӡ��");
				}else {
					treePrinting(tree, "./treeprint.txt");
					this.updateHelp("������Ѿ���ӡ������̨��ͬʱ�ɹ����浽��./treeprint.txt��");
				}
			}
			@Override
			public void func_btn_save() {
				super.func_btn_save();
				if(tree == null) {
					this.updateHelp("����������û�г�ʼ�����޷����档");
				}else {
					tree.saveTreeToFile("./hfmtree");
					this.updateHelp("�ɹ����浽��./hfmtree��");
				}
			}
			@Override
			public void func_btn_load() {
				super.func_btn_load();
				File file = new File("./hfmtree");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("δ�ҵ��ļ���./hfmtree��");

				} else {
					tree = new HuffmanTree<Character>();
					loadTreeFromFile(tree, "./hfmtree");
					this.updateHelp("��./hfmtree�ж�ȡ���������ɹ���");
				}
			}
			@Override
			public void func_btn_6() {
				super.func_btn_6();
				this.updateHelp("����ִ�У���./tobetrans.txt���Զ������ַ�Ȩ�ء�");
				File file = new File("./tobetrans.txt");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("δ�ҵ��ļ���./tobetrans.txt��");
				} else {
					tree = new HuffmanTree<Character>();
					makeTreeFromFile(tree, "./tobetrans.txt");
					this.updateHelp("����ɣ���./tobetrans.txt���Զ������ַ�Ȩ�ء�");
				}
			}
			@Override
			public void func_btn_7() {
				super.func_btn_7();
			}
		};
		window.active();
		
		/*
		//initialization(tree);
		
	    HashMap<Character, String> codeMap = new HashMap<Character, String>();
	    tree.getCodeMap(codeMap);

	    for (Character key : codeMap.keySet()) {
	    	System.out.println(key + "," + codeMap.get(key));
		}
	    
	    tree.output(System.out);
	    System.out.println(tree.weight());
	    tree.inOrderOutput(System.out);

	    //tree.Coding("./tobetrans.txt", "./codefile.txt");
	    //tree.Decoding("./codefile.txt", "./textfile.txt");

	    treePrinting(tree);
	    */
	}

	public static void initialization(HuffmanTree<Character> tree) {
	    String confirm;
	    while (true) {
	    	System.out.println("�Ƿ��tobetrans.txt���Զ������ַ�Ȩ�أ�(y/n)");
	        confirm = scanner.nextLine();
	        if (confirm.equals("y")) {
	        	System.out.println("ѡ���tobetrans.txt���Զ������ַ�Ȩ��...");
	            makeTreeFromFile(tree, "./tobetrans.txt");
	            break;
	        } else if(confirm.equals("n")) {
	            while (true) {
	            	System.out.println("�Ƿ��hfmtree.txt���Զ���ȡ�洢�Ļ���������(y/n)");
	            	confirm = scanner.nextLine();
	    	        if (confirm.equals("y")) {
	                	System.out.println("ѡ���hfmtree.txt���Զ���ȡ�洢�Ļ�������...");
	                    loadTreeFromFile(tree, "./hfmtree.txt");
	                    break;
	                }
	                else if (confirm.equals("n")) {
	                	System.out.println("ѡ���ֶ������ַ�Ȩ��...");
	                	makeTreeFromInput(tree);
	                    break;
	                } else {
	                	System.out.println("��������(y/n)���Ƿ��hfmtree.txt���Զ���ȡ�洢�Ļ���������(y/n)");
	                    continue;
	                }
	            }
	            break;
	        } else {
	        	System.out.println("��������(y/n)���Ƿ��tobetrans.txt���Զ������ַ�Ȩ�أ�(y/n)");
	            continue;
	        }
	    }
	    tree.saveTreeToFile("./hfmtree.txt");
	}
	public static void treePrinting(HuffmanTree<Character> tree, String filename) {
	    tree.print(System.out);
	    tree.printFile(filename);
	}
	
	public static void makeTreeFromInput(HuffmanTree<Character> tree) {
		HashMap<Character, Integer> textMap = new HashMap<Character, Integer>();
		int n;
		System.out.println("�������ַ�����Сn��");
		n = scanner.nextInt();
		char[] c = new char[n];
		System.out.println("����������n���ַ���");
		for (int i = 0; i < n; i++)
			c[i] = scanner.next().charAt(0);
		System.out.println("����������n��Ȩֵ��");
		int[] v = new int[n];
		for (int i = 0; i < n; i++)
			v[i] = scanner.nextInt();
		for (int i = 0; i < n; i++)
			textMap.put(c[i], v[i]);
		tree.makeTreeFromMap(textMap);
		System.out.println("��ʼ���ɹ���");
	}
	
	public static void makeTreeFromFile(HuffmanTree<Character> tree, String filename) {
		HashMap<Character, Integer> textMap = new HashMap<Character, Integer>();
		try {
			File file = new File(filename);
			if (!file.exists() || file.isDirectory())
				return;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			int c;
			char key;
			while ((c = bufferedReader.read()) != -1) {
				key = (char)c;
				textMap.put(key, textMap.containsKey(key) ? textMap.get(key) + 1 : 1);
			}
			bufferedReader.close();
		} catch (IOException e) {
			return;
		}
		tree.makeTreeFromMap(textMap);
	}
	
	public static void loadTreeFromFile(HuffmanTree<Character> tree, String filename) {
		HashMap<Character, Integer> textMap = new HashMap<Character, Integer>();
		try {
			File file = new File(filename);
			if (!file.exists() || file.isDirectory())
				return;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			Scanner scan = new Scanner(bufferedReader);
			scan.useDelimiter("\n");
			int n;
			n = scan.nextInt();
			char[] c = new char[n];
			for (int i = 0; i < n; i++)
				c[i] = scan.next().charAt(0);

			int[] v = new int[n];
			for (int i = 0; i < n; i++)
				v[i] = scan.nextInt();
			for (int i = 0; i < n; i++)
				textMap.put(c[i], v[i]);
			scan.close();
			bufferedReader.close();
		} catch (IOException e) {
			return;
		}
		tree.makeTreeFromMap(textMap);
	}
	
	public static void Coding(String fromFile, String toFile) {
		HashMap<Character, String> codeMap = new HashMap<Character, String>();
	    tree.getCodeMap(codeMap);
	    String code = "";
		try {
			File file = new File(fromFile);
			if (!file.exists() || file.isDirectory())
				return;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			int c;
			char key;
			while ((c = bufferedReader.read()) != -1) {
				key = (char)c;
				code += codeMap.get(key);
			}
			bufferedReader.close();
		} catch (IOException e) {
			return;
		}
		int length = (int)Math.ceil(code.length() / 8.0);
		byte[] buffer = new byte[length];
		int temp;
		for (int i = 0; i < length; i++) {
			buffer[i] = 127;
			temp = 128;
			for (int j = i * 8; j < i * 8 + 8 && j < code.length(); j++) {
				if (code.charAt(j) == '0')
					buffer[i] -= temp;
				temp = temp / 2;
			}
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(toFile));
			fileOutputStream.write(buffer, 0, length);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Print(String fromFile, String toFile) {
		String code = "";
		int input, temp, count = 0;
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
					count ++;
					if(count % 50 == 0) {
						code += "\n";
					}else if(count % 5 == 0) {
						code += " ";
					}
				}
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.print(code);
		try {
			FileWriter fileWriter = new FileWriter(toFile);
			fileWriter.write(code);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
