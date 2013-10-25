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
		JDialog config = new JDialog(this, "设置", true);
		config.add(new configPanel());
		add(config);
		Panel panel = new Panel();
		add(panel);
	}
}

class Panel extends JPanel implements ActionListener {
	public Panel() {
		setLayout(null);

	}
	private JTextField
	private void actionPerformed(ActionEvent e) {

	}
}

class configPanel extends JPanel implements ActionListener {
	public configPanel() {
		setLayout(null);
		JLabel addressLb = new JLabel("通信地址"),
			   targetTextPortLb = new JLabel("目标消息接收端口"),
			   localTextPortLb = new JLabel("本地消息接收端口"),
			   targetFilePortLb = new JLabel("目标文件接收端口"),
			   localFilePortLb = new JLabel("本地文件接收端口");
		add(addressLb);
		add(targetTextPortLb);
		add(localTextPortLb);
		add(targetFilePortLb);
		add(localFilePortLb);
		add(btOk);
		add(address);
		add(targetTextPort);
		add(localTextPort);
		add(targetFilePort);
		add(localFilePort);
		btOk.setBounds();
		addressLb.setBounds();
		targetTextPortLb.setBounds();
		localTextPortLb.setBounds();
		targetFilePortLb.setBounds();
		localFilePortLb.setBounds();
		address.setBounds();
		targetTextPort.setBounds();
		localTextPort.setBounds();
		targetFilePort.setBounds();
		localFilePort.setBounds();
	}
	public JTextField address = new JTextField();
						targetTextPort = new JTextField();
						localTextPort = new JTextField();
						targetFilePort = new JTextField();
						localFilePort = new JTextField();
	private JButton btOk = new JButton("返回");
	private void actionPerformed(ActionEvent e) {
		if (e.getSource() == btOk) {

		}
	}
}