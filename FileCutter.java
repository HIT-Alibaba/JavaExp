import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class FileCutter {
	public static void main (String[] args) {
		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class Frame extends JFrame {
	public Frame() {
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
		setTitle("文件分割器");

		Panel panel = new Panel();
		//panel.setLayout(new GridLayout(1,1));

		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setTabPlacement(JTabbedPane.TOP);
		CutPanel cutPanel = new CutPanel();
		MergePanel mergePanel = new MergePanel();

		tabPane.addTab("分割", cutPanel);
		tabPane.addTab("合并", mergePanel);
		tabPane.setSelectedIndex(0);
		panel.add(tabPane);
		this.add(panel);
	}
}

class CutPanel extends JPanel {

	JTextField filename = new JTextField(),
			   filesize = new JTextField();

	JFileChooser fileChooser = new JFileChooser();

	JButton btCut = new JButton("开始切割");

	JRadioButton rbtn1 = new JRadioButton("1.44MB",true),  
		 		 rbtn2 = new JRadioButton("1.20MB"),
		 		 rbtn3 = new JRadioButton("720KB"),
		 		 rbtn4 = new JRadioButton("360KB"),
		 		 rbtnS = new JRadioButton("自定义块的大小(单位：KB):");

	public CutPanel() {
		JLabel nameLabel = new JLabel("文件名:"),
			   sizeLabel = new JLabel("文件大小(byte):"),
			   blockLabel = new JLabel("块大小:"),
			   numLabel = new JLabel("块数量:"),
			   tarLabel = new JLabel("目标文件:");

		ButtonGroup buttonGroup = new ButtonGroup();

		this.addActionListener(new Listener());

		buttonGroup.add(rbtn1);
		buttonGroup.add(rbtn2);
		buttonGroup.add(rbtn3);
		buttonGroup.add(rbtn4);
		buttonGroup.add(rbtnS);

		add(rbtnS);
		add(rbtn1);
		add(rbtn2);
		add(rbtn3);
		add(rbtn4);
		add(rbtnS);
		add(nameLabel);
		add(sizeLabel);
		//add(fileChooser);
		add(btCut);
	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btCut)
				add(fileChooser);
		}
	}
}

class MergePanel extends JPanel /*implements ActionListener*/ {
	public MergePanel() {
		
		JButton btMerge = new JButton("开始合并");
		add(btMerge);
	}
}