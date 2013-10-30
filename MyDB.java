import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class MyDB {
	public static void main (String[] args) throws IOException {
		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class Frame extends JFrame {
	public Frame() throws IOException{
		pframe = this;
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;
		int frameW = 650;
		int frameH = 600;
		int x = screenW / 4;
		int y = screenH / 4;
		setSize(frameW, frameH);
		setLocationRelativeTo(null);
		setTitle("学生信息数据库");
		JDialog logdlg = new JDialog(this, "登录", true);
		logdlg.add(new LogPanel());
		Panel panel = new Panel();
		add(panel);
	}
	Frame pframe = null;
  class Panel extends JPanel implements ActionListener {
	public Panel() {
		setLayout(null);
		add(btInsert);
		btInsert.setBounds(10, 10, 40, 30);
		btInsert.addActionListener(this);
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("students");
		} catch (Exception err) {}
		DBCursor cursor = db.getCollection("student").find();
		try {
		   while(cursor.hasNext()) {
		   	   DBObject current = cursor.next();
		       dataList.append(current.get("id") + " ");
		       dataList.append(current.get("name") + " ");
		       dataList.append(current.get("sex") + " ");
		       dataList.append(current.get("age") + " ");
		       dataList.append(current.get("birthday") + " ");
		       dataList.append(current.get("college") + " ");
		       dataList.append("\n");
		   }
		} finally {
		   cursor.close();
		}
	}
	private JButton btInsert = new JButton("学生添加");
	private JTextArea dataList = new JTextArea(400, 300);
	private MongoClient mongoClient;
	private	DB db;

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btInsert) {
				JDialog insertdlg = new JDialog(pframe, "插", true);
				insertdlg.add(new InsertPanel());
			}
		}

  }
}



class LogPanel extends JPanel implements ActionListener {
	public LogPanel() {
		JLabel userLb = new JLabel("用户名"),
			   pwdLb = new JLabel("密码");
		setLayout(null);
		add(login);
		add(quit);
		add(user);
		add(pwd);
		add(userLb);
		add(pwdLb);
		login.setBounds(10, 100, 40, 30);
		quit.setBounds(50, 100, 40, 30);
		user.setBounds(60, 30, 40, 30);
		pwd.setBounds(60, 70, 40, 30);
		userLb.setBounds(10, 30, 40, 30);
		pwdLb.setBounds(10, 70, 40, 30);
		login.addActionListener(this);
		quit.addActionListener(this);
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("students");
		} catch (Exception err) {}
	}

	private JButton login = new JButton("登录");
	private JButton quit = new JButton("退出");
	private JTextField user = new JTextField();
	private JTextField pwd = new JTextField();
	private MongoClient mongoClient;
	private	DB db;

		public void actionPerformed (ActionEvent e) {
			if (e.getSource() == login) {
				BasicDBObject query = new BasicDBObject("username", user.getText()).
					append("password", pwd.getText());
				DBCursor cursor = db.getCollection("admin").find(query);
				try {
				   if (cursor.hasNext()) {
				   		setVisible(false);
				   } else {
				   		user.setText("");
				   		pwd.setText("");
				   }
				   
				} finally {
				   cursor.close();
				}
			}
			if (e.getSource() == quit) {
				System.exit(0);
			}
		}
}

class InsertPanel extends JPanel implements ActionListener {
	public InsertPanel() {
		setLayout(null);
		JLabel nameLb = new JLabel("姓名"),
			   idLb = new JLabel("学号"),
			   sexLb = new JLabel("性别"),
			   ageLb = new JLabel("年龄"),
			   birthLb = new JLabel("生日"),
			   collegeLb = new JLabel("学院");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(man);
		buttonGroup.add(woman);
		add(btOk);
		add(btCancle);
		add(id);
		add(name);
		add(age);
		add(year);
		add(month);
		add(day);
		add(man);
		add(woman);
		add(college);
		add(nameLb);
		add(idLb);
		add(sexLb);
		add(ageLb);
		add(birthLb);
		add(collegeLb);

		btOk.setBounds(10, 220, 40, 30);
		btCancle.setBounds(60, 220, 40, 30);
		id.setBounds(60, 60, 40, 30);
		name.setBounds(60, 10, 40, 30);
		age.setBounds(60, 120, 40, 30);
		year.setBounds(60, 150, 40, 30);
		month.setBounds(100, 150, 40, 30);
		day.setBounds(140, 150, 40, 30);
		man.setBounds(60, 90, 40, 30);
		woman.setBounds(100, 90, 40, 30);
		college.setBounds(60, 180, 40, 30);
		nameLb.setBounds(10, 10, 40, 30);
		idLb.setBounds(10, 60, 40, 30);
		sexLb.setBounds(10, 90, 40, 30);
		ageLb.setBounds(10, 120, 40, 30);
		birthLb.setBounds(10, 150, 40, 30);
		collegeLb.setBounds(10, 180, 40, 30);
		btOk.addActionListener(this);
		btCancle.addActionListener(this);
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("students");
		} catch (Exception err) {}
	}
	private JButton btOk = new JButton("确认");
	private JButton btCancle = new JButton("取消");
	private JTextField id = new JTextField();
	private JTextField name = new JTextField();
	private JTextField age = new JTextField();
	private JTextField year = new JTextField();
	private JTextField month = new JTextField();
	private JTextField day = new JTextField();
	private JTextField college = new JTextField();
	private JRadioButton man = new JRadioButton("m", true);
	private JRadioButton woman = new JRadioButton("f");
	private MongoClient mongoClient;
	private	DB db;
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btOk) {
				String sex;
				if (man.isSelected()) {
					sex = "man";
				} else {
					sex = "woman";
				}
				String birthday = year.getText() + "-" + month.getText() + "-" + day.getText();
				DBCollection coll = db.getCollection("student");
				BasicDBObject doc = new BasicDBObject("id", id.getText()).
                    append("name", name.getText()).
                    append("sex", sex).
                    append("age", age.getText()).
                    append("birthday", birthday).
                    append("college", college.getText());

				coll.insert(doc);
			}
			if (e.getSource() == btCancle) {

			}
			setVisible(false);
		}
	
}