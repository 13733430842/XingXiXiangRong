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

public class Main2Activity extends AppCompatActivity {

    private Button btn1,btn2,btn1_1,btn2_2;
    private EditText et1,et2,et1_1,et2_2;
    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;
    private int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1= (Button) findViewById(R.id.btn1_t_p);
        btn2= (Button) findViewById(R.id.btn1_p_t);
        btn1_1= (Button) findViewById(R.id.btn2_t_p);
        btn2_2= (Button) findViewById(R.id.btn2_p_t);
        et1= (EditText) findViewById(R.id.edit1_text);
        et2= (EditText) findViewById(R.id.edit1_pwd);
        et1_1= (EditText) findViewById(R.id.edit2_text);
        et2_2= (EditText) findViewById(R.id.edit2_pwd);
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
                et1_1.setText(null);
                et2_2.setText(null);
                switch (checkedId){
                    case R.id.r_1:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                        editor1.putInt("node",1);
                        editor1.apply();
                        break;
                    case R.id.r_2:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor2=sharedPreferences.edit();
                        editor2.putInt("node",2);
                        editor2.apply();
                        break;
                    case R.id.r_3:
                        sharedPreferences=getSharedPreferences("PwdNode",MODE_PRIVATE);
                        SharedPreferences.Editor editor3=sharedPreferences.edit();
                        editor3.putInt("node",3);
                        editor3.apply();
                        break;
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Encryption encryption=new Encryption();

                String text=et1.getText().toString().trim();
                if (text.length()>0){
                    encryption.setpwd(text);
                    et2.setText(encryption.getresuilt());
                    et2.setSelection(et2.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decryption decryption=new Decryption();
                String text=et2.getText().toString().trim();
                if (text.length()>0){
                    decryption.setpwd(text);
                    et1.setText(decryption.getresuilt());
                    et1.setSelection(et1.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                }
            }
        });
        btn1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ASCII ascii=new ASCII();
                String text=et1_1.getText().toString().trim();
                if (text.length()>0){
                    ascii.setpwd(text);
                    et2_2.setText(ascii.getresuilt(n));
                    et2_2.setSelection(et2_2.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1_1.setText("");
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
                    et1_1.setText(resuilt.getresuilt());
                    et1_1.setSelection(et1_1.getText().length());
                }else {
                    Toast.makeText(Main2Activity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    et1_1.setText("");
                    et2_2.setText("");
                }
            }
        });
    }
}
