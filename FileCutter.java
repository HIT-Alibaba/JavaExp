import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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

		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setTabPlacement(JTabbedPane.TOP);
		CutPanel cutPanel = new CutPanel();
		MergePanel mergePanel = new MergePanel();

		tabPane.addTab("分割", cutPanel);
		tabPane.addTab("合并", mergePanel);
		tabPane.setSelectedIndex(0);
		add(tabPane);
		//tabPane.addActionListener();
		//cutPanel.addActionListener();
	}
}

class CutPanel extends JPanel {
	JTextField filename = new JTextField(),
			   filesize = new JTextField(),
			   blocksize = new JTextField(),
			   blocknum = new JTextField(),
			   tarPath = new JTextField();

	JFileChooser fileChooser = new JFileChooser();

	JButton btCut = new JButton("开始切割"),
			btCho = new JButton("源文件"),
			btTar = new JButton("目标文件");

	JRadioButton rbtn1 = new JRadioButton("1.44MB",true),  
		 		 rbtn2 = new JRadioButton("1.20MB"),
		 		 rbtn3 = new JRadioButton("720KB"),
		 		 rbtn4 = new JRadioButton("360KB"),
		 		 rbtnS = new JRadioButton("自定义块的大小(单位：KB):");

	JProgressBar progressbar = new JProgressBar();

	public CutPanel() {
		setLayout(null);
		JLabel nameLabel = new JLabel("文件名:"),
			   sizeLabel = new JLabel("文件大小(byte):"),
			   blockLabel = new JLabel("块大小:"),
			   numLabel = new JLabel("块数量:"),
			   tarLabel = new JLabel("保存目录:");
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtn1);
		buttonGroup.add(rbtn2);
		buttonGroup.add(rbtn3);
		buttonGroup.add(rbtn4);
		buttonGroup.add(rbtnS);
		add(rbtn1);
		add(rbtn2);
		add(rbtn3);
		add(rbtn4);
		add(rbtnS);
		add(nameLabel);
		add(sizeLabel);
		add(blockLabel);
		add(numLabel);
		add(tarLabel);
		add(btCut);
		add(filename);
		add(filesize);
		add(btCho);
		add(btTar);
		add(blocksize);
		add(blocknum);
		add(tarPath);
		add(progressbar);
		filename.setBounds(200, 50, 250, 20);
		filesize.setBounds(200, 90, 250, 20);
		btCut.setBounds(100, 400, 100, 20);
		btCho.setBounds(460, 50, 100, 20);
		blocksize.setBounds(400, 260, 100, 20);
		nameLabel.setBounds(100, 50, 100, 20);
		sizeLabel.setBounds(100, 90, 100, 20);
		blockLabel.setBounds(100, 200, 100, 20);
		numLabel.setBounds(100, 300, 100, 20);
		tarLabel.setBounds(100, 330, 100, 20);
		rbtn1.setBounds(200, 200, 200, 20);
		rbtn2.setBounds(400, 200, 200, 20);
		rbtn3.setBounds(200, 230, 200, 20);
		rbtn4.setBounds(400, 230, 200, 20);
		rbtnS.setBounds(200, 260, 200, 20);
		blocknum.setBounds(210, 300, 100, 20);
		tarPath.setBounds(210, 330, 100, 20);
		btTar.setBounds(320, 330, 100, 20);
		progressbar.setBounds(210, 400, 300, 20);

		//this.addActionListener(new Listener());
	}
	
	private class Listener implements ActionListener {
		private File targetpath = NULL, sourcefile = NULL;
		private int blockBytes = 0;
		public void actionPerformed(ActionEvent e) {
			if (rbtn1.isSelected())
				blockBytes = 1.44 * 1024 * 1024;
			if (rbtn2.isSelected())
				blockBytes = 1.2 * 1024 * 1024;
			if (rbtn3.isSelected())
				blockBytes = 720 * 1024;
			if (rbtn4.isSelected())
				blockBytes = 360 * 1024;
			if (rbtnS.isSelected()) {
				blockBytes = Integer.parseInt(blocksize.getText()) * 1024;
			}
			if (e.getSource() == btCho) {
				fileChooser.setCurrentDirectory(new File("."));
				int result = fileChooser.showOpenDialog();
				if (result == JFileChooser.APPROVE_OPTION) {
					sourcefile = fileChooser.getSelectedFile();
					filename.setText(sourcefile.getPath());
					filesize.setText(sourcefile.length());
				}
				return;
			}
			if (e.getSource() == btTar) {
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog();
				if (result == JFileChooser.APPROVE_OPTION) {
					targetpath = fileChooser.getSelectedFile();
					tarPath.setText(targetpath.getPath());
				}
				return;
			}
			if (e.getSource() == btCut) {
				if (targetpath != NULL && sourcefile != NULL) {
					//执行分割
				}
			}	
		}
	}
}

class MergePanel extends JPanel /*implements ActionListener*/ {
	JTextField filenum = new JTextField(),
			   tarfile = new JTextField();

	JButton btMerge = new JButton("开始合并"),
			btCho = new JButton("选择文件"),
			btRemove = new JButton("移除"),
			btClear = new JButton("清空"),
			btTar = new JButton("目标文件");

	JTextArea area = new JTextArea(300, 200);

	JProgressBar progressbar = new JProgressBar();

	JFileChooser fileChooser = new JFileChooser();

	public MergePanel() {
		setLayout(null);
		JLabel numLabel = new JLabel("文件数量"),
			   collectLabel = new JLabel("要合并的文件:"),
			   tarLabel = new JLabel("目标文件:");
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);

		add(numLabel);
		add(collectLabel);
		add(tarLabel);
		add(filenum);
		add(tarfile);
		add(btMerge);
		add(btCho);
		add(btRemove);
		add(btClear);
		add(area);
		add(progressbar);
		add(btTar);
		numLabel.setBounds(100, 330, 100, 20);
		collectLabel.setBounds(100, 90, 100, 20);
		tarLabel.setBounds(100, 360, 100, 20);
		filenum.setBounds(210, 330, 100, 20);
		tarfile.setBounds(210, 360, 200, 20);
		btMerge.setBounds(100, 400, 100, 20);
		btCho.setBounds(470, 50, 100, 20);
		btRemove.setBounds(320, 330, 100, 20);
		btClear.setBounds(430, 330, 100, 20);
		area.setBounds(210, 120, 300, 200);
		progressbar.setBounds(210, 400, 300, 20);
		btTar.setBounds(420, 360, 50, 20);

		//this.addActionListener(new Listener());
	}

	private class Listener implements ActionListener {
		private File targetfile = NULL, sourcefiles[] = NULL;
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btCho) {
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setMultiSelectionEnabled(true);
				int result = fileChooser.showOpenDialog();
				if (result == JFileChooser.APPROVE_OPTION) {
					sourcefile = fileChooser.getSelectedFiles();
					
				}
				return;
			}
			if (e.getSource() == btTar) {
				fileChooser.setCurrentDirectory(new File("."));
				int result = fileChooser.showSaveDialog();
				if (result == JFileChooser.APPROVE_OPTION) {
					targetpath = fileChooser.getSelectedFile();
					tarPath.setText(targetpath.getPath());
				}
				return;
			}
			if (e.getSource() == btClear) {

			}
			if (e.getSource() == btRemove) {

			}
			if (e.getSource() == btMerge) {

			}
		}
	}
}