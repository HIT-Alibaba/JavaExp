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
		Logdialog logdlg = new Logdialog(null, "登录", true);
		logdlg.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		logdlg.setVisible(true);
	}
}

class Logdialog extends JDialog implements ActionListener {
	public Logdialog (JFrame parent, String title, Boolean bool) {
		super(parent, title, bool);
		setLayout(null);
		setSize(300, 200);
		setLocationRelativeTo(null);
		JLabel userLb = new JLabel("用户名"),
			   pwdLb = new JLabel("密码");
		add(login);
		add(quit);
		add(user);
		add(pwd);
		add(userLb);
		add(pwdLb);
		login.setBounds(10, 100, 40, 30);
		quit.setBounds(60, 100, 40, 30);
		user.setBounds(60, 10, 40, 30);
		pwd.setBounds(60, 50, 40, 30);
		userLb.setBounds(10, 10, 40, 30);
		pwdLb.setBounds(10, 50, 40, 30);
		login.addActionListener(this);
		quit.addActionListener(this);
		try {
			mongoClient = new MongoClient();
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
			   		Frame mainFrame = new Frame();
			   		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.setVisible(true);
			   		this.setVisible(false);
			   	} else {
			   		user.setText("");
			   		pwd.setText("");
			   	}
			} catch (IOException errr) {
			cursor.close();
			}
			if (e.getSource() == quit) {
				System.exit(0);
			}
		}
	}
}


class Frame extends JFrame implements ActionListener {
	public Frame() throws IOException{
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
		add(btInsert);
		add(dataList);
		add(refresh);
		setLayout(null);
		btInsert.setBounds(10, 10, 40, 30);
		refresh.setBounds(60, 10, 40, 30);
		dataList.setBounds(10, 100, 600, 300);
		btInsert.addActionListener(this);
		refresh.addActionListener(this);
		dataList.append("学号\t姓名\t性别\t年龄\t生日\t学院\n");
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("students");
		} catch (Exception err) {}
		DBCursor cursor = db.getCollection("student").find();
		try {
		   while(cursor.hasNext()) {
		   	   DBObject current = cursor.next();
		       dataList.append(current.get("id") + "\t");
		       dataList.append(current.get("name") + "\t");
		       dataList.append(current.get("sex") + "\t");
		       dataList.append(current.get("age") + "\t");
		       dataList.append(current.get("birthday") + "\t");
		       dataList.append(current.get("college") + "");
		       dataList.append("\n");
		   }
		} finally {
		   cursor.close();
		}
	}
	private JButton btInsert = new JButton("学生添加"),
					refresh = new JButton("刷新列表");
	private JTextArea dataList = new JTextArea(600, 300);
	private MongoClient mongoClient;
	private	DB db;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btInsert) {
			try {
				InsertFrame insertFrame = new InsertFrame();
				insertFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				insertFrame.setVisible(true);
			} catch (IOException er) {}
		}
		if (e.getSource() == refresh) {
			dataList.setText("学号\t姓名\t性别\t年龄\t生日\t学院\n");
			DBCursor cursor = db.getCollection("student").find();
			try {
			   while(cursor.hasNext()) {
			   	   DBObject current = cursor.next();
			       dataList.append(current.get("id") + "\t");
			       dataList.append(current.get("name") + "\t");
			       dataList.append(current.get("sex") + "\t");
			       dataList.append(current.get("age") + "\t");
			       dataList.append(current.get("birthday") + "\t");
			       dataList.append(current.get("college") + "");
			       dataList.append("\n");
			   }
			} finally {
			   cursor.close();
			}
		}
	}
}

class InsertFrame extends JFrame implements ActionListener {
	public InsertFrame() throws IOException{
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
		setTitle("插入数据");
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
	private JRadioButton man = new JRadioButton("男", true);
	private JRadioButton woman = new JRadioButton("女");
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
			this.setVisible(false);
		}
		if (e.getSource() == btCancle) {
			this.setVisible(false);
		}
	}
}