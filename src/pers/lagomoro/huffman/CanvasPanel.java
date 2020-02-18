package pers.lagomoro.huffman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CanvasPanel extends JPanel{
	
	protected Scene scene = null;
	public boolean isBusy = false;
	
	public CanvasPanel(){
		super();
		this.initialize();
		this.refresh();
	}

	protected void initialize() {
		this.setOpaque(false);
	}
	
	public void refresh() {
		this.revalidate();
		this.repaint();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		if(Main.window.canvas.isBusy)
			Main.treeScene.animateStop = true;
		this.refresh();
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	    graphics.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 180);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		this.paint(graphics);
    }
	
	protected void paint(Graphics2D graphics) {
		this.clear(graphics);
		graphics.setColor(this.getBackground());
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(this.scene != null)
			this.scene.paint(graphics, this.getWidth(), this.getHeight());
	}
	
	public void clear(Graphics2D graphics) {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void drawImage(Graphics2D graphics, Image image, int x, int y, int width, int height, int target) {
		int realX = x - (target - 1) % 3 * width / 2;
		int realY = y - (9 - target) / 3 * height / 2;
		graphics.drawImage(image, realX, realY, width, height, this);
	}
	
}
