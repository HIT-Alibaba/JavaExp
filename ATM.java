import java.io.*;
import java.util.*;
import java.text.*;

public class ATM  {
	public static void main(String[] args) {
		Scanner infos = new Scanner(new File("/home/soufii/userInfo"));
		String rec = infos.nextLine();
		int i = 0, num = 0;
		while (infos.hasNextLine()) {
			i++;
			infos.nextLine();
		}
		String[] userArr = new String[i];
		i = 0;
		Scanner infos1 = new Scanner(new File("/home/soufii/userInfo"));
		rec = infos1.nextLine();
		while (infos1.hasNextLine()) {
			userArr[i] = infos1.nextLine();
			i++;
			infos1.nextLine();
		}
		Boolean isAdmin = false;
		Boolean isLogin = false;
		Scanner in = new Scanner(System.in);
		Scanner userInfo = new Scanner(new File("/home/soufii/userInfo"));
		System.out.print("请输入卡号：");
		String id = in.nextLine();
		System.out.print("请输入密码：");
		String pwd = in.nextLine();
		int money;
		String time, type;
		String line = userInfo.nextLine();
		
		while (userInfo.hasNextLine()) {
			StringTokenizer tokens = new StringTokenizer(line,"#");
			if (tokens.nextToken() == id && tokens.nextToken() == pwd) {
				System.out.print("登陆成功！");
				money = Integer.parseInt(tokens.nextToken());
				type = tokens.nextToken();
				for (i = 0; i < userArr.length; i++) {
					if (userArr[i] ==line) 
						num = i;
				}
				if (tokens.nextToken() == "admin") {
					System.out.print("欢迎，管理员~\n");
					isAdmin = true;
				}
				time = tokens.nextToken();
				showMenu(isAdmin, id, pwd, money, type, time, userArr, num);

				isLogin = true;
				break;
			}
			line = userInfo.nextLine();
		}
		if (!isLogin) System.out.print("用户卡号或密码有误！");
	}
	public static void showMenu(Boolean isAdmin, String id, String pwd, int money, String type, String time, String[] userArr, int num) {
		if (isAdmin) {
			showAdminMenu(userArr, num, id, pwd, time);
		}
		else {
			User user = new User(id, pwd, money, type, time);
			showUserMenu(user, userArr, num);
		}
	}
	public static void showUserMenu(User user, String[] userArr, int num) {
		Boolean isLoop = true;
		while (isLoop) {
			System.out.print("1. 查询余额\n");
			System.out.print("2. 存款\n");
			System.out.print("3. 取款\n");
			System.out.print("4. 明细\n");
			System.out.print("5. 修改密码\n");
			System.out.print("0. 退出系统\n");
			Scanner in = new Scanner(System.in);
			int choose = in.nextInt();
			int n;
			String nmoney = "";
			switch (choose) {
				case 1:
					System.out.print(user.getMoney());
					break;
				case 2:
					System.out.print("存多少？");
					n = in.nextInt();
					user.save(n);
					nmoney = Integer.toString(user.getMoney());
					userArr[num] = user.id + "#" + user.pwd + "#" + nmoney + "#" + "hq" + user.time + "\n";
					break;
				case 3:
					System.out.print("取多少?");
					n= in.nextInt();
					user.pick(n);
					nmoney = Integer.toString(user.getMoney());
					userArr[num] = user.id + "#" + user.pwd + "#" + nmoney + "#" + "hq" + user.time + "\n";
					break;
				case 4:
					User.getInfo(user.getId());
				case 5:
					System.out.print("旧密码:");
					String opwd = in.nextLine();
					if (opwd == user.pwd) {
						String npwd = in.nextLine();
						if (npwd.length() >= 6 && Integer.parseInt(npwd) % 111111 != 0) {
							user.chgPwd(npwd);
							userArr[num] = user.id + "#" + npwd + "#" + nmoney + '#' + "hq" + user.time + "\n";
						}
					}

					break;
				case 0:
					isLoop = false;
					FileWriter writer = new FileWriter("/home/soufii/userInfo");
					for (int i = 0; i < userArr.length; i++) {
						writer.write(userArr[i]);
						writer.flush();
					}
					
					writer.close();
					break;
			}

		}
	}
	public static void showAdminMenu(String[] userArr, int num, String id, String pwd, String time) {
		Scanner userInfo = new Scanner(new File("/home/soufii/userInfo"));
		String line = userInfo.nextLine();
		int money = 0;
		while (userInfo.hasNextLine()) {
			StringTokenizer tokens = new StringTokenizer(line,"#");
			tokens.nextToken();
			tokens.nextToken();
			money += Integer.parseInt(tokens.nextToken());
			line = userInfo.nextLine();
		}
		User.allMoney = money;
		Boolean isLoop = true;
		FileWriter writer = new FileWriter("/home/soufii/userInfo");
		while (isLoop) {
			System.out.print("1. 所有用户信息\n");
			System.out.print("2. 创建新号\n");
			System.out.print("3. ATM余额\n");
			System.out.print("4. 修改密码\n");
			System.out.print("0. 退出系统\n");
			Scanner in = new Scanner(System.in);
			int choose = in.nextInt();
			String npwd;
			switch (choose) {
				case 1:
					Scanner allInfo = new Scanner(new File("/home/soufii/userInfo"));
					String aline = allInfo.nextLine();
					while (allInfo.hasNextLine()) {
						System.out.print(aline);
						aline = allInfo.nextLine();
					}
					break;
				case 2:
					Date now = new Date(); 
					System.out.print("创建新号");
					String nid = in.nextLine();
					npwd = in.nextLine();
					DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      				String ntime = stamp.format(now);
					User newUser = new User(nid, npwd, 10000, "hq", ntime);
					String[] nuserArr = new String[userArr.length + 1];
					for (int i = 0; i < userArr.length; i++) {
						nuserArr[i] = userArr[i];
					}
					nuserArr[userArr.length] = nid + "#" + npwd + "#" + '0' + "#" + "hq" + ntime + "\n";
					User.saveToATM(10000);
					for (int i = 0; i < nuserArr.length; i++) {
						writer.write(nuserArr[i]);
						writer.flush();
					}
					break;
				case 3:
					System.out.print("");
					User.getAllMoney();
					break;
				case 4:
					System.out.print("旧密码:");
					String opwd = in.nextLine();
					if (opwd == pwd) {
						npwd = in.nextLine();
						if (npwd.length() >= 6 && Integer.parseInt(npwd) % 111111 != 0) {
							userArr[num] = id + "#" + npwd + "#" + '0' + "#" + "admin" + time + "\n";
						}
					}
					for (int i = 0; i < userArr.length; i++) {
						writer.write(userArr[i]);
						writer.flush();
					}
					
					break;
				case 0:
					isLoop = false;
					writer.close();
					break;
			}
		}
	}
}

class User {
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
		Record record = new Record(id);
		Date now = new Date();
		DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      	String time = stamp.format(now);
		record.save(id, money, time);
	}
	public void pick(int money) {
		this.money -= money;
		allMoney -= money;
		Record record = new Record(id);
		Date now = new Date();
		DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      	String time = stamp.format(now);
		record.save(id, -money, time);
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
	public String id,
				   pwd,
				   type,
				   time;
	public int money;
	public static int allMoney;
}

class Record {
	public Record(String id) {
		this.id = id;
	}
	public void out() {
		Scanner info = new Scanner(new File("/home/soufii/record"));
		String line = info.nextLine();
		while (info.hasNextLine()) {
			StringTokenizer tokens = new StringTokenizer(line,"#");
			if (id == tokens.nextToken()) {
				System.out.print(line);
			}
			line = info.nextLine();
		}
	}
	public void save(String id, int opt, String time) {
		FileWriter writer = new FileWriter("/home/soufii/record", true);  
		String s = Integer.toString(opt); 
		writer.write(id + "#" + s + "#" + time + "\n");
		writer.flush();  
        writer.close(); 
	}
	public String id;
}