package Message;

public class math {
	
	public String radom() {
		String r="";
		int n=0;
		for (int i = 0; i < 6; i++) {
			n=(int) (Math.random()*10);
			r+=String.valueOf(n);
		}
		return r;
	}

}
