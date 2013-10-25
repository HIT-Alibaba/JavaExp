import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MyQQ {
	public static void main (String[] args) {
		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class Frame extends JFrame {
	public Frame() throws IOException{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;
		int frameW = 600;
		int frameH = 650;
		int x = screenW / 4;
		int y = screenH / 4;
		setSize(frameW, frameH);
		setLocationRelativeTo(null);
		setTitle("chat～");

		add();
	}
}