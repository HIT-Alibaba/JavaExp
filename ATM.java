import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.Date;

public class ATM  {
	public static void main(String[] args) {
		Boolean isAdmin = false;
		Boolean isLogin = false;
		Scanner in = new Scanner(System.in);
		Scanner userInfo = new Scanner(new File("~/userInfo"));
		PrintWriter out = new PrintWriter("~/userInfo");
		System.out.print("请输入卡号：");
		String id = in.nextLine();
		System.out.print("请输入密码：");
		String pwd = in.nextInt();
		int money;
		String time, type;
		String line = userInfo.nextLine();
		
		while (line) {
			StringTokenizer tokens = new StringTokenizer(line,"#");
			if (tokens.nextToken() == id && tokens.nextToken() == pwd) {
				System.out.print("登陆成功！");
				money = Integer.parseInt(tokens.nextToken());
				type = tokens.nextToken();
				if (tokens.nextToken() == "admin") {
					System.out.print("欢迎，管理员~\n");
					isAdmin = true;
				}
				time = tokens.nextToken();
				showMenu(isAdmin);
				isLogin = true;
				break;
			}
			line = userInfo.nextLine();
		}
		if (!isLogin) System.out.print("用户卡号或密码有误！");
	}
	public static void showMenu(Boolean isAdmin) {
		if (isAdmin) {
			showAdminMenu();
		}
		else {
			User user = new User(id, pwd, money, type, time);
			showUserMenu();
		}
	}
	public static void showUserMenu() {
		Boolean isLoop = true;
		while (isLoop) {
			System.out.print("1. 查询余额\n");
			System.out.print("2. 存款\n");
			System.out.print("3. 取款\n");
			System.out.print("4. 明细\n");
			System.out.print("5. 修改密码\n");
			System.out.print("0. 退出系统\n");
			Scanner in = new Scanner(System.in);
			String choose = in.nextInt();
			switch (choose) {
				case 1:
					System.out.print(user.getMoney());
					break;
				case 2:
					System.out.print("存多少？");
					int num = in.nextInt();
					user.save(num);
					break;
				case 3:
					System.out.print("取多少?");
					int num = in.nextInt();
					user.pick(num);
					break;
				case 4:
					User.getInfo(user.getId());
				case 5:
					System.out.print("旧密码:");
					String opwd = in.nextLine();
					if (opwd == pwd) {
						String npwd = in.nextLine();
						if (npwd.length >= 6 && npwd % 111111 != 0)
						user.chgPwd(npwd);
					}
					break;
				case 0:
					isLoop = false;
					break;
			}
		}
	}
	public static showAdminMenu() {
		Boolean isLoop = true;
		while (isLoop) {
			System.out.print("1. 所有用户信息\n");
			System.out.print("2. 创建新号\n");
			System.out.print("3. ATM余额\n");
			System.out.print("4. 修改密码\n");
			System.out.print("0. 退出系统\n");
			Scanner in = new Scanner(System.in);
			String choose = in.nextInt();
			switch (choose) {
				case 1:
					Scanner allInfo = new Scanner(new File("~/userInfo"));
					String line = allInfo.nextLine();
					while (line) {
						System.out.print(line);
						line = allInfo.nextLine();
					}
					break;
				case 2:
					Date now = new Date(); 
					System.out.print("");
					String id = in.nextLine();
					String pwd = in.nextLine();
					String type = in.nextLine();
					DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      				String time = stamp.format(now);
					User newUser = new User(id, pwd, 10000, type, time);
					User.saveToATM(10000);
					break;
				case 3:
					System.out.print("");
					User.getAllMoney();
					break;
				case 4:
					System.out.print("旧密码:");
					String opwd = in.nextLine();
					if (opwd == pwd)
						String npwd = in.nextLine();
						if (npwd.length >= 6 && npwd % 111111 != 0)
						user.chgPwd(npwd);
					break;
				case 0:
					isLoop = false;
					break;
			}
		}
	}
}

public class User {
	public User(String id, String pwd, int money, String type, String time) {
		this.id = id;
		this.pwd = pwd;
		this.money = money;
		this.type = type;
		this.time = time;
	}
	public int getMoney() {
		return this.money;
	}
	public void save(int money) {
		this.money += money;
		allMoney += money;
	}
	public void pick(int money) {
		this.money -= money;
		allMoney -= money;
	}
	public void chgPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getId() {
		return this.id;
	}
	public static void getInfo(String id) {
		Record record = new Record(id);
		record.out();
	}
	public static int getAllMoney() {
		return allMoney;
	}
	public static void saveToATM(int money) {
		allMoney += money;
	}
	private String id,
				   pwd,
				   type,
				   time;
	private int money;
	private static int allMoney = 0;
}

public class Record {
	public Record(String id) {
		this.id = id;
	}
	public void out() {
		Scanner info = new Scanner(new File("~/record"));
		String line = info.nextLine();
		while (line) {
			StringTokenizer tokens = new StringTokenizer(line,"#");
			if (id == tokens.nextToken()) {
				System.out.print(line);
			}
			line = info.nextLine();
		}
	}
	private String id;
}