package pers.lagomoro.huffman;

import java.awt.Color;
import java.awt.Graphics2D;
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

	public static MainWindow window;
	public static HuffmanTree<Character> tree;
	public static Scanner scanner;
	public static TreeScene treeScene;
	
	public static void main(String[] args) {
		
		tree = null;
		scanner = new Scanner(System.in);
		
		treeScene = new TreeScene();
		
		window = new MainWindow() {
			private static final long serialVersionUID = 1L;
			@Override
			public void func_btn_1() {
				super.func_btn_1();
				this.updateHelp("���ڿ���̨�а�����ʾ�������ݡ�");
				tree = new HuffmanTree<Character>();
				makeTreeFromInput(tree);
				tree.outputTreeScene(treeScene);
				window.canvas.setScene(treeScene);
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
					window.canvas.setScene(treeScene);
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
					window.canvas.setScene(treeScene);
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
					tree.outputTreeScene(treeScene);
					window.canvas.setScene(treeScene);
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
					tree.outputTreeScene(treeScene);
					window.canvas.setScene(treeScene);
					this.updateHelp("����ɣ���./tobetrans.txt���Զ������ַ�Ȩ�ء�");
				}
			}
			@Override
			public void func_btn_play() {
				super.func_btn_play();
				if(treeScene.animate == null) {
					this.updateHelp("û�д��ڵĶ������У��޷����š�");
				} else if(window.canvas.isBusy) {
					this.updateHelp("�������ڲ����У������ظ������");
				} else {
					this.updateHelp("��ʼ���Ŷ�����");
					treeScene.startAnimate(window.canvas);
				}
			}
			@Override
			public void func_btn_pause() {
				super.func_btn_pause();
				if(treeScene.animate == null) {
					this.updateHelp("û�д��ڵĶ������У��޷���ͣ��");
				} else if(!window.canvas.isBusy) {
					this.updateHelp("û�����ڲ��ŵĶ������У�������ͣ��");
				}else {
					if (treeScene.animatePause == false) {
						treeScene.animatePause = true;
						this.updateHelp("������ͣ��");
					} else if(treeScene.animatePause == true) {
						treeScene.animatePause = false;
						this.updateHelp("�����ָ���");
					} 
				}
			}
			@Override
			public void func_btn_stop() {
				super.func_btn_stop();
				if(!window.canvas.isBusy) {
					this.updateHelp("û�����ڲ��ŵĶ������У�����ֹͣ��");
				} else {
					treeScene.animateStop = true;
					this.updateHelp("����ֹͣ��");
				} 
			}
			@Override
			public void func_btn_prev() {
				super.func_btn_prev();
				if(!window.canvas.isBusy) {
					this.updateHelp("û�����ڲ��ŵĶ������У����Ȳ��š�");
				} else {
					if(treeScene.setLastCheckpoint(window.canvas)) {
						this.updateHelp("�ɹ�������һ����");
					}else {
						this.updateHelp("��������һ�����㡣");
					}
				} 
			}
			@Override
			public void func_btn_next() {
				super.func_btn_next();
				if(!window.canvas.isBusy) {
					this.updateHelp("û�����ڲ��ŵĶ������У����Ȳ��š�");
				} else {
					if(treeScene.setNextCheckpoint(window.canvas)) {
						this.updateHelp("�ɹ�������һ����");
					}else {
						this.updateHelp("��������һ�����㡣");
					}
				} 
			}
			@Override
			public void func_btn_slow() {
				super.func_btn_slow();
				if(treeScene.animateSpeed > 0.125) {
					treeScene.animateSpeed /= 2;
				}
				this.updateHelp("�ɹ��޸Ķ��������ٶȣ�" + treeScene.animateSpeed);
			}
			@Override
			public void func_btn_fast() {
				super.func_btn_fast();
				if(treeScene.animateSpeed < 16) {
					treeScene.animateSpeed *= 2;
				}
				this.updateHelp("�ɹ��޸Ķ��������ٶȣ�" + treeScene.animateSpeed);
			}
		};
		window.active();
		
		window.canvas.setScene(new Scene() {
			@Override
			public void paint(Graphics2D graphics, int width, int height) {
				super.paint(graphics, width, height);
				graphics.setColor(Color.BLACK);
				this.setFontSize(graphics, 24);
				graphics.drawString("��ӭʹ�� ���ݽṹ�γ������ʾϵͳ��", 300, 310);
			}
		});
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
		
		String codeMapStr = "�����\n\n";
		for(Character key : codeMap.keySet())
			codeMapStr += key + " - " + codeMap.get(key) + "\n";
		codeMapStr += "\n������byte���飺\n\n";
		for(int i = 0;i < buffer.length; i++) {
			codeMapStr += buffer[i] + " ";
			if(i % 15 == 14)
				codeMapStr += "\n";
		}
		System.out.print(codeMapStr);
		final String codeMapString = codeMapStr;
		window.canvas.setScene(new Scene() {
			@Override
			public void paint(Graphics2D graphics, int width, int height) {
				super.paint(graphics, width, height);
				graphics.setColor(Color.BLACK);
				String[] codes = codeMapString.split("\n");
				int x = 90, y = 50;
				for(int i = 0; i < codes.length; i++) {
					if(i == 77 && codes.length >= 77) {
						graphics.drawString("...����ʾ�������ƣ�����code��鿴./codefile", x, y);
						break;
					}
					graphics.drawString(codes[i], x, y);
					y += 15;
					if(i == 38) {
						x = 540;
						y = 50;
					}
				}
			}
		});
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
		final String codec = code;
		window.canvas.setScene(new Scene() {
			@Override
			public void paint(Graphics2D graphics, int width, int height) {
				super.paint(graphics, width, height);
				graphics.setColor(Color.BLACK);
				String[] codes = codec.split("\n");
				int x = 90, y = 50;
				for(int i = 0; i < codes.length; i++) {
					if(i == 77 && codes.length >= 77) {
						graphics.drawString("...����ʾ�������ƣ�����code��鿴./codeprint.txt", x, y);
						break;
					}
					graphics.drawString(codes[i], x, y);
					y += 15;
					if(i == 38) {
						x = 540;
						y = 50;
					}
				}
			}
		});
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
