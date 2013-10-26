import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class MyQQ {
	public static void main (String[] args) throws IOException {
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
		Panel panel = new Panel(this);
		add(panel);
	}
}

class Panel extends JPanel implements ActionListener {
	public Panel(Frame parent) {
		setLayout(null);
		pt = parent;
		JLabel nameLb = new JLabel("昵称");
		add(nameLb);
		add(name);
		add(say);
		add(log);
		add(config);
		add(sendMsg);
		add(sendFile);
		add(accept);
		nameLb.setBounds();
		name.setBounds();
		say.setBounds();
		log.setBounds();
		config.setBounds();
		sendMsg.setBounds();
		sendFile.setBounds();
		accept.setBounds();
		config.addActionListener(this);
		sendMsg.addActionListener(this);
		sendFile.addActionListener(this);
		accept.addActionListener(this);
		Runnable get = new ReviceMsg();
		Thread t = new Thread(get);
		t.start();
	}
	private ServerSocket ss = new ServerSocket(3000);
	private String address = "";
	private int	targetTextPort,
				localTextPort,
				targetFilePort,
				localFilePort;
	private JTextField name = new JTextField();
	private JTextArea say = new JTextArea(500, 400);
	private JTextArea log = new JTextArea(500, 100);
	private JButton config = new JButton("系统设置"),
					sendMsg = new JButton("发送消息"),
					sendFile = new JButton("传送文件"),
					accept = new JButton("接收文件");
	public Frame pt;
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == config) {
			ConfigDialog set = new ConfigDialog(pt);
			address = set.addressD;
			targetTextPort = Integer.parseInt(set.targetTextPortD);
			localTextPort = Integer.parseInt(set.localTextPortD);
			targetFilePort = Integer.parseInt(set.targetFilePortD);
			localFilePort = Integer.parseInt(set.localFilePortD);
		}
		if (e.getSource() == sendMsg) {
			String msg = name.getText() + "说:" + say.getText() + "\n";
			log.append(msg);
			Runnable st = new SendMsg();
			Thread t = new Thread(st);
			t.start();
		} 
		if (e.getSource() == sendFile) {
			Runnable sf = new SendThread();
			Thread t = new Thread(sf);
			t.start();
		}
		if (e.getSource() == accept) {
			Runnable af = new AcceptThread();
			Thread t1 = new Thread(af);
			t1.start();
		}
	}

	private class SendMsg implements Runnable {
		private DatagramSocket dataSocket;
    	private DatagramPacket dataPacket;
    	public void run() {
			try {
	            dataSocket = new DatagramSocket(localTextPort);
	            byte[] sendDataByte = new byte[1024];
	            String sendStr = say.getText();
	            sendDataByte = sendStr.getBytes();
	            dataPacket = new DatagramPacket(sendDataByte, 
	            	sendDataByte.length, 
	            	InetAddress.getByName(address), 
	            	targetTextPort);
	            dataSocket.send(dataPacket);
	        } catch (Exception err) {}
    	}
	}

	private class ReviceMsg implements Runnable {
		public void run() {
			try {
	            DatagramSocket dataSocket = new DatagramSocket(localTextPort);
	            byte[] receiveByte = new byte[1024];
	            DatagramPacket dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
	            String receiveStr;
	            while (true) {
	                dataSocket.receive(dataPacket);
	                receiveStr = new String(receiveByte, 0, dataPacket.getLength());
	                log.append(receiveStr);
	            }
	        } catch (Exception e) {}
	    }
	}

	private class SendThread implements Runnable {
		JFileChooser fileChooser = new JFileChooser();
		File sourcefile = null;
		String path;
		public void run() {
			fileChooser.setCurrentDirectory(new File("."));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					sourcefile = fileChooser.getSelectedFile();
					path = sourcefile.getPath();
				}
				Socket socket = new Socket(address, targetFilePort);
				try {
				    FileInputStream fs = new FileInputStream(path);
				    byte[] bytes = new byte[1024];
				    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			        int length = 0;
			        while ((length = fs.read(bytes)) != -1) {
			            bos.write(bytes, 0, length);
			        }
			        bos.close();
			        fs.close();
			        socket.close();
			        log.append("文件传送完毕！");
		    	} catch(Exception err) {}
		}
	}

	private class AcceptThread implements Runnable {
		JFileChooser fileChooser = new JFileChooser();
		File targetfile = null;
		String path;
		public void run() {
			fileChooser.setCurrentDirectory(new File("."));
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				targetfile = fileChooser.getSelectedFile();
				path = targetfile.getPath();
			}
			Socket socket = ss.accept();
	        try {
	        	BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
	            byte[] bytes = new byte[1024];
	            FileOutputStream fos = new FileOutputStream(path + "/recivedfile");
	            BufferedOutputStream bos = new BufferedOutputStream(fos);
	            int len = 0;
	            while ((len = bis.read(bytes)) != -1) {
	                bos.write(bytes, 0, len);
	            }
	            bos.close();
	            fos.close();
	            bis.close();
	            socket.close();
	            log.append("文件接收成功！");
	        } catch(Exception err) {}
	    }
	}
}

class ConfigDialog extends JDialog {
	public ConfigDialog(Frame parent) {
		super(parent, "设置", true);
		add(new ConfigPanel());
		setVisible(true);
	}
	public String addressD,
					targetTextPortD,
					localTextPortD,
					targetFilePortD,
					localFilePortD;

 private class ConfigPanel extends JPanel implements ActionListener {
	public void ConfigPanel() {
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
		btOk.addActionListener(this);
	}
	public JTextField address = new JTextField(),
						targetTextPort = new JTextField(),
						localTextPort = new JTextField(),
						targetFilePort = new JTextField(),
						localFilePort = new JTextField();
	private JButton btOk = new JButton("返回");
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btOk) {
			addressD = address.getText();
			targetTextPortD = targetTextPort.getText();
			localTextPortD = localTextPort.getText();
			targetFilePortD = targetFilePort.getText();
			localFilePortD = localFilePort.getText();
			setVisible(false);
		}
	}
 }
}