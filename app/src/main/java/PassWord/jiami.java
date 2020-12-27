package PassWord;

import java.util.ArrayList;

/**
 * �����Կ�������
 */
public class jiami {
    private String miyao,mingwen,miwen;
    private ArrayList<Integer> i_miyao;

    public jiami(String mingwen) {
        this.mingwen = mingwen;
        setMiyao();
    }
    public String getMiyao() {//����������ܺ���Կ
        String string="";
        String in="";
        if (miyao.length()!=0){
            string=miyao;
        }else {
            string="先获取密文";
        }
        return string;
    }
    private void setMiyao() {//��ȡ�������Կ
        math math=new math(mingwen.length());
        miyao = math.radom();
    }

    private ArrayList<Character> characters_mingwen(){//�������ַ���ת��Ϊ�ַ�����
        ArrayList<Character> characters=new ArrayList<Character>();
        for (int i = 0; i < mingwen.length(); i++) {
            characters.add(mingwen.charAt(i));
        }
        return characters;
    }
    private ArrayList<Integer> integers_miyao(){//����Կת��Ϊ(ASCII�뼯��)��������
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
    private ArrayList<Integer> integers_mingwen(){//���ַ�����ת��ΪASCII�뼯��

        ArrayList<Integer> integers=new ArrayList<Integer>();
        for (int i = 0; i < characters_mingwen().size(); i++) {
            integers.add(Integer.valueOf(characters_mingwen().get(i)));
        }
        return integers;
    }
    private ArrayList<Integer> integers_miwen(){//��������ASCII�뼯�ϣ��ͣ���ԿASCII�뼯�ϣ���ϳɣ�����ASCII�뼯�ϣ�
        ArrayList<Integer> integers=new ArrayList<Integer>();
        for (int i=0;i<integers_mingwen().size();i++){

                integers.add((integers_mingwen().get(i))+(integers_miyao().get(i))+(integers_mingwen().size()-i));

        }
        return integers;
    }
    private ArrayList<Character> characters_miwen() {//������ASCII�뼯��ת��Ϊ�ַ�����
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
