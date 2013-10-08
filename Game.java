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
		Graphic panel = new Graphic();
		add(panel);
		//this.addKeyListener(new Graphic.ColorAction());
	}
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 600;
}

class Graphic extends JComponent {
	private Rectangle2D rect1; 
	private Rectangle2D rect2;
	private Rectangle2D rect3;
	private Rectangle2D rect4;
	private Rectangle2D c1;
	private Rectangle2D c2;
	private Rectangle2D c3;
	private Rectangle2D c4;
	private double x1 = 0.0;
	private double x2 = 250.0;
	private double x3 = 0.0;
	private double x4 = 250.0;
	private double y1 = 0.0;
	private double y2 = 0.0;
	private double y3 = 250.0;
	private double y4 = 250.0;
	private double width = 50.0;
	private double height = 50.0;
	private double cx1 = 100.0;
	private double cx2 = 150.0;
	private double cx3 = 100.0;
	private double cx4 = 150.0;
	private double cy1 = 100.0;
	private double cy2 = 100.0;
	private double cy3 = 150.0;
	private double cy4 = 150.0;
	private Color color1 = Color.BLUE;
	private Color color2 = Color.RED;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//g.drawString("A Big Game", 100, 100);
		rect1 = new Rectangle2D.Double(x1, y1, width, height);
		rect2 = new Rectangle2D.Double(x2, y2, width, height);
		rect3 = new Rectangle2D.Double(x3, y3, width, height);
		rect4 = new Rectangle2D.Double(x4, y4, width, height);
		g2.setPaint(color1);
		g2.fill(rect1);
		g2.fill(rect2);
		g2.fill(rect3);
		g2.fill(rect4);
		g2.draw(rect1);
		g2.draw(rect2);
		g2.draw(rect3);
		g2.draw(rect4);
		c1 = new Rectangle2D.Double(cx1, cy1, width, height);
		c2 = new Rectangle2D.Double(cx2, cy2, width, height);
		c3 = new Rectangle2D.Double(cx3, cy3, width, height);
		c4 = new Rectangle2D.Double(cx4, cy4, width, height);
		g2.setPaint(color2);
		g2.draw(c1);
		g2.draw(c2);
		g2.draw(c3);
		g2.draw(c4);

	}
	public class ColorAction implements KeyListener {
		public ColorAction() {}
		public void keyTyped(KeyEvent e) {}    
      	public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			char keyChar = e.getKeyChar();
			switch (keyChar) {  //KP_UP
				case KeyEvent.VK_1:

					break;
				case KeyEvent.VK_2:

					break;
				case KeyEvent.VK_3:

					break;
				case KeyEvent.VK_4:

					break;
			}
		}
		
	}
}