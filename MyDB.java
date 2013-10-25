import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import com.mongodb.*;

public class MyDB {
	public static void main (String[] args) {
		LogFrame logframe = new LogFrame();
		logframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logframe.setVisible(true);
	}
}

class LogFrame extends JFrame {
	public LogFrame() throws IOException{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;
		int frameW = 325;
		int frameH = 300;
		int x = screenW / 4;
		int y = screenH / 4;
		setSize(frameW, frameH);
		setLocationRelativeTo(null);
		setTitle("登录");
		LogPanel logpanel = new LogPanel();
		add(logpanel);
	}
}

class LogPanel extends JPanel {
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
		login.setBounds(200, 50, 250, 20);
		quit.setBounds();
		user.setBounds();
		pwd.setBounds();
		userLb.setBounds();
		pwdLb.setBounds();
		login.addActionListener(new Listener());
		quit.addActionListener(new Listener());
	}

	private JButton login = new JButton("登录");
	private JButton quit = new JButton("退出");
	private JTextField user = new JTextField();
	private JTextField pwd = new JTextField();
	private MongoClient mongoClient = new MongoClient("localhost", 27017);
	private	DB db = mongoClient.getDB("students");

	private class Listener implements ActionListener {
		public actionPerformed (ActionEvent e) {
			if (e.getSource() == login) {
				BasicDBObject query = new BasicDBObject("username", user.getText()).
					append("password", pwd.getText());
				DBCursor cursor = db.getCollection("admin").findOne(query);
				try {
				   if (cursor.hasNext()) {

				   } else {

				   }
				} finally {
				   cursor.close();
				}
			}
			if (e.getSource() == quit) {

			}
		}
	}
}

class Frame extends JFrame {
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
		Panel panel = new Panel();
		add(panel);
	}
}

class Panel extends JPanel {
	public Panel() {
		setLayout(null);
		add(btInsert);
		btInsert.setBounds(200, 50, 250, 20);
		btInsert.addActionListener(new Listener());

		DBCursor cursor = db.getCollection("student").find();
		try {
		   while(cursor.hasNext()) {
		       //cursor.next();
		   }
		} finally {
		   cursor.close();
		}
	}

	private JButton btInsert = new JButton("学生添加");
	private MongoClient mongoClient = new MongoClient("localhost", 27017);
	private	DB db = mongoClient.getDB("students");

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btInsert) {
				
			}
		}
	}

}

class InsertFrame extends JFrame {
	public InsertFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;
		int frameW = 400;
		int frameH = 500;
		int x = screenW / 4;
		int y = screenH / 4;
		setSize(frameW, frameH);
		setLocationRelativeTo(null);
		setTitle("插");
		InsertPanel insertPanel = new InsertPanel();
		add(insertPanel);
	}
}

class InsertPanel extends JPanel {
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

		btOk.setBounds();
		btCancle.setBounds();
		id.setBounds();
		name.setBounds();
		age.setBounds();
		year.setBounds();
		month.setBounds();
		day.setBounds();
		man.setBounds();
		woman.setBounds();
		college.setBounds();
		nameLb.setBounds();
		idLb.setBounds();
		sexLb.setBounds();
		ageLb.setBounds();
		birthLb.setBounds();
		collegeLb.setBounds();

		btOk.addActionListener(new Listener());
		btCancle.addActionListener(new Listener());
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
	private MongoClient mongoClient = new MongoClient("localhost", 27017);
	private	DB db = mongoClient.getDB("students");

	private class Listener implements ActionListener {
		public actionPerformed(ActionEvent e) {
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
		}
	}
}