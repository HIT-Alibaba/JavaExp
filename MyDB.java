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
		btInsert.setBounds(200, 50, 250, 20);
		btInsert.addActionListener(this);

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
	private MongoClient mongoClient = new MongoClient("localhost", 27017);
	private	DB db = mongoClient.getDB("students");

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
		login.setBounds(200, 50, 250, 20);
		quit.setBounds();
		user.setBounds();
		pwd.setBounds();
		userLb.setBounds();
		pwdLb.setBounds();
		login.addActionListener(this);
		quit.addActionListener(this);
	}

	private JButton login = new JButton("登录");
	private JButton quit = new JButton("退出");
	private JTextField user = new JTextField();
	private JTextField pwd = new JTextField();
	private MongoClient mongoClient = new MongoClient("localhost", 27017);
	private	DB db = mongoClient.getDB("students");

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

		btOk.addActionListener(this);
		btCancle.addActionListener(this);
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