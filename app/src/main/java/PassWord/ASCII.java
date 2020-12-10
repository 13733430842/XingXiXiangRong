package PassWord;

import java.util.ArrayList;

public class ASCII {
private String pwd;
public void setpwd(String pwd){
	this.pwd=pwd;
}
private ArrayList<Character> characters(){
	ArrayList<Character> characters=new ArrayList<Character>();
	for (int i = 0; i < pwd.length(); i++) {
		characters.add(pwd.charAt(i));
	}
	return characters;
}
private ArrayList<Integer> integers(){
	ArrayList<Integer> integers=new ArrayList<Integer>();
	for (int i = 0; i < characters().size(); i++) {
		integers.add(Integer.valueOf(characters().get(i)));
	}
	return integers;
}
public String getresuilt(int n){

	String str="";
	for (int i = 0; i <integers().size(); i++) {
		if (n==1) {
			str+=String.valueOf(integers().get(i)+((i+2)*(i+1)))+" ";	//加密1 x+((i+1)*(i+2))
		}else if (n==2) {
			str+=String.valueOf(integers().get(i)+((i+integers().size())*(i+1)))+" ";		//加密2
		}else if (n==3) {
			str+=String.valueOf(integers().get(i)+((integers().size()-i)*(i+1)))+" ";		//加密2.1
		}

	}
	return str;
}


}
