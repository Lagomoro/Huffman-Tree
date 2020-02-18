package pers.lagomoro.huffman;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JTextField help;
	JLabel treeNode, treeWeight;
	CanvasPanel canvas;

	public MainWindow(){
		this.initialize();
		this.create();
	}
	
	protected void initialize() {
		this.setTitle("数据结构课程设计演示程序 - 201800301015 王永睿");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1024, 624);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
	}
	
	protected void create() {
		this.createMenu();
		this.createHelp();
		this.createCanvas();
	}
	
	protected void createMenu() {
		int x = 20, y = 20, width = 220, height = 25, padding = 10;
		
		JLabel label_1 = new JLabel("基本功能：");
		label_1.setBounds(x, y, width, height);
		this.add(label_1);
		
		y += height + padding;
		JButton btn_1 = new JButton("初始化（Initialization）");
		btn_1.setFocusPainted(false);
		btn_1.setBounds(x, y, width, height);
		btn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_1();
			}
		});
		this.add(btn_1);
		
		y += height + padding;
		JButton btn_2 = new JButton("编码（Coding）");
		btn_2.setFocusPainted(false);
		btn_2.setBounds(x, y, width, height);
		btn_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_2();
			}
		});
		this.add(btn_2);
		
		y += height + padding;
		JButton btn_3= new JButton("解码（Decoding）");
		btn_3.setFocusPainted(false);
		btn_3.setBounds(x, y, width, height);
		btn_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_3();
			}
		});
		this.add(btn_3);
		
		y += height + padding;
		JButton btn_4= new JButton("打印代码文件（Print）");
		btn_4.setFocusPainted(false);
		btn_4.setBounds(x, y, width, height);
		btn_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_4();
			}
		});
		this.add(btn_4);
		
		y += height + padding;
		JButton btn_5= new JButton("打印哈夫曼树（Tree printing）");
		btn_5.setFocusPainted(false);
		btn_5.setBounds(x, y, width, height);
		btn_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_5();
			}
		});
		this.add(btn_5);
		
		y += height + padding;
		JLabel label_2 = new JLabel("保存与读取：");
		label_2.setBounds(x, y, width, height);
		this.add(label_2);
		
		y += height + padding;
		JButton btn_save= new JButton("保存哈夫曼树（Save）");
		btn_save.setFocusPainted(false);
		btn_save.setBounds(x, y, width, height);
		btn_save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_save();
			}
		});
		this.add(btn_save);
		
		y += height + padding;
		JButton btn_load= new JButton("读取哈夫曼树（Load）");
		btn_load.setFocusPainted(false);
		btn_load.setBounds(x, y, width, height);
		btn_load.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_load();
			}
		});
		this.add(btn_load);
		
		y += height + padding;
		JLabel label_3 = new JLabel("额外功能：");
		label_3.setBounds(x, y, width, height);
		this.add(label_3);
		
		y += height + padding;
		JButton btn_6= new JButton("自动扫描文件，并生成哈夫曼树");
		btn_6.setFocusPainted(false);
		btn_6.setBounds(x, y, width, height);
		btn_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_6();
			}
		});
		this.add(btn_6);
		
		y += height + padding;
		JButton btn_7= new JButton("哈夫曼树生成过程演示");
		btn_7.setFocusPainted(false);
		btn_7.setBounds(x, y, width, height);
		btn_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_7();
			}
		});
		this.add(btn_7);
		
		y += height + padding;
		JLabel label_4 = new JLabel("当前哈夫曼树信息：");
		label_4.setBounds(x, y, width, height);
		this.add(label_4);
		
		x += 10;
		y += height + padding/2;
		this.treeNode = new JLabel("节点数：未初始化");
		this.treeNode.setBounds(x, y, width, height);
		this.add(this.treeNode);
		
		y += height;
		this.treeWeight = new JLabel("总重量：未初始化");
		this.treeWeight.setBounds(x, y, width, height);
		this.add(this.treeWeight);
	}
	
	protected void createHelp() {
		int x = 0, y = 564, width = 1018, height = 25;
		
		this.help = new JTextField("欢迎使用数据结构演示程序！");
		this.help.setEditable(false);
		this.help.setBackground(Color.WHITE);
		this.help.setBounds(x, y, width, height);
		this.help.setMargin(new Insets(0, 10, 0, 10));
		this.add(this.help);
	}
	
	protected void createCanvas() {
		int x = 260, y = 20, width = 738, height = 524;
		
		this.canvas = new CanvasPanel();
		this.canvas.setBackground(Color.WHITE);
		this.canvas.setBounds(x, y, width, height);
		this.canvas.refresh();
		this.add(this.canvas);
	}

	public void updateHelp(String text) {
		this.help.setText(text);
		if(Main.tree != null) {
			this.treeNode.setText("节点数：" + Main.tree.size());
			this.treeWeight.setText("总重量：" + Main.tree.weight());
		}
	}
	
	public void active() {
		this.setVisible(true);
	}

	public void deactive() {
		this.setVisible(false);
	}
	
	public void func_btn_1() {}
	public void func_btn_2() {}
	public void func_btn_3() {}
	public void func_btn_4() {}
	public void func_btn_5() {}
	public void func_btn_save() {}
	public void func_btn_load() {}
	public void func_btn_6() {}
	public void func_btn_7() {}
}
