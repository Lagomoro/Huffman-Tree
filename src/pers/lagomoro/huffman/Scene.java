package pers.lagomoro.huffman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Scene {
	
	public static final int STROKE_INSIDE = -1;
	public static final int STROKE_MIDDLE = 0;
	public static final int STROKE_OUTSIDE = 1;
	
	public Scene() {
		// TODO 自动生成的构造函数存根
	}
	
	public void paint(Graphics2D graphics, int width, int height) {
		
	}
	
	public void clearRect(Graphics2D graphics, int x, int y, int width, int height) {
		graphics.clearRect(x, y, width, height);
	}
	
	public void setXORMode(Graphics2D graphics, Color alt) {
		graphics.setXORMode(alt);
	}
	
	public void setPaintMode(Graphics2D graphics) {
		graphics.setPaintMode();
	}
	
	public void drawCircle(Graphics2D graphics, int ox, int oy, int radius, int lineWidth, int drawMode, Color color) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		int sp = 0 - radius - (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
		int sd = radius * 2 + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth);
		graphics.drawOval(ox + sp, oy + sp, sd, sd);
	}
	
	public void fillCircle(Graphics2D graphics, int ox, int oy, int radius, Color color) {
		graphics.setColor(color);
		graphics.fillOval(ox - radius, oy - radius, radius * 2, radius * 2);
	}
	
	public void drawArc(Graphics2D graphics, int ox, int oy, int radius, int startAngle, int arcAngle, int lineWidth, int drawMode, Color color) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		int sp = 0 - radius - (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
		int sd = radius * 2 + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth);
		graphics.drawArc(ox + sp, oy + sp, sd, sd, startAngle, arcAngle);
	}

	public void fillArc(Graphics2D graphics, int ox, int oy, int radius, int startAngle, int arcAngle, Color color) {
		graphics.setColor(color);
		graphics.fillArc(ox - radius, oy - radius, radius * 2, radius * 2, startAngle, arcAngle);
	}

	public void drawRect(Graphics2D graphics, int x, int y, int width, int height, int lineWidth, int drawMode, Color color) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		int sp = 0 - (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
		int sd = 0 + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth);
		graphics.drawRect(x + sp, y + sp, width + sd, height + sd);
	}
	
	public void fillRect(Graphics2D graphics, int x, int y, int width, int height, Color color) {
		graphics.setColor(color);
		graphics.fillRect(x, y, width, height);
	}
	
	public void drawRoundRect(Graphics2D graphics, int x, int y, int width, int height, int radius, int lineWidth, int drawMode, Color color) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		int sp = 0 - (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
		int sd = 0 + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth);
		int sr = radius + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
		graphics.drawRoundRect(x + sp, y + sp, width + sd, height + sd, sr, sr);
	}

	public void fillRoundRect(Graphics2D graphics, int x, int y, int width, int height, int radius, Color color) {
		graphics.setColor(color);
		graphics.drawRoundRect(x, y, width, height, radius, radius);		
	}
	
	public void drawPolygon(Graphics2D graphics, int xPoints[], int yPoints[], int nPoints, Color color) {
		graphics.setColor(color);
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	public void fillPolygon(Graphics2D graphics, int xPoints[], int yPoints[], int nPoints, Color color) {
		graphics.setColor(color);
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	protected int[] polarToRect(int r, int s) {
		return new int[]{(int) (r * Math.cos(Math.PI*s/180)), (int) (r * Math.sin(Math.PI*s/180))};
	}
	
	public void drawRegularTriangle(Graphics2D graphics, int ox, int oy, int distance, int angle, int lineWidth, int drawMode, Color color) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		int sd = distance + (int)(Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * (lineWidth/Math.cos(60/180*Math.PI)));
		int[] dot1 = polarToRect(sd, angle);
		int[] dot2 = polarToRect(sd, angle + 120);
		int[] dot3 = polarToRect(sd, angle + 240);
		graphics.drawPolygon(new int[] {ox + dot1[0], ox + dot2[0], ox + dot3[0]}, new int[] {oy + dot1[1], oy + dot2[1], oy + dot3[1]},3);
	}
	
	public void fillRegularTriangle(Graphics2D graphics, int ox, int oy, int distance, int angle, Color color) {
		graphics.setColor(color);
		int[] dot1 = polarToRect(distance, angle);
		int[] dot2 = polarToRect(distance, angle + 120);
		int[] dot3 = polarToRect(distance, angle + 240);
		graphics.fillPolygon(new int[] {ox + dot1[0], ox + dot2[0], ox + dot3[0]}, new int[] {oy + dot1[1], oy + dot2[1], oy + dot3[1]},3);
	}
	
	public void setFontSize(Graphics2D graphics, int size) {
		Font font = graphics.getFont();
		graphics.setFont(new Font(font.getName(), font.getStyle(), size));
	}
	
}
