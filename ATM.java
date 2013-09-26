import java.io.*;
import java.util.*;
import java.text.*;

public class ATM  {
	public static void main(String[] args)  throws IOException { 
		Scanner infos = new Scanner(new File("/home/soufii/JavaExp/userInfo"));
		String rec = infos.nextLine();
		int i = 0, num = 0;
		while (infos.hasNextLine()) {
			i++;
			infos.nextLine();
		}
		String[] userArr = new String[i + 1];
		i = 0;
		Scanner infos1 = new Scanner(new File("/home/soufii/JavaExp/userInfo"));
		while (infos1.hasNextLine()) {
			userArr[i] = infos1.nextLine();
			i++;
		}
		Boolean isAdmin = false;
		Boolean isLogin = false;
		Scanner in = new Scanner(System.in);
		Scanner userInfo = new Scanner(new File("/home/soufii/JavaExp/userInfo"));
		System.out.print("请输入卡号：");
		String id = in.nextLine();
		System.out.print("请输入密码：");
		String pwd = in.nextLine();
		int money;
		String time, type, line;
		while (userInfo.hasNextLine()) {
			line = userInfo.nextLine();
			StringTokenizer tokens = new StringTokenizer(line,"#");
			String inpid = tokens.nextToken();
			String inppwd = tokens.nextToken();
			if (inpid .equals( id) && inppwd .equals( pwd)) {
				System.out.print("登陆成功！");
				money = Integer.parseInt(tokens.nextToken());
				type = tokens.nextToken();
				for (i = 0; i < userArr.length; i++) {
					if (userArr[i] .equals(line)) 
						num = i;
				}
				if (type.equals( "admin")) {
					System.out.print("欢迎，管理员\n");
					isAdmin = true;
				}
				time = tokens.nextToken();

				showMenu(isAdmin, id, pwd, money, type, time, userArr, num);
				isLogin = true;
				break;
			}
		}
		if (!isLogin) System.out.print("用户卡号或密码有误！");
	}
	public static void showMenu(Boolean isAdmin, String id, String pwd, int money, String type, String time, String[] userArr, int num) throws IOException {
		if (isAdmin) {
			showAdminMenu(userArr, num, id, pwd, time);
		}
		else {
			User user = new User(id, pwd, money, type, time);
			showUserMenu(user, userArr, num);
		}
	}
	public static void showUserMenu(User user, String[] userArr, int num)  throws IOException{
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
					System.out.print('\n');
					break;
				case 2:
					
					while(true) {
						System.out.print("存多少？");
					n = in.nextInt();
						if (n < 2000 && n % 100 == 0){
							user.save(n);
							nmoney = Integer.toString(user.getMoney());
							userArr[num] = user.id + "#" + user.pwd + "#" + Integer.toString(user.money) + "#" + "hq" + "#" + user.time;
							break;
						} else {
							System.out.print("格式错误!");
						}	
					}
					break;
				case 3:
					
					while(true) {
						System.out.print("取多少?");
					n= in.nextInt();
						if (n < 2000 && n % 100 == 0){
							user.pick(n);
							nmoney = Integer.toString(user.getMoney());
							userArr[num] = user.id + "#" + user.pwd + "#" + Integer.toString(user.money) + "#" + "hq" + "#" + user.time;
							break;
						} else {
							System.out.print("格式错误!");
						}	
					}
					
					break;
				case 4:
					User.getInfo(user.getId());
					break;
				case 5:
					System.out.print("旧密码:");
					Scanner inin = new Scanner(System.in);
					String opwd = inin.nextLine();
					if (opwd.equals( user.pwd)) {
						
						while (true) {
							System.out.print("新密码:");
						String npwd = inin.nextLine();
							if (npwd.length() >= 6 && Integer.parseInt(npwd) % 111111 != 0) {
								user.chgPwd(npwd);
								userArr[num] = user.id + "#" + npwd + "#" + user.money + '#' + "hq" + "#" + user.time;
								break;
							} else {
								System.out.print("格式错误！");
							}
						}
					}

					break;
				case 0:
					isLoop = false;
					FileWriter writer = new FileWriter("/home/soufii/JavaExp/userInfo");
					for (int i = 0; i < userArr.length; i++) {
						if (userArr[i].length() != 0) {
							writer.write(userArr[i] + "\n");
							writer.flush();
						}
					}
					
					writer.close();
					break;
			}
		}
	}
	public static void showAdminMenu(String[] userArr, int num, String id, String pwd, String time) throws IOException {
		String line;
		int money = 0;
		Scanner userInfo = new Scanner(new File("/home/soufii/JavaExp/userInfo"));
		while (userInfo.hasNextLine()) {
			line = userInfo.nextLine();
			StringTokenizer tokens = new StringTokenizer(line,"#");
			tokens.nextToken();
			tokens.nextToken();
			money += Integer.parseInt(tokens.nextToken());
		}
		User.allMoney = money;
		Boolean isLoop = true;
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
					Scanner allInfo = new Scanner(new File("/home/soufii/JavaExp/userInfo"));
					String aline;
					while (allInfo.hasNextLine()) {
						aline = allInfo.nextLine();
						System.out.print(aline + "\n");
					}
					break;
				case 2:
					Date now = new Date(); 
					System.out.print("创建新号");
					Scanner inin = new Scanner(System.in);
					String nid = inin.nextLine();
					System.out.print("密码");
					npwd = inin.nextLine();
					DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      				String ntime = stamp.format(now);
					User newUser = new User(nid, npwd, 10000, "hq", ntime);
					String[] nuserArr = new String[userArr.length + 1];
					for (int i = 0; i < userArr.length; i++) {
						nuserArr[i] = userArr[i];
					}
					nuserArr[userArr.length] = nid + "#" + npwd + "#" + "10000" + "#" + "hq" + "#" + ntime;
					User.saveToATM(10000);
					FileWriter writer = new FileWriter("/home/soufii/JavaExp/userInfo");
					for (int i = 0; i < nuserArr.length; i++) {
						if (nuserArr[i].length() != 0) {
							writer.write(nuserArr[i] + "\n");
							writer.flush();
						}
					}
					writer.close();
					break;
				case 3:
					System.out.print(User.getAllMoney());
					System.out.print('\n');
					break;
				case 4:
					System.out.print("旧密码:");
					Scanner inin0 = new Scanner(System.in);
					String opwd = inin0.nextLine();
					if (opwd .equals( pwd)) {
						
						while(true) {
							System.out.print("新密码");
						npwd = inin0.nextLine();
							if (npwd.length() >= 6 && Integer.parseInt(npwd) % 111111 != 0) {
								userArr[num] = id + "#" + npwd + "#" + '0' + "#" + "admin" + "#" + time;
								break;
							} else {
								System.out.print("格式错误！");
							}
						}
					}
					
					break;
				case 0:
					isLoop = false;
					FileWriter writes = new FileWriter("/home/soufii/JavaExp/userInfo");
					for (int i = 0; i < userArr.length; i++) {
						if (userArr[i].length() != 0) {
							writes.write(userArr[i] + "\n");
							writes.flush();
						}
					}
					writes.close();
					break;
			}
		}
	}
}

