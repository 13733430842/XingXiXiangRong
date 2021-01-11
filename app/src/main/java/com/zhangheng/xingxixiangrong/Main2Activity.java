package com.zhangheng.xingxixiangrong;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import PassWord.ASCII;
import PassWord.Decryption;
import PassWord.Encryption;
import PassWord.Resuilt;
import PassWord.jiami;
import PassWord.jiemi;

public class Main2Activity extends AppCompatActivity {

    private Button btn1_1,btn1_2,btn2_1,btn2_2,btn3_1,btn3_2;
    private EditText et1_1,et1_2,et2_1,et2_2,et3_1,et3_miyao,et3_2;
    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;
    private int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1_1= (Button) findViewById(R.id.btn1_t_p);
        btn1_2= (Button) findViewById(R.id.btn1_p_t);
        btn2_1= (Button) findViewById(R.id.btn2_t_p);
        btn2_2= (Button) findViewById(R.id.btn2_p_t);
        btn3_1= (Button) findViewById(R.id.btn3_t_p);
        btn3_2= (Button) findViewById(R.id.btn3_p_t);
        et1_1= (EditText) findViewById(R.id.edit1_text);
        et1_2= (EditText) findViewById(R.id.edit1_pwd);
        et2_1= (EditText) findViewById(R.id.edit2_text);
        et2_2= (EditText) findViewById(R.id.edit2_pwd);
        et3_1= (EditText) findViewById(R.id.edit3_text);
        et3_miyao= (EditText) findViewById(R.id.edit3_miyao);
        et3_2= (EditText) findViewById(R.id.edit3_pwd);
        radioGroup= (RadioGroup) findViewById(R.id.radio_group);

        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
        n=sharedPreferences.getInt("node",1);
        switch (n){
            case 1:
                radioGroup.check(R.id.r_1);
                break;
            case 2:
                radioGroup.check(R.id.r_2);
                break;
            case 3:
                radioGroup.check(R.id.r_3);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                et2_1.setText(null);
                et2_2.setText(null);
                switch (checkedId){
                    case R.id.r_1:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                        editor1.putInt("node",1);
                        editor1.apply();
                        n=sharedPreferences.getInt("node",1);
                        break;
                    case R.id.r_2:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor2=sharedPreferences.edit();
                        editor2.putInt("node",2);
                        editor2.apply();
                        n=sharedPreferences.getInt("node",1);
                        break;
                    case R.id.r_3:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor3=sharedPreferences.edit();
                        editor3.putInt("node",3);
                        editor3.apply();
                        n=sharedPreferences.getInt("node",1);
                        break;
                }
            }
        });

        btn1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Encryption encryption=new Encryption();
                String text=et1_1.getText().toString().trim();
                if (text.length()>0){
                    encryption.setpwd(text);
                    et1_2.setText(encryption.getresuilt());
                    et1_2.setSelection(et1_2.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1_1.setText("");
                    et1_2.setText("");
                }
            }
        });

        btn1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decryption decryption=new Decryption();
                String text=et1_2.getText().toString().trim();
                if (text.length()>0){
                    decryption.setpwd(text);
                    et1_1.setText(decryption.getresuilt());
                    et1_1.setSelection(et1_1.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1_1.setText("");
                    et1_2.setText("");
                }
            }
        });

        btn2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ASCII ascii=new ASCII();
                String text=et2_1.getText().toString().trim();
                if (text.length()>0){
                    ascii.setpwd(text);
                    et2_2.setText(ascii.getresuilt(n));
                    et2_2.setSelection(et2_2.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et2_1.setText("");
                    et2_2.setText("");
                }
            }
        });

        btn2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resuilt resuilt=new Resuilt(n);
                String text=et2_2.getText().toString().trim();
                if (text.length()>0){
                    resuilt.setpwd(text);
                    et2_1.setText(resuilt.getresuilt());
                    et2_1.setSelection(et1_1.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et2_1.setText("");
                    et2_2.setText("");
                }
            }
        });

        btn3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=et3_1.getText().toString();
                if (s.length()>0) {
                    jiami jiami = new jiami(s);
                    et3_2.setText(jiami.Resuilt());
                    et3_2.setSelection(et3_2.getText().length());
                    et3_miyao.setText(jiami.getMiyao());
                    et3_miyao.setSelection(et3_miyao.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et3_2.setText("");
                    et3_miyao.setText("");
                }
            }
        });

        btn3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=et3_2.getText().toString();
                String miyao=et3_miyao.getText().toString();
                if (s.length()>0&&miyao.length()>0){
                    jiemi jiemi=new jiemi(s,miyao);
                    et3_1.setText(jiemi.Resuilt());
                    et3_1.setSelection(et3_1.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et3_1.setText("");
                }
            }
        });
    }
}
