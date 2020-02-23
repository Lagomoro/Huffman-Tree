package pers.lagomoro.huffman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TreeScene extends Scene {

	public final Color COLOR_WEIGHT  = Color.BLUE;
	public final Color COLOR_CODE    = Color.RED;
	public final Color COLOR_DEFAULT = Color.BLACK;
	
	public final double PADDING_LEFT = 0.1;
	public final double PADDING_TOP = 0.1;
	public final double CONTENT_WIDTH = 0.8;
	public final double CONTENT_HEIGHT = 0.65;
	
	public TreeSceneNode[] nodes = null;
	public AnimateNode[] animate = null;
	public String info = null;
	public int nowFrame = 0;
	public int jumpFrame = 0;
	
	public String animateName = "没有存在的动画序列";
	public double animateSpeed = 1.0;
	
	public boolean animatePause = false;
	public boolean animateStop = false;
	
	public TreeScene() {
		// TODO 自动生成的构造函数存根
	}
	
	public void setNodes(TreeSceneNode[] nodes) {
		this.nodes = nodes;
	}
	
	public void setAnimate(AnimateNode[] animate, String animateName) {
		this.animate = animate;
		this.animateName = animateName;
	}
	
	public void startAnimate(CanvasPanel canvas) {
		if(this.nodes != null && this.animate != null && !canvas.isBusy) {
			new Thread(()->{
				canvas.isBusy = true;
				AnimateNode frame;
				for(nowFrame = 0; nowFrame < animate.length; nowFrame ++) {
					frame = animate[nowFrame];

					switch(frame.animate) {
					case "sleep":
					case "info":
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "move":
						if(frame.nodeID > -1) {
							if(frame.x > -1)
								this.nodes[frame.nodeID].x = frame.x;
							if(frame.y > -1)
								this.nodes[frame.nodeID].y = frame.y;
						}
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "resetPlace":
						if(frame.nodeID > -1)
							this.nodes[frame.nodeID].resetPlace();
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "color":
						if(frame.nodeID > -1) {
							if(frame.color != null)
								this.nodes[frame.nodeID].color = frame.color;
						}
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "resetColor":
						if(frame.nodeID > -1)
							this.nodes[frame.nodeID].resetColor();
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "show":
						if(frame.nodeID > -1)
							this.nodes[frame.nodeID].hide = false;
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					case "hide":
						if(frame.nodeID > -1)
							this.nodes[frame.nodeID].hide = true;
						if(frame.info != null)
							info = frame.info;
						if(jumpFrame > 0) {jumpFrame --; break;}
						canvas.refresh();
						if(frame.sleepTime > 0)
							try {Thread.sleep((int)(frame.sleepTime / animateSpeed));} catch (InterruptedException e) {e.printStackTrace();}
						break;
					}
					while(animatePause && jumpFrame <= 0) {
						canvas.refresh();
						try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
						if(animateStop && jumpFrame <= 0)
							animatePause = false;
					}
					if(animateStop){
						animateStop = false;
						if(jumpFrame > 0)
							nowFrame = 0;
						else
							break;
					}
				}
				this.info = null;
				for(int i = 0; i < this.nodes.length; i++) {
					this.nodes[i].hide = false;
					this.nodes[i].resetColor();
					this.nodes[i].resetPlace();
				}
				canvas.refresh();
				canvas.isBusy = false;
				Main.window.updateHelp("动画播放完毕。");
			}).start();
		}
	}
	
	public int getLastCheckpoint(){
		for(int i = this.nowFrame - 1; i > 0;i--) {
			if(this.animate[i].checkpoint) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean setLastCheckpoint(CanvasPanel canvas){
		if(canvas.isBusy) {
			if(this.getLastCheckpoint() != -1) {
				this.jumpFrame = this.getLastCheckpoint();
				this.animatePause = true;
				this.animateStop = true;
				return true;
			}
		}
		return false;
	}
	
	public int getNextCheckpoint(){
		for(int i = this.nowFrame + 1; i < this.animate.length;i++) {
			if(this.animate[i].checkpoint) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean setNextCheckpoint(CanvasPanel canvas){
		if(canvas.isBusy) {
			if(this.getNextCheckpoint() != -1) {
				this.jumpFrame = this.getNextCheckpoint();
				this.animatePause = true;
				this.animateStop = true;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paint(Graphics2D graphics, int width, int height) {
		super.paint(graphics, width, height);
		if(this.nodes != null) {
			int radius = (int)((1.0/this.nodes.length) * width * 0.8);
			radius = Math.min(radius, width/50);
			for(TreeSceneNode node : nodes) {
				if(node.hide)
					continue;
					
				int x = (int)(node.x * width * CONTENT_WIDTH + width * PADDING_LEFT);
				int y = (int)(node.y * height * CONTENT_HEIGHT + height * PADDING_TOP);
				this.drawNode(graphics, x, y, radius, node.color, node.element, node.weight);
			
				graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				if(node.leftNode >= 0) {
					graphics.setColor(COLOR_DEFAULT);
					int x2 = (int)(nodes[node.leftNode].x * width * CONTENT_WIDTH + width * PADDING_LEFT);
					int y2 = (int)(nodes[node.leftNode].y * height * CONTENT_HEIGHT + height * PADDING_TOP);
					graphics.drawLine(x - (int)(radius * 0.1), y + radius, x2, y2 - radius);
					
					graphics.setColor(COLOR_CODE);
					this.setFontSize(graphics, (int)(radius * 0.9));
					FontMetrics fontMetrics = graphics.getFontMetrics();
					int textx = x - radius - (fontMetrics.stringWidth("0") / 2);
					int texty = y + (int)(radius * 1.1) + (fontMetrics.getHeight() / 4 * 3);
					graphics.drawString("0", textx, texty);
				}
				if(node.rightNode >= 0) {
					graphics.setColor(COLOR_DEFAULT);
					int x2 = (int)(nodes[node.rightNode].x * width * CONTENT_WIDTH + width * PADDING_LEFT);
					int y2 = (int)(nodes[node.rightNode].y * height * CONTENT_HEIGHT + height * PADDING_TOP);
					graphics.drawLine(x + (int)(radius * 0.1), y + radius, x2, y2 - radius);
				
					graphics.setColor(COLOR_CODE);
					this.setFontSize(graphics, (int)(radius * 0.9));
					FontMetrics fontMetrics = graphics.getFontMetrics();
					int textx = x + radius - (fontMetrics.stringWidth("1") / 2);
					int texty = y + (int)(radius * 1.1) + (fontMetrics.getHeight() / 4 * 3);
					graphics.drawString("1", textx, texty);
				}
			}
		}
		if(this.info != null) {
			String[] infos = info.split("\n");
			graphics.setColor(COLOR_DEFAULT);
			this.setFontSize(graphics, 16);
			graphics.drawString(infos[0], 50, 560);
			for(int i = 1; i < infos.length; i++)
				graphics.drawString(infos[i], 50, 580 + i * 20);
		}
	}
	
	public void drawNode(Graphics2D graphics, int x, int y, int radius, Color color, Character text, int weight) {
		if(color != null)
			this.fillCircle(graphics, x, y, radius, color);
		this.drawCircle(graphics, x, y, radius, 1, 0, COLOR_DEFAULT);
		FontMetrics fontMetrics = null;
		int textx, texty;
		if(text != null) {
			graphics.setColor(COLOR_DEFAULT);
			this.setFontSize(graphics, (int)(radius * 1.2));
			fontMetrics = graphics.getFontMetrics();
			textx = x - (fontMetrics.charWidth(text) / 2);
			texty = y + (fontMetrics.getHeight() / 4);
			graphics.drawString(text.toString(), textx, texty);
			
			graphics.setColor(COLOR_WEIGHT);
			this.setFontSize(graphics, (int)(radius * 0.9));
			fontMetrics = graphics.getFontMetrics();
			textx = x - (fontMetrics.stringWidth(Integer.toString(weight)) / 2);
			texty = y + (int)(radius * 1.1) + (fontMetrics.getHeight() / 4 * 3);
			graphics.drawString(Integer.toString(weight), textx, texty);
		} else {
			graphics.setColor(COLOR_WEIGHT);
			this.setFontSize(graphics, (int)(radius * 0.9));
			fontMetrics = graphics.getFontMetrics();
			textx = x - (fontMetrics.stringWidth(Integer.toString(weight)) / 2);
			texty = y + (fontMetrics.getHeight() / 4);
			graphics.drawString(Integer.toString(weight), textx, texty);
		}
	}

}
