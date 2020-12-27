package PassWord;

/**
 * 随机数生成
 * 可以生成需求长度的随机数
 */
public class math {
	private int in;

	public math(int in) {
		this.in = in;
	}

	public String radom() {
		String r="";
		int n=0;
		int a;
		for (int i = 0; i < in; i++) {
			n=(int) (Math.random()*10);
			if (n!=0) {//去除数字0
				a=n;
			}else {
				a=9;
			}
			r += String.valueOf(a);
		}
		return r;
	}

}
