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
	JLabel treeNode, treeWeight, treeHeight, animate, speed;
	JButton btn_pause;
	CanvasPanel canvas;

	public MainWindow(){
		this.initialize();
		this.create();
	}
	
	protected void initialize() {
		this.setTitle("���ݽṹ�γ������ʾϵͳ - ��������/������ - 201800301015 �����");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setSize(1024, 624);
		this.setSize(1280, 768);
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
		
		JLabel label_1 = new JLabel("�������ܣ�");
		label_1.setBounds(x, y, width, height);
		this.add(label_1);
		
		y += height + padding;
		JButton btn_1 = new JButton("�ֶ���ʼ����Initialization��");
		btn_1.setFocusPainted(false);
		//btn_1.setForeground(Color.lightGray);
		btn_1.setBounds(x, y, width, height);
		btn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_1();
				//Main.window.updateHelp("JAR���з�ʽû�п���̨����֧���ֶ����롣");
			}
		});
		this.add(btn_1);
		
		y += height + padding;
		JButton btn_2 = new JButton("���루Coding��");
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
		JButton btn_3 = new JButton("���루Decoding��");
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
		JButton btn_4 = new JButton("��ӡ�����ļ���Print��");
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
		JButton btn_5 = new JButton("��ӡ����������Tree printing��");
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
		JLabel label_2 = new JLabel("�������ȡ��");
		label_2.setBounds(x, y, width, height);
		this.add(label_2);
		
		y += height + padding;
		JButton btn_save = new JButton("�������������Save��");
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
		JButton btn_load = new JButton("��ȡ����������Load��");
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
		JLabel label_3 = new JLabel("���⹦�ܣ�");
		label_3.setBounds(x, y, width, height);
		this.add(label_3);
		
		y += height + padding;
		JButton btn_6 = new JButton("�Զ�ɨ���ļ��������ɹ�������");
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
		JLabel label_4 = new JLabel("��ǰ����������Ϣ��");
		label_4.setBounds(x, y, width, height);
		this.add(label_4);
		
		x += 10;
		y += height + padding/2;
		this.treeNode = new JLabel("�ڵ�����δ��ʼ��");
		this.treeNode.setBounds(x, y, width, height);
		this.add(this.treeNode);
		
		y += height;
		this.treeWeight = new JLabel("��������δ��ʼ��");
		this.treeWeight.setBounds(x, y, width, height);
		this.add(this.treeWeight);
		
		y += height;
		this.treeHeight = new JLabel("���߶ȣ�δ��ʼ��");
		this.treeHeight.setBounds(x, y, width, height);
		this.add(this.treeHeight);
		
		x -= 10;
		y += height + padding;
		JLabel label_5 = new JLabel("��ǰ����������Ϣ��");
		label_5.setBounds(x, y, width, height);
		this.add(label_5);
		
		x += 10;
		y += height;
		this.animate = new JLabel("û�д��ڵĶ�������");
		this.animate.setBounds(x, y, width, height);
		this.add(this.animate);
		
		x -= 10;
		y += height + padding/2;
		width = 70;
		padding = 5;
		JButton btn_play = new JButton("����");
		btn_play.setFocusPainted(false);
		btn_play.setBounds(x, y, width, height);
		btn_play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_play();
			}
		});
		this.add(btn_play);
		
		x += width + padding;
		btn_pause = new JButton("��ͣ");
		btn_pause.setFocusPainted(false);
		btn_pause.setBounds(x, y, width, height);
		btn_pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_pause();
			}
		});
		this.add(btn_pause);
		
		x += width + padding;
		JButton btn_stop = new JButton("ֹͣ");
		btn_stop.setFocusPainted(false);
		btn_stop.setBounds(x, y, width, height);
		btn_stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_stop();
			}
		});
		this.add(btn_stop);
		
		
		x = 20;
		padding = 10;
		y += height + padding/2;
		width = 107;
		padding = 6;
		JButton btn_prev = new JButton("��һ��");
		btn_prev.setFocusPainted(false);
		btn_prev.setBounds(x, y, width, height);
		btn_prev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_prev();
			}
		});
		this.add(btn_prev);
		
		x += width + padding;
		JButton btn_next = new JButton("��һ��");
		btn_next.setFocusPainted(false);
		btn_next.setBounds(x, y, width, height);
		btn_next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_next();
			}
		});
		this.add(btn_next);
		
		x = 30;
		padding = 10;
		y += height + padding;
		width = 90;
		padding = 5;
		JLabel label_6 = new JLabel("���������ٶ�");
		label_6.setBounds(x, y, width, height);
		this.add(label_6);
		
		x += width + padding;
		width = 25;
		JButton btn_slow = new JButton("-");
		btn_slow.setFocusPainted(false);
		btn_slow.setBounds(x, y, width, height);
		btn_slow.setMargin(new Insets(0, 0, 0, 0));
		btn_slow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_slow();
			}
		});
		this.add(btn_slow);
		
		x += width + padding;
		width = 45;
		this.speed = new JLabel("1.0");
		this.speed.setBounds(x, y, width, height);
		this.speed.setHorizontalAlignment(JLabel.CENTER);
		this.add(this.speed);
		
		x += width + padding;
		width = 25;
		JButton btn_fast = new JButton("+");
		btn_fast.setFocusPainted(false);
		btn_fast.setBounds(x, y, width, height);
		btn_fast.setMargin(new Insets(0, 0, 0, 0));
		btn_fast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				func_btn_fast();
			}
		});
		this.add(btn_fast);
	}
	
	protected void createHelp() {
		int x = 0, y = 564 + 144, width = 1018 + 256, height = 25;
		
		this.help = new JTextField("��ӭʹ�� ���ݽṹ�γ������ʾϵͳ��");
		this.help.setEditable(false);
		this.help.setBackground(Color.WHITE);
		this.help.setBounds(x, y, width, height);
		this.help.setMargin(new Insets(0, 10, 0, 10));
		this.add(this.help);
	}
	
	protected void createCanvas() {
		int x = 260, y = 20, width = 738 + 256, height = 524 + 144;
		
		this.canvas = new CanvasPanel();
		this.canvas.setBackground(Color.WHITE);
		this.canvas.setBounds(x, y, width, height);
		this.canvas.refresh();
		this.add(this.canvas);
	}

	public void updateHelp(String text) {
		this.help.setText(text);
		if(Main.tree != null) {
			this.treeNode.setText("�ڵ�����" + Main.tree.size());
			this.treeWeight.setText("��������" + Main.tree.weight());
			this.treeHeight.setText("���߶ȣ�" + Main.tree.treeHeight());
			this.animate.setText(Main.treeScene.animateName);
			this.btn_pause.setText(Main.treeScene.animatePause ? "����" : "��ͣ");
		}
		this.speed.setText(Double.toString(Main.treeScene.animateSpeed));
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
	public void func_btn_play() {}
	public void func_btn_pause() {}
	public void func_btn_stop() {}
	public void func_btn_prev() {}
	public void func_btn_next() {}
	public void func_btn_slow() {}
	public void func_btn_fast() {}
}
