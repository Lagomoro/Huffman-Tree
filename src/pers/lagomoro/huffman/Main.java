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
				this.updateHelp("请在控制台中按照提示输入内容。");
				tree = new HuffmanTree<Character>();
				makeTreeFromInput(tree);
				tree.outputTreeScene(treeScene);
				window.canvas.setScene(treeScene);
				this.updateHelp("初始化成功。");
			}
			@Override
			public void func_btn_2() {
				super.func_btn_2();
				File file = new File("./tobetrans.txt");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("未找到文件：./tobetrans.txt。");
				} else if(tree == null) {
					this.updateHelp("哈夫曼树还没有初始化，无法编码。");
				} else {
					Coding("./tobetrans.txt", "./codefile");
					this.updateHelp("编码成功，保存到：./codefile。");
				}
			}
			@Override
			public void func_btn_3() {
				super.func_btn_3();
				File file = new File("./codefile");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("未找到文件：./codefile。");
				} else if(tree == null) {
					this.updateHelp("哈夫曼树还没有初始化，无法解码。");
				} else {
					tree.Decoding("./codefile", "./textfile.txt");
					window.canvas.setScene(treeScene);
					this.updateHelp("解码成功，保存到：./textfile.txt。");
				}
			}
			@Override
			public void func_btn_4() {
				super.func_btn_4();
				File file = new File("./codefile");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("未找到文件：./codefile。");
				} else if(tree == null) {
					this.updateHelp("哈夫曼树还没有初始化，无法打印编码。");
				} else {
					Print("./codefile", "./codeprint.txt");
					this.updateHelp("编码已经打印到控制台，同时成功保存到：./codeprint.txt。");
				}
			}
			@Override
			public void func_btn_5() {
				super.func_btn_5();
				if(tree == null) {
					this.updateHelp("哈夫曼树还没有初始化，无法打印。");
				}else {
					treePrinting(tree, "./treeprint.txt");
					window.canvas.setScene(treeScene);
					this.updateHelp("凹入表已经打印到控制台，同时成功保存到：./treeprint.txt。");
				}
			}
			@Override
			public void func_btn_save() {
				super.func_btn_save();
				if(tree == null) {
					this.updateHelp("哈夫曼树还没有初始化，无法保存。");
				}else {
					tree.saveTreeToFile("./hfmtree");
					this.updateHelp("成功保存到：./hfmtree。");
				}
			}
			@Override
			public void func_btn_load() {
				super.func_btn_load();
				File file = new File("./hfmtree");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("未找到文件：./hfmtree。");

				} else {
					tree = new HuffmanTree<Character>();
					loadTreeFromFile(tree, "./hfmtree");
					tree.outputTreeScene(treeScene);
					window.canvas.setScene(treeScene);
					this.updateHelp("从./hfmtree中读取哈夫曼树成功。");
				}
			}
			@Override
			public void func_btn_6() {
				super.func_btn_6();
				this.updateHelp("正在执行：从./tobetrans.txt中自动分析字符权重。");
				File file = new File("./tobetrans.txt");
				if (!file.exists() || file.isDirectory()) {
					this.updateHelp("未找到文件：./tobetrans.txt。");
				} else {
					tree = new HuffmanTree<Character>();
					makeTreeFromFile(tree, "./tobetrans.txt");
					tree.outputTreeScene(treeScene);
					window.canvas.setScene(treeScene);
					this.updateHelp("已完成：从./tobetrans.txt中自动分析字符权重。");
				}
			}
			@Override
			public void func_btn_play() {
				super.func_btn_play();
				if(treeScene.animate == null) {
					this.updateHelp("没有存在的动画序列，无法播放。");
				} else if(window.canvas.isBusy) {
					this.updateHelp("动画正在播放中，请勿重复点击。");
				} else {
					this.updateHelp("开始播放动画。");
					treeScene.startAnimate(window.canvas);
				}
			}
			@Override
			public void func_btn_pause() {
				super.func_btn_pause();
				if(treeScene.animate == null) {
					this.updateHelp("没有存在的动画序列，无法暂停。");
				} else if(!window.canvas.isBusy) {
					this.updateHelp("没有正在播放的动画序列，无需暂停。");
				}else {
					if (treeScene.animatePause == false) {
						treeScene.animatePause = true;
						this.updateHelp("动画暂停。");
					} else if(treeScene.animatePause == true) {
						treeScene.animatePause = false;
						this.updateHelp("动画恢复。");
					} 
				}
			}
			@Override
			public void func_btn_stop() {
				super.func_btn_stop();
				if(!window.canvas.isBusy) {
					this.updateHelp("没有正在播放的动画序列，无需停止。");
				} else {
					treeScene.animateStop = true;
					this.updateHelp("动画停止。");
				} 
			}
			@Override
			public void func_btn_prev() {
				super.func_btn_prev();
				if(!window.canvas.isBusy) {
					this.updateHelp("没有正在播放的动画序列，请先播放。");
				} else {
					if(treeScene.setLastCheckpoint(window.canvas)) {
						this.updateHelp("成功设置上一步。");
					}else {
						this.updateHelp("不存在上一个检查点。");
					}
				} 
			}
			@Override
			public void func_btn_next() {
				super.func_btn_next();
				if(!window.canvas.isBusy) {
					this.updateHelp("没有正在播放的动画序列，请先播放。");
				} else {
					if(treeScene.setNextCheckpoint(window.canvas)) {
						this.updateHelp("成功设置下一步。");
					}else {
						this.updateHelp("不存在下一个检查点。");
					}
				} 
			}
			@Override
			public void func_btn_slow() {
				super.func_btn_slow();
				if(treeScene.animateSpeed > 0.125) {
					treeScene.animateSpeed /= 2;
				}
				this.updateHelp("成功修改动画播放速度：" + treeScene.animateSpeed);
			}
			@Override
			public void func_btn_fast() {
				super.func_btn_fast();
				if(treeScene.animateSpeed < 16) {
					treeScene.animateSpeed *= 2;
				}
				this.updateHelp("成功修改动画播放速度：" + treeScene.animateSpeed);
			}
		};
		window.active();
		
		window.canvas.setScene(new Scene() {
			@Override
			public void paint(Graphics2D graphics, int width, int height) {
				super.paint(graphics, width, height);
				graphics.setColor(Color.BLACK);
				this.setFontSize(graphics, 24);
				graphics.drawString("欢迎使用 数据结构课程设计演示系统！", 300, 310);
			}
		});
	}

	public static void initialization(HuffmanTree<Character> tree) {
	    String confirm;
	    while (true) {
	    	System.out.println("是否从tobetrans.txt中自动分析字符权重？(y/n)");
	        confirm = scanner.nextLine();
	        if (confirm.equals("y")) {
	        	System.out.println("选择从tobetrans.txt中自动分析字符权重...");
	            makeTreeFromFile(tree, "./tobetrans.txt");
	            break;
	        } else if(confirm.equals("n")) {
	            while (true) {
	            	System.out.println("是否从hfmtree.txt中自动读取存储的霍夫曼树？(y/n)");
	            	confirm = scanner.nextLine();
	    	        if (confirm.equals("y")) {
	                	System.out.println("选择从hfmtree.txt中自动读取存储的霍夫曼树...");
	                    loadTreeFromFile(tree, "./hfmtree.txt");
	                    break;
	                }
	                else if (confirm.equals("n")) {
	                	System.out.println("选择手动输入字符权重...");
	                	makeTreeFromInput(tree);
	                    break;
	                } else {
	                	System.out.println("请您输入(y/n)，是否从hfmtree.txt中自动读取存储的霍夫曼树？(y/n)");
	                    continue;
	                }
	            }
	            break;
	        } else {
	        	System.out.println("请您输入(y/n)，是否从tobetrans.txt中自动分析字符权重？(y/n)");
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
		System.out.println("请输入字符集大小n：");
		n = scanner.nextInt();
		char[] c = new char[n];
		System.out.println("请依次输入n个字符：");
		for (int i = 0; i < n; i++)
			c[i] = scanner.next().charAt(0);
		System.out.println("请依次输入n个权值：");
		int[] v = new int[n];
		for (int i = 0; i < n; i++)
			v[i] = scanner.nextInt();
		for (int i = 0; i < n; i++)
			textMap.put(c[i], v[i]);
		tree.makeTreeFromMap(textMap);
		System.out.println("初始化成功。");
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
		
		String codeMapStr = "编码表：\n\n";
		for(Character key : codeMap.keySet())
			codeMapStr += key + " - " + codeMap.get(key) + "\n";
		codeMapStr += "\n编码后的byte数组：\n\n";
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
						graphics.drawString("...受显示区域限制，完整code请查看./codefile", x, y);
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
						graphics.drawString("...受显示区域限制，完整code请查看./codeprint.txt", x, y);
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
