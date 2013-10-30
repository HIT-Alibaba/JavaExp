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
		JScrollPane ssay = new JScrollPane(say);
		JScrollPane slog = new JScrollPane(log);
		add(nameLb);
		add(name);
		add(ssay);
		add(slog);
		add(sendMsg);
		add(sendFile);
		add(accept);
		nameLb.setBounds(10, 410, 50, 20);
		name.setBounds(60, 410, 100, 20);
		ssay.setBounds(10, 430, 490, 200);
		slog.setBounds(10, 10, 490, 400);
		sendMsg.setBounds(610, 630, 60, 20);
		sendFile.setBounds(550, 630, 60, 20);
		accept.setBounds(480, 630, 60, 20);
		try {
			Scanner confile = new Scanner(new File("/home/soufii/JavaExp/config"));
			address = confile.nextLine();
			localTextPort = Integer.parseInt(confile.nextLine());
			localFilePort = Integer.parseInt(confile.nextLine());
			targetTextPort = Integer.parseInt(confile.nextLine());
			targetFilePort = Integer.parseInt(confile.nextLine());
		} catch (IOException er) {}
		sendMsg.addActionListener(this);
		sendFile.addActionListener(this);
		accept.addActionListener(this);
		try {
			ss = new ServerSocket(localFilePort);
		} catch (IOException err) {}
		try {
			dataSocket = new DatagramSocket(localTextPort);;
		} catch (IOException e) {}
		Runnable get = new ReviceMsg();
		Thread t = new Thread(get);
		t.start();
	}
	private DatagramSocket dataSocket = null;
	private ServerSocket ss = null;
	private String address = "";
	private int	targetTextPort,
				localTextPort,
				targetFilePort,
				localFilePort;
	private JTextField name = new JTextField();
	private JTextArea say = new JTextArea(500, 400);
	private JTextArea log = new JTextArea(500, 100);
	private JButton sendMsg = new JButton("发送消息"),
					sendFile = new JButton("传送文件"),
					accept = new JButton("接收文件");
	public Frame pt;
	public void actionPerformed(ActionEvent e) {
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
    	private DatagramPacket dataPacket;
    	public void run() {
			try {
	            byte[] sendDataByte = new byte[1024];
	            String sendStr = name.getText() + "说:" + say.getText() + '\n';
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
				try {
					Socket socket = new Socket(address, targetFilePort);
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
			        log.append("文件传送完毕！\n");
		    	} catch(Exception err) {}
		}
	}

	private class AcceptThread implements Runnable {
		JFileChooser fileChooser = new JFileChooser();
		File targetfile = null;
		String path;
		public void run() {
			fileChooser.setCurrentDirectory(new File("."));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				targetfile = fileChooser.getSelectedFile();
				path = targetfile.getPath();
			}
	        try {
	        	Socket socket = ss.accept();
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
	            log.append("文件接收成功！\n");
	        } catch(Exception err) {}
	    }
	}
}