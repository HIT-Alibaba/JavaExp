import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Game {
	public static void main(String[] args) {
		SimpleFrame frame = new SimpleFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}

class SimpleFrame extends JFrame {
	public Graphic panel;
	public SimpleFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;
		int frameW = 300; //screenW / 2;
		int frameH = 350; //screenH / 2;
		int x = screenW / 4;
		int y = screenH / 4;
		setSize(frameW, frameH);
		//setLocation(x, y);
		setLocationRelativeTo(null);
		setTitle("大游戏");
		panel = new Graphic();
		add(panel);
		this.addKeyListener(new ColorAction());
	}
	private class ColorAction implements KeyListener {
		private Rectangle2D choRect = null;
		private int choNum;
		private double x, y;
		public ColorAction() {}
		public void keyTyped(KeyEvent e) {}    
      	public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			char keyChar = e.getKeyChar();
			switch (keyChar) {
				case KeyEvent.VK_1:
					choRect = panel.rect1;
					choNum = 1;
					setToDefault();
					panel.color1 = Color.RED;
					break;
				case KeyEvent.VK_2:
					choRect = panel.rect2;
					choNum = 2;
					setToDefault();
					panel.color2 = Color.RED;
					break;
				case KeyEvent.VK_3:
					choRect = panel.rect3;
					choNum = 3;
					setToDefault();
					panel.color3 = Color.RED;
					break;
				case KeyEvent.VK_4:
					choRect = panel.rect4;
					choNum = 4;
					setToDefault();
					panel.color4 = Color.RED;
					break;
				case KeyEvent.VK_UP:
					if (choRect != null && choRect.getY() > 0.0 && enabled(choRect, -50.0, false)) {
						switch (choNum) {
							case 1:
								panel.y1 -= 50.0;
								break;
							case 2:
								panel.y2 -= 50.0;
								break;
							case 3:
								panel.y3 -= 50.0;
								break;
							case 4:
								panel.y4 -= 50.0;
								break;
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if (choRect != null && choRect.getY() < 250.0 && enabled(choRect, 50.0, false)) {
						switch (choNum) {
							case 1:
								panel.y1 += 50.0;
								break;
							case 2:
								panel.y2 += 50.0;
								break;
							case 3:
								panel.y3 += 50.0;
								break;
							case 4:
								panel.y4 += 50.0;
								break;
						}
					}
					break;
				case KeyEvent.VK_LEFT:
					if (choRect != null && choRect.getX() > 0.0 && enabled(choRect, -50.0, true)) {
						switch (choNum) {
							case 1:
								panel.x1 -= 50.0;
								break;
							case 2:
								panel.x2 -= 50.0;
								break;
							case 3:
								panel.x3 -= 50.0;
								break;
							case 4:
								panel.x4 -= 50.0;
								break;
						}
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (choRect != null && choRect.getX() < 250.0 && enabled(choRect, 50.0, true)) {
						switch (choNum) {
							case 1:
								panel.x1 += 50.0;
								break;
							case 2:
								panel.x2 += 50.0;
								break;
							case 3:
								panel.x3 += 50.0;
								break;
							case 4:
								panel.x4 += 50.0;
								break;
						}
					}
					break;
			}
			panel.isDone1 = (panel.x1 == panel.cx1 && panel.y1 == panel.cy1);
			panel.isDone2 = (panel.x2 == panel.cx2 && panel.y2 == panel.cy2);
			panel.isDone3 = (panel.x3 == panel.cx3 && panel.y3 == panel.cy3);
			panel.isDone4 = (panel.x4 == panel.cx4 && panel.y4 == panel.cy4);
			panel.repaint();
		}
			
		public void setToDefault() {
			panel.color1 = Color.BLUE;
			panel.color2 = Color.BLUE;
			panel.color3 = Color.BLUE;
			panel.color4 = Color.BLUE;
		}

		public Boolean enabled(Rectangle2D choRect, double i, Boolean isX) {
			double nextX = choRect.getX(),
				   nextY = choRect.getY();
			double x1 = panel.x1,
				   x2 = panel.x2,
				   x3 = panel.x3,
				   x4 = panel.x4;
			double y1 = panel.y1,
				   y2 = panel.y2,
				   y3 = panel.y3,
				   y4 = panel.y4;
			
			if (isX) nextX += i;
			else nextY += i;
			if (nextX == x1 && nextY == y1) return false;
			if (nextX == x2 && nextY == y2) return false;
			if (nextX == x3 && nextY == y3) return false;
			if (nextX == x4 && nextY == y4) return false;
			return true;
		}
	}
}

class Graphic extends JComponent {
	public Rectangle2D rect1; 
	public Rectangle2D rect2;
	public Rectangle2D rect3;
	public Rectangle2D rect4;
	public double x1 = 0.0;
	public double x2 = 250.0;
	public double x3 = 0.0;
	public double x4 = 250.0;
	public double y1 = 0.0;
	public double y2 = 0.0;
	public double y3 = 250.0;
	public double y4 = 250.0;
	public double width = 50.0;
	public double height = 50.0;
	public double cx1 = 100.0;
	public double cx2 = 150.0;
	public double cx3 = 100.0;
	public double cx4 = 150.0;
	public double cy1 = 100.0;
	public double cy2 = 100.0;
	public double cy3 = 150.0;
	public double cy4 = 150.0;
	public Color color1 = Color.BLUE;
	public Color color2 = Color.BLUE;
	public Color color3 = Color.BLUE;
	public Color color4 = Color.BLUE;
	public Color colorC = Color.RED;
	public Boolean isDone1 = false;
	public Boolean isDone2 = false;
	public Boolean isDone3 = false;
	public Boolean isDone4 = false;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		rect1 = new Rectangle2D.Double(x1, y1, width, height);
		rect2 = new Rectangle2D.Double(x2, y2, width, height);
		rect3 = new Rectangle2D.Double(x3, y3, width, height);
		rect4 = new Rectangle2D.Double(x4, y4, width, height);
		g2.setPaint(color1);
		g2.fill(rect1);
		g2.setPaint(color2);
		g2.fill(rect2);
		g2.setPaint(color3);
		g2.fill(rect3);
		g2.setPaint(color4);
		g2.fill(rect4);
		g2.draw(rect1);
		g2.draw(rect2);
		g2.draw(rect3);
		g2.draw(rect4);
		Rectangle2D c1 = new Rectangle2D.Double(cx1, cy1, width, height);
		Rectangle2D c2 = new Rectangle2D.Double(cx2, cy2, width, height);
		Rectangle2D c3 = new Rectangle2D.Double(cx3, cy3, width, height);
		Rectangle2D c4 = new Rectangle2D.Double(cx4, cy4, width, height);
		g2.setPaint(colorC);
		g2.draw(c1);
		g2.draw(c2);
		g2.draw(c3);
		g2.draw(c4);
		if (isDone4 && isDone3 && isDone2 && isDone1) {
			g2.drawString("游戏结束", 100, 100);
		}
	}
}