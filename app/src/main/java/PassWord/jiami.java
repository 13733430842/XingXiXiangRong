package PassWord;

import java.util.ArrayList;

/**
 * 随机秘钥密码加密
 */
public class jiami {
    private String miyao,mingwen,miwen;
    private ArrayList<Integer> i_miyao;

    public jiami(String mingwen) {
        this.mingwen = mingwen;
        setMiyao();
    }
    public String getMiyao() {//返回随机加密后秘钥
        String string="";
        String in="";
        if (miyao.length()!=0){
            string=miyao;
        }else {
            string="先获取密文";
        }
        return string;
    }
    private void setMiyao() {//获取随机码秘钥
        math math=new math(mingwen.length());
        miyao = math.radom();
    }

    private ArrayList<Character> characters_mingwen(){//将明文字符串转换为字符集合
        ArrayList<Character> characters=new ArrayList<Character>();
        for (int i = 0; i < mingwen.length(); i++) {
            characters.add(mingwen.charAt(i));
        }
        return characters;
    }
    private ArrayList<Integer> integers_miyao(){//将秘钥转换为(ASCII码集合)单个数字
        ArrayList<Integer> integers=new ArrayList<Integer>();
        for (int i
             = 0; i < miyao.length(); i++) {
            String s= String.valueOf(miyao.charAt(i));
            int a=Integer.parseInt(s);
            integers.add(a);
        }
        i_miyao=integers;
        return integers;
    }
    private ArrayList<Integer> integers_mingwen(){//将字符集合转换为ASCII码集合

        ArrayList<Integer> integers=new ArrayList<Integer>();
        for (int i = 0; i < characters_mingwen().size(); i++) {
            integers.add(Integer.valueOf(characters_mingwen().get(i)));
        }
        return integers;
    }
    private ArrayList<Integer> integers_miwen(){//将（明文ASCII码集合）和（秘钥ASCII码集合）组合成（密文ASCII码集合）
        ArrayList<Integer> integers=new ArrayList<Integer>();
        for (int i=0;i<integers_mingwen().size();i++){

                integers.add((integers_mingwen().get(i))+(integers_miyao().get(i))+(integers_mingwen().size()-i));

        }
        return integers;
    }
    private ArrayList<Character> characters_miwen() {//将密文ASCII码集合转换为字符集合
        ArrayList<Character> characters = new ArrayList<Character>();
        for (int i = 0; i < integers_miwen().size(); i++) {
            int b = integers_miwen().get(i);
            char c = (char) b;
            characters.add(c);
        }
        return characters;
    }
    public String Resuilt(){
        String str="";
        for (int i=0;i<characters_miwen().size();i++){
            str+=String.valueOf(characters_miwen().get(i));
        }
        return str;
    }
}
