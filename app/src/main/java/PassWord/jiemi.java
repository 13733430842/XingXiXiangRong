package PassWord;

import java.util.ArrayList;

/**
 * 随机秘钥密码解密
 */
public class jiemi {
    private String miyao;
    private String miwen;

    public jiemi(String miwen,String miyao) {
        this.miyao = miyao;
        this.miwen = miwen;
    }
    private ArrayList<Character> characters_miwen(){//将密文字符串转换为字符集合
        ArrayList<Character> characters=new ArrayList<>();
        for (int i=0;i<miwen.length();i++){
            characters.add(miwen.charAt(i));
        }
        return characters;
    }
    private ArrayList<Character> characters_miyao(){//将秘钥字符串转换为字符集合
        ArrayList<Character> characters=new ArrayList<>();
        for (int i=0;i<miyao.length();i++){
            characters.add(miyao.charAt(i));
        }
        return characters;
    }
    private ArrayList<Integer> integers_miyao(){//将秘钥字符集合转换为(解密后ASCII码)单数字集合
        ArrayList<Integer> integers=new ArrayList<>();
        for (int i=0;i<characters_miyao().size();i++){
            String s= String.valueOf(characters_miyao().get(i));
            int a=Integer.parseInt(s);
            integers.add(a);
        }
        return integers;
    }
    private ArrayList<Integer> integers_miwen(){//将密文字符集合转换为ASCII码集合
        ArrayList<Integer> integers=new ArrayList<>();
        for (int i=0;i<characters_miwen().size();i++){
            integers.add(Integer.valueOf(characters_miwen().get(i)));
        }
        return integers;
    }
    private ArrayList<Integer> integers_mingwen(){//将（密文ASCII码集合）和（秘钥ASCII码集合）组合成（明文ASCII码集合）
        ArrayList<Integer> integers=new ArrayList<>();
        for (int i=0;i<integers_miwen().size();i++){

                integers.add(integers_miwen().get(i)-integers_miyao().get(i)-(integers_miwen().size()-i));
        }
        return integers;
    }
    private ArrayList<Character> characters_mingwen(){//将明文ASCII码集合转换为字符集合
        ArrayList<Character> characters=new ArrayList<>();
        for (int i=0;i<integers_mingwen().size();i++){
            int b=integers_mingwen().get(i);
            char c=(char)b;
            characters.add(c);
        }
        return characters;
    }
    public String Resuilt(){
        String str="";
        for (int i=0;i<characters_mingwen().size();i++){
            str+=characters_mingwen().get(i);
        }
        return str;
    }
}