class User {
	public User(String id, String pwd, int money, String type, String time) throws IOException {
		this.id = id;
		this.pwd = pwd;
		this.money = money;
		this.type = type;
		this.time = time;
	}
	public int getMoney() throws IOException {
		return this.money;
	}
	public void save(int money) throws IOException {
		this.money += money;
		allMoney += money;
		Record record = new Record(id);
		Date now = new Date();
		DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      	String time = stamp.format(now);
		record.save(id, money, time);
	}
	public void pick(int money) throws IOException {
		this.money -= money;
		allMoney -= money;
		Record record = new Record(id);
		Date now = new Date();
		DateFormat stamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); 
      	String time = stamp.format(now);
		record.save(id, -money, time);
	}
	public void chgPwd(String pwd) throws IOException {
		this.pwd = pwd;
	}
	public String getId() {
		return this.id;
	}
	public static void getInfo(String id) throws IOException {
		Record record = new Record(id);
		record.out();
	}
	public static int getAllMoney()throws IOException {
		return allMoney;
	}
	public static void saveToATM(int money) throws IOException{
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
	public Record(String id) throws IOException{
		this.id = id;
	}
	public void out() throws IOException {
		Scanner info = new Scanner(new File("/home/soufii/JavaExp/record"));
		String line;
		while (info.hasNextLine()) {
			line = info.nextLine();
			StringTokenizer tokens = new StringTokenizer(line,"#");
			if (id .equals( tokens.nextToken())) {
				System.out.print(line + '\n');
			}
		}
	}
	public void save(String id, int opt, String time) throws IOException {
		FileWriter writer = new FileWriter("/home/soufii/JavaExp/record", true);  
		String s = Integer.toString(opt); 
		writer.write(id + "#" + s + "#" + time + "\n");
		writer.flush();  
        writer.close(); 
	}
	public String id;
}