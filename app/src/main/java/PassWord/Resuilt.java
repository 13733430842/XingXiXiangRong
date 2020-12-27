package PassWord;


import java.util.ArrayList;

public class Resuilt {
	private String pwd;
	private int n;
	public Resuilt(int n){
		this.n=n;
	}

	public void setpwd(String pwd) {
		this.pwd=pwd;
	}
	private ArrayList<String> strArrayList(){
		String b=pwd;
		ArrayList<String> list=new ArrayList<String>();
		String[] c=b.split(" ");
		for (int i = 0; i < c.length; i++) {
			String d=c[i].replace(" ", "");
			list.add(d);
		}
		return list;
	}
	private ArrayList<Character> characters(){
		ArrayList<Integer> integers=new ArrayList<Integer>();
		ArrayList<Character> characters=new ArrayList<Character>();
		for (int i = 0; i < strArrayList().size(); i++) {
			int a=Integer.valueOf(strArrayList().get(i));
			if (n==1) {
				integers.add(a-((i+(2*i))*(i+(2*i+1))));		//解密1
			}else if (n==2) {
				integers.add(a-(((i)+strArrayList().size())*(i+1)));	//解密2
			}else if (n==3) {
				integers.add(a-((strArrayList().size()-(i+1))*(i+1)));	//解密2.1
			}
		}
		for (int j = 0; j < integers.size(); j++) {
			int b=integers.get(j);
			char c=(char)b;
			characters.add(c);
		}
		return characters;
	}
	public String getresuilt(){
		String string="";
		for (int i = 0; i < characters().size(); i++) {
			string+=characters().get(i);
		}		
		return string;
	}
}
