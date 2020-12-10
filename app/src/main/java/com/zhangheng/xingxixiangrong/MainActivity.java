package com.zhangheng.xingxixiangrong;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Message.math;
import Message.message;
import utils.IVideo;
import utils.VideoImpl;

import static com.zhangheng.xingxixiangrong.R.id.cancel_action;
import static com.zhangheng.xingxixiangrong.R.id.shimi1_open;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final String APP_CACHE_DIRNAME = "/zhanghengmyweb";// web缓存目录
    private Button myweb_btn_url,btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,
    btn17,btn18,btn19,btn20,btn21,btn22,btn23,shimi_close,shimi_open,btn24,btn25,btn26,btn27;
    private ImageButton web_ibtn_fanhui,web_ibtn_qianjin;
    private WebView myweb;
    private ViewStub nomsg;
    private ProgressBar progressBar;
    private TextView tv_progress,tv_title;
    private EditText shimi_pwd;
    private AutoCompleteTextView myweb_et_url;
    private LinearLayout shimi_content;
    private SlidingDrawer slidingDrawer;
    private LinearLayout myweb_layout;
    private List<String> list=new ArrayList<>();
    final int NOTIFYID = 1;//通知ID
    //private boolean isError=false;//网页是否加载失败
    SharedPreferences sharedPreferences;
    boolean f=false;
    String videoURL;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myweb_btn_url= (Button) findViewById(R.id.myweb_btn_url);
        myweb_et_url= (AutoCompleteTextView) findViewById(R.id.myweb_et_url);
        btn0 = (Button) findViewById(R.id.myweb_btn0);
        btn1 = (Button) findViewById(R.id.myweb_btn1);
        btn2 = (Button) findViewById(R.id.myweb_btn2);
        btn3 = (Button) findViewById(R.id.myweb_btn3);
        btn4 = (Button) findViewById(R.id.myweb_btn4);
        btn5 = (Button) findViewById(R.id.myweb_btn5);
        btn6 = (Button) findViewById(R.id.myweb_btn6);
        btn7 = (Button) findViewById(R.id.myweb_btn7);
        btn8 = (Button) findViewById(R.id.myweb_btn8);
        btn9 = (Button) findViewById(R.id.myweb_btn9);
        btn10 = (Button) findViewById(R.id.myweb_btn10);
        btn11 = (Button) findViewById(R.id.myweb_btn11);
        btn12 = (Button) findViewById(R.id.myweb_btn12);
        btn13 = (Button) findViewById(R.id.myweb_btn13);
        btn14 = (Button) findViewById(R.id.myweb_btn14);
        btn15 = (Button) findViewById(R.id.myweb_btn15);
        btn16 = (Button) findViewById(R.id.myweb_btn16);
        btn17 = (Button) findViewById(R.id.myweb_btn17);
        btn18 = (Button) findViewById(R.id.myweb_btn18);
        btn19 = (Button) findViewById(R.id.myweb_btn19);
        btn20 = (Button) findViewById(R.id.myweb_btn20);
        btn21 = (Button) findViewById(R.id.myweb_btn21);
        btn22 = (Button) findViewById(R.id.myweb_btn22);
        btn23 = (Button) findViewById(R.id.myweb_btn23);
        btn24 = (Button) findViewById(R.id.myweb_btn24);
        btn25 = (Button) findViewById(R.id.myweb_btn25);
        btn26 = (Button) findViewById(R.id.myweb_btn26);
        btn27= (Button) findViewById(R.id.myweb_btn27);
        shimi_pwd= (EditText) findViewById(R.id.shimi_pwd);
        shimi_close= (Button) findViewById(R.id.shimi_close);
        shimi_open= (Button) findViewById(R.id.shimi1_open);
        web_ibtn_fanhui = (ImageButton) findViewById(R.id.myweb_ibtn_fanhui);
        web_ibtn_qianjin = (ImageButton) findViewById(R.id.myweb_ibtn_qianjin);
        tv_progress = (TextView) findViewById(R.id.myweb_tv_progress);
        tv_title = (TextView) findViewById(R.id.myweb_tv_title);
        progressBar= (ProgressBar) findViewById(R.id.myweb_progressBar);
        slidingDrawer= (SlidingDrawer) findViewById(R.id.sd_myweb);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                shimi_pwd.setText("");
                shimi_content.setVisibility(View.GONE);
            }
        });
        shimi_content= (LinearLayout) findViewById(R.id.shimi_content);
        myweb_layout= (LinearLayout) findViewById(R.id.myweb_layout);

        shimi_content.setVisibility(View.GONE);

        setOnClickListener();//点击事件
        setOnLongClickListener();//长按事件
        showScrenInfo();//适配抽屉窗口的大小
        CunWeburl();//存输入网址的历史记录
        showWeburl();//显示输入网址的历史记录
        //clearHistory();//清除所有历史记录

        myweb = (WebView) findViewById(R.id.myweb);
        //myweb.setLayerType(View.LAYER_TYPE_HARDWARE, null);//设置打开*setLayerType(View.LAYER_TYPE_SOFTWARE, null);//设置关闭*
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.getSettings().setDomStorageEnabled(true);
        //LOAD_CACHE_ONLY:
        //LOAD_DEFAULT: （默认）根据cache-control决定是 不使用网络，只读取本地缓存数据否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据
        myweb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        myweb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口缩放处理
        //myweb.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);//设置是否支持flash插件
        myweb.getSettings().setMediaPlaybackRequiresUserGesture(false);//自动播放,WebView是否需要用户的手势进行媒体播放

        // 开启database storage API功能
        myweb.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = this.getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        //Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
        myweb.getSettings().setAppCachePath(cacheDirPath);
        myweb.getSettings().setAppCacheEnabled(true);
        //Log.i("databasepath", myweb.getSettings().getDatabasePath());
        myweb.getSettings().setUseWideViewPort(false);  //将图片调整到适合webview的大小
        myweb.getSettings().setBuiltInZoomControls(true); //设置支持缩放
        myweb.getSettings().setSupportZoom(true);  //支持缩放
        myweb.getSettings().setLoadsImagesAutomatically(true);  //支持自动加载图片
        myweb.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
        myweb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局默认NARROW_COLUMNS
        myweb.getSettings().supportMultipleWindows();  //多窗口
        myweb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myweb.setWebViewClient(new MywebViewClient(MainActivity.this));
        myweb.setWebChromeClient(new MyWebChromeClientToMyWeb(new VideoImpl(this, myweb)));
        myweb.setDownloadListener(new MywebViewDownLoadListener());
        //web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //myweb.loadUrl("http://y.webzcz.cn/");//Free音乐
        //myweb.loadUrl("https://www.baidu.com");//百度
        //设置主页地址
        sharedPreferences=getSharedPreferences("weburl",MODE_PRIVATE);
        String url=sharedPreferences.getString("url","https://m.youku.com/");
        myweb.loadUrl(url);
        myweb.loadUrl("javascript:alert(\"星曦向荣公告：\n(1)点击右上角的黑色三角形可以查看更多功能；" +
                "\n(2)长按标题可将当前页面设置主页；\n(3)单击标题可将当前页面的网址显示至搜索栏；" +
                "\n(4)目前视频只能解析腾讯，爱奇艺，优酷；\")");

        //myweb.loadUrl("http://lcoc.top/666/");//MKOnlinePlayer

        /*if (Build.VERSION.SDK_INT < 8) {
            myweb.getSettings().setPluginsEnabled(true);

        } else {}*/
            myweb.getSettings().setPluginState(WebSettings.PluginState.ON);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            myweb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (slidingDrawer.isFocused()){
            f=!f;
        }
        myweb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slidingDrawer.close();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        slidingDrawer.close();
                        break;
                }
                return f;
            }
        });

    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                slidingDrawer.close();
                break;
            case MotionEvent.ACTION_MOVE:
                slidingDrawer.close();
                break;
        }
        return true;
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        myweb.onResume();
        /*if (myweb != null) {
            myweb.destroy();
        }
        //获取通知管理服务
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //创建消息通知类
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this);
        //设置打开消息通知后该消息通知自动消失
        notification.setAutoCancel(true);
        //消息提示音
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setDefaults(Notification.DEFAULT_LIGHTS);
        //设置标题栏消息通知小图标
        notification.setSmallIcon(R.mipmap.weixinlogo);
        //设置下拉列表中的大图标
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.weixinlogo));
        //设置通知内容的主题
        notification.setContentTitle("ZH的星曦向荣万能应用");
        //设置通知内容
        notification.setContentText(tv_title.getText().toString());
        //设置通知时间
        notification.setWhen(System.currentTimeMillis());
        //创建一个启动其他界面的Intent
        Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,0);
        //点击通知栏进行界面跳转
        notification.setContentIntent(pi);
        //  清除登录通知
        //notificationManager.cancel(1);
        //System.exit(0);
        //发送通知
        notificationManager.notify(NOTIFYID, notification.build());*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        //showWeburl();
        myweb.resumeTimers();
        shimi_content.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this,"尊敬的用户,欢迎回来",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //获取通知管理服务
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);//  清除通知
    }

    public void setOnClickListener(){
        OnClickmyweb onClick=new OnClickmyweb();
        btn0.setOnClickListener(onClick);
        btn1.setOnClickListener(onClick);
        btn2.setOnClickListener(onClick);
        btn3.setOnClickListener(onClick);
        btn4.setOnClickListener(onClick);
        btn5.setOnClickListener(onClick);
        btn6.setOnClickListener(onClick);
        btn7.setOnClickListener(onClick);
        btn8.setOnClickListener(onClick);
        btn9.setOnClickListener(onClick);
        btn10.setOnClickListener(onClick);
        btn11.setOnClickListener(onClick);
        btn12.setOnClickListener(onClick);
        btn13.setOnClickListener(onClick);
        btn14.setOnClickListener(onClick);
        btn15.setOnClickListener(onClick);
        btn16.setOnClickListener(onClick);
        btn17.setOnClickListener(onClick);
        btn18.setOnClickListener(onClick);
        btn19.setOnClickListener(onClick);
        btn20.setOnClickListener(onClick);
        btn21.setOnClickListener(onClick);
        btn22.setOnClickListener(onClick);
        btn23.setOnClickListener(onClick);
        btn24.setOnClickListener(onClick);
        btn25.setOnClickListener(onClick);
        btn26.setOnClickListener(onClick);
        btn27.setOnClickListener(onClick);
        myweb_btn_url.setOnClickListener(onClick);
        web_ibtn_fanhui.setOnClickListener(onClick);
        web_ibtn_qianjin.setOnClickListener(onClick);
        tv_progress.setOnClickListener(onClick);
        shimi_open.setOnClickListener(onClick);
        shimi_close.setOnClickListener(onClick);
        tv_title.setOnClickListener(onClick);
    }
    public void setOnLongClickListener(){
        OnLongClickmyweb onLongClick=new OnLongClickmyweb();
        btn1.setOnLongClickListener(onLongClick);
        btn2.setOnLongClickListener(onLongClick);
        btn3.setOnLongClickListener(onLongClick);
        btn4.setOnLongClickListener(onLongClick);
        btn5.setOnLongClickListener(onLongClick);
        btn6.setOnLongClickListener(onLongClick);
        btn7.setOnLongClickListener(onLongClick);
        btn19.setOnLongClickListener(onLongClick);
        btn9.setOnLongClickListener(onLongClick);
        btn10.setOnLongClickListener(onLongClick);
        btn11.setOnLongClickListener(onLongClick);
        btn12.setOnLongClickListener(onLongClick);
        btn13.setOnLongClickListener(onLongClick);
        btn14.setOnLongClickListener(onLongClick);
        btn15.setOnLongClickListener(onLongClick);
        btn16.setOnLongClickListener(onLongClick);
        btn17.setOnLongClickListener(onLongClick);
        btn18.setOnLongClickListener(onLongClick);
        btn20.setOnLongClickListener(onLongClick);
        btn21.setOnLongClickListener(onLongClick);
        btn22.setOnLongClickListener(onLongClick);
        btn23.setOnLongClickListener(onLongClick);
        btn24.setOnLongClickListener(onLongClick);
        btn25.setOnLongClickListener(onLongClick);
        tv_title.setOnLongClickListener(onLongClick);

    }
    private class OnClickmyweb implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            boolean b=false;
            switch (v.getId()){
                case R.id.myweb_tv_title:
                    b=!b;
                    if (b) {
                        String weburl = myweb.getUrl().toString();
                       myweb_et_url.setText(weburl);
                        Toast.makeText(MainActivity.this,"本页网址已粘贴到搜索栏",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.shimi1_open:

                    if (shimi_pwd.getText().toString().length()!=0){
                        if (getResources().getString(R.string.password).equals(shimi_pwd.getText().toString().trim())){
                            shimi_content.setVisibility(View.VISIBLE);
                        }else if ("13733430842".equals(shimi_pwd.getText().toString().trim())){
                            math ma=new math();
                            message m=new message("13733430842",ma.radom());
                            shimi_pwd.setText(m.MessageMain());
                        }
                        else {
                            Toast.makeText(MainActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this,"密码为空！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.shimi_close:
                    shimi_pwd.setText("");
                    shimi_content.setVisibility(View.GONE);
                    break;
                case R.id.myweb_btn_url:
                    String str1=myweb_et_url.getText().toString();
                    if (myweb_et_url.getText().toString().trim().length()!=0) {
                        if (myweb_et_url.getText().toString().startsWith("http:")||myweb_et_url.getText().toString().startsWith("https:")) {
                            myweb.loadUrl(myweb_et_url.getText().toString().trim());
                        } else if (myweb_et_url.getText().toString().startsWith("www.")||myweb_et_url.getText().toString().startsWith("m.")){
                                myweb.loadUrl("http://" + myweb_et_url.getText().toString().trim());
                            }else {
                            myweb.loadUrl("https://www.baidu.com/s?ie=UTF-8&wd=" + myweb_et_url.getText().toString().trim());
                        }

                        if(list.indexOf(str1)<0) {
                            list.add(0, str1);

                        }
                        //Toast.makeText(MainActivity.this, str1, Toast.LENGTH_SHORT).show();

                        CunWeburl();
                        //showWeburl();

                    }else {
                        Toast.makeText(MainActivity.this,"地址不能为空！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.myweb_btn0:
                    System.exit(0);
                    //web.clearCache(true);//清除缓存
                    //Toast.makeText(getActivity(),"清除缓存",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myweb_btn1:
                    myweb.loadUrl(getResources().getString(R.string.uri1));//韩剧网
                    //web.loadUrl("https://v.nn69.top/");//思古影视

                    break;

                case R.id.myweb_btn2:
                    myweb.loadUrl(getResources().getString(R.string.uri2));//百度
                    //Toast.makeText(getActivity(),"百度",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.myweb_btn3:
                    myweb.loadUrl(getResources().getString(R.string.uri3));//Free音乐
                    //myweb.loadUrl("http://lcoc.top/666/");//MKOnlinePlayer
                    //Toast.makeText(getActivity(),"这是音乐",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myweb_btn4:
                    myweb.loadUrl(getResources().getString(R.string.uri4));//微信公众号音乐
                    break;
                case R.id.myweb_btn5:
                    myweb.loadUrl(getResources().getString(R.string.uri5));//116看影视
                    break;
                case R.id.myweb_btn6:
                    myweb.loadUrl(getResources().getString(R.string.uri6));//影视全网集
                    //web.loadUrl("https://www.518kv.com/");//云播影院
                    break;
                case R.id.myweb_btn7:
                    //myweb.loadUrl("http://www.qji.me/");//影视全网集
                    myweb.loadUrl(getResources().getString(R.string.uri7));//云播影院
                    //Toast.makeText(MainActivity.this, "云播影院", Toast.LENGTH_LONG).show();
                    break;
                case R.id.myweb_btn8:
                    //清除网页访问留下的缓存
                    //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
                    //Webview.clearCache(true);

                    //清除当前webview访问的历史记录
                    //只会webview访问历史记录里的所有记录除了当前访问记录
                    //myweb.clearHistory();

                    //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
                    //Webview.clearFormData();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示：");
                    dialog.setMessage("清除记录会清理所有的浏览记录和数据，确定继续清除？");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myweb.clearCache(true);//清除缓存
                            Toast.makeText(MainActivity.this,"清除完成",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Toast.makeText(MainActivity.this, "请查看其他", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (dialog != null) {
                        dialog.show();
                    }

                    break;
                case R.id.myweb_btn9:
                    AlertDialog.Builder dialog9 = new AlertDialog.Builder(MainActivity.this);
                    dialog9.setTitle("注意");
                    dialog9.setMessage("该网站是违规网站，18岁以下禁入");
                    dialog9.setPositiveButton("进入", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myweb.loadUrl(getResources().getString(R.string.uri9));//资源1
                            //Toast.makeText(MainActivity.this, "请注意身体", Toast.LENGTH_LONG).show();
                            myweb.loadUrl("javascript:alert('来自星曦向荣的提示：\n请注意身体')");
                        }
                    });
                    dialog9.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "请查看其他", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (dialog9 != null) {
                        dialog9.show();
                    }
                    break;
                case R.id.myweb_btn10:
                    myweb.loadUrl(getResources().getString(R.string.uri10));//史莱姆
                    break;
                case R.id.myweb_btn11:
                    myweb.loadUrl(getResources().getString(R.string.uri11));//茶杯狐
                    break;
                case R.id.myweb_btn12:
                    myweb.loadUrl(getResources().getString(R.string.uri12));//坑搜网
                    break;
                case R.id.myweb_btn13:
                    myweb.loadUrl(getResources().getString(R.string.uri13));

                    //myweb.loadUrl("javascript:alert('来自星曦向荣的提示：\n请连接305寝室的移动WiFi后方可正常使用')");
                    //myweb.loadUrl("https://weibo.com/");//微博
                    break;
                case R.id.myweb_btn14:
                    myweb.loadUrl(getResources().getString(R.string.uri14));//哔哩哔哩
                    break;
                case R.id.myweb_btn15:

                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
                    dialog1.setTitle("注意");
                    dialog1.setMessage("该网站是违规网站，18岁以下禁入");
                    dialog1.setPositiveButton("进入", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myweb.loadUrl(getResources().getString(R.string.uri15));//资源2
                            //Toast.makeText(MainActivity.this, "请注意身体", Toast.LENGTH_LONG).show();
                            myweb.loadUrl("javascript:alert('来自星曦向荣的提示：\n请注意身体')");
                        }
                    });
                    dialog1.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "请查看其他", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (dialog1 != null) {
                        dialog1.show();
                    }
                    break;
                case R.id.myweb_btn16:
                    myweb.loadUrl(getResources().getString(R.string.uri16));//蓝调网站
                    break;
                case R.id.myweb_btn17:
                    myweb.loadUrl(getResources().getString(R.string.uri17));//斗鱼直播
                    break;
                case R.id.myweb_btn18:
                    myweb.loadUrl(getResources().getString(R.string.uri18));//虎牙直播
                    break;
                case R.id.myweb_btn19:
                    //myweb.loadUrl("https://egame.qq.com/");//企鹅电竞
                    myweb.loadUrl("https://tv.cctv.com/live/index.shtml?spm=C28340.P9dhkRStLqPh.EYq0LGFLODJm.2");//央视直播
                    myweb.loadUrl("javascript:alert('来自星曦向荣的提示：\n如果直播卡播，建议看回放')");
                    break;
                case R.id.myweb_btn20:
                    myweb.loadUrl(getResources().getString(R.string.uri20));
                    break;
                case R.id.myweb_btn21:
                    myweb.loadUrl(getResources().getString(R.string.uri21));
                    break;
                case R.id.myweb_btn22:
                    myweb.loadUrl(getResources().getString(R.string.uri22));
                    break;
                case R.id.myweb_btn23:
                    myweb.loadUrl(getResources().getString(R.string.uri23));
                    myweb.loadUrl("javascript:alert('来自星曦向荣的提示：\n直播可能能无法横屏观看')");
                    break;
                case R.id.myweb_btn24:
                    myweb.loadUrl(getResources().getString(R.string.uri24));
                    break;
                case R.id.myweb_btn25:
                    myweb.loadUrl(getResources().getString(R.string.uri25));
                    break;
                case R.id.myweb_btn26:
                    AlertDialog.Builder dialog26 = new AlertDialog.Builder(MainActivity.this);
                    dialog26.setTitle("提示：");
                    dialog26.setMessage("清除历史记录会清理所有的网址输入记录，确定继续清除？");
                    dialog26.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //myweb.clearCache(true);//清除缓存
                            clearHistory();
                            Toast.makeText(MainActivity.this,"清除完成，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog26.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Toast.makeText(MainActivity.this, "请查看其他", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (dialog26 != null) {
                        dialog26.show();
                    }
                    clearHistory();
                    break;
                case R.id.myweb_btn27:
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    break;

                case R.id.myweb_ibtn_fanhui:
                    if (myweb.canGoBack()){
                        myweb.goBack();
                    }else {
                        Toast.makeText(MainActivity.this,"到底了！",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.myweb_ibtn_qianjin:
                    if (myweb.canGoForward()){
                        myweb.goForward();
                    }else {
                        Toast.makeText(MainActivity.this,"到顶了！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.myweb_tv_progress:
                    myweb.reload();
                    Toast.makeText(MainActivity.this,"刷新中",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class OnLongClickmyweb implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.myweb_tv_title:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("主页设置");
                    dialog.setMessage("是否将该<"+tv_title.getText().toString()+">设置为主页面？");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url",myweb.getUrl().toString());
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog.show();
                    break;
                case R.id.myweb_btn1:
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
                    dialog1.setTitle("主页设置");
                    dialog1.setMessage("是否将该 "+getResources().getString(R.string.chat_btn1)+" 设置为主页面？");
                    dialog1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri1));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog1.show();
                    /*if (dialog1 != null) {
                        dialog1.show();
                    }*/
                    break;
                case R.id.myweb_btn2:
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(MainActivity.this);
                    dialog2.setTitle("主页设置");
                    dialog2.setMessage("是否将该 "+getResources().getString(R.string.chat_btn2)+" 设置为主页面？");
                    dialog2.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri2));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog2.show();
                    break;
                case R.id.myweb_btn3:
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(MainActivity.this);
                    dialog3.setTitle("主页设置");
                    dialog3.setMessage("是否将该 "+getResources().getString(R.string.chat_btn3)+" 设置为主页面？");
                    dialog3.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri3));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog3.show();
                    break;
                case R.id.myweb_btn4:
                    AlertDialog.Builder dialog4 = new AlertDialog.Builder(MainActivity.this);
                    dialog4.setTitle("主页设置");
                    dialog4.setMessage("是否将该 "+getResources().getString(R.string.chat_btn4)+" 设置为主页面？");
                    dialog4.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri4));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog4.show();
                    break;
                case R.id.myweb_btn5:
                    AlertDialog.Builder dialog5 = new AlertDialog.Builder(MainActivity.this);
                    dialog5.setTitle("主页设置");
                    dialog5.setMessage("是否将该 "+getResources().getString(R.string.chat_btn5)+" 设置为主页面？");
                    dialog5.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri5));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog5.show();
                    break;
                case R.id.myweb_btn6:
                    AlertDialog.Builder dialog6 = new AlertDialog.Builder(MainActivity.this);
                    dialog6.setTitle("主页设置");
                    dialog6.setMessage("是否将该 "+getResources().getString(R.string.chat_btn6)+" 设置为主页面？");
                    dialog6.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri6));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog6.show();
                    break;
                case R.id.myweb_btn7:
                    AlertDialog.Builder dialog7 = new AlertDialog.Builder(MainActivity.this);
                    dialog7.setTitle("主页设置");
                    dialog7.setMessage("是否将该 "+getResources().getString(R.string.chat_btn7)+" 设置为主页面？");
                    dialog7.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri7));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog7.show();
                    break;
                case R.id.myweb_btn19:
                    AlertDialog.Builder dialog19 = new AlertDialog.Builder(MainActivity.this);
                    dialog19.setTitle("主页设置");
                    dialog19.setMessage("是否将该 "+getResources().getString(R.string.btn19)+" 设置为主页面？");
                    dialog19.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri19));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog19.show();
                    break;
                case R.id.myweb_btn9:
                    AlertDialog.Builder dialog9 = new AlertDialog.Builder(MainActivity.this);
                    dialog9.setTitle("主页设置");
                    dialog9.setMessage("是否将该 "+getResources().getString(R.string.btn9)+" 设置为主页面？");
                    dialog9.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri9));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog9.show();
                    break;
                case R.id.myweb_btn10:
                    AlertDialog.Builder dialog10 = new AlertDialog.Builder(MainActivity.this);
                    dialog10.setTitle("主页设置");
                    dialog10.setMessage("是否将该 "+getResources().getString(R.string.btn10)+" 设置为主页面？");
                    dialog10.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri10));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog10.show();
                    break;
                case R.id.myweb_btn11:
                    AlertDialog.Builder dialog11 = new AlertDialog.Builder(MainActivity.this);
                    dialog11.setTitle("主页设置");
                    dialog11.setMessage("是否将该 "+getResources().getString(R.string.btn11)+" 设置为主页面？");
                    dialog11.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri11));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog11.show();
                    break;
                case R.id.myweb_btn12:
                    AlertDialog.Builder dialog12 = new AlertDialog.Builder(MainActivity.this);
                    dialog12.setTitle("主页设置");
                    dialog12.setMessage("是否将该 "+getResources().getString(R.string.btn12)+" 设置为主页面？");
                    dialog12.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri12));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog12.show();
                    break;
                case R.id.myweb_btn13:
                    AlertDialog.Builder dialog13 = new AlertDialog.Builder(MainActivity.this);
                    dialog13.setTitle("主页设置");
                    dialog13.setMessage("是否将该 "+getResources().getString(R.string.btn13)+" 设置为主页面？");
                    dialog13.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri13));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog13.show();
                    break;
                case R.id.myweb_btn14:
                    AlertDialog.Builder dialog14 = new AlertDialog.Builder(MainActivity.this);
                    dialog14.setTitle("主页设置");
                    dialog14.setMessage("是否将该 "+getResources().getString(R.string.btn14)+" 设置为主页面？");
                    dialog14.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri14));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog14.show();
                    break;
                case R.id.myweb_btn15:
                    AlertDialog.Builder dialog15 = new AlertDialog.Builder(MainActivity.this);
                    dialog15.setTitle("主页设置");
                    dialog15.setMessage("是否将该 "+getResources().getString(R.string.btn15)+" 设置为主页面？");
                    dialog15.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri15));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog15.show();
                    break;
                case R.id.myweb_btn16:
                    AlertDialog.Builder dialog16 = new AlertDialog.Builder(MainActivity.this);
                    dialog16.setTitle("主页设置");
                    dialog16.setMessage("是否将该 "+getResources().getString(R.string.btn16)+" 设置为主页面？");
                    dialog16.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri16));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog16.show();
                    break;
                case R.id.myweb_btn17:
                    AlertDialog.Builder dialog17 = new AlertDialog.Builder(MainActivity.this);
                    dialog17.setTitle("主页设置");
                    dialog17.setMessage("是否将该 "+getResources().getString(R.string.btn17)+" 设置为主页面？");
                    dialog17.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri17));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog17.show();
                    break;
                case R.id.myweb_btn18:
                    AlertDialog.Builder dialog18 = new AlertDialog.Builder(MainActivity.this);
                    dialog18.setTitle("主页设置");
                    dialog18.setMessage("是否将该 "+getResources().getString(R.string.btn18)+" 设置为主页面？");
                    dialog18.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri18));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog18.show();
                    break;
                case R.id.myweb_btn20:
                    AlertDialog.Builder dialog20 = new AlertDialog.Builder(MainActivity.this);
                    dialog20.setTitle("主页设置");
                    dialog20.setMessage("是否将该 "+getResources().getString(R.string.btn20)+" 设置为主页面？");
                    dialog20.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri20));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog20.show();
                    break;
                case R.id.myweb_btn21:
                    AlertDialog.Builder dialog21 = new AlertDialog.Builder(MainActivity.this);
                    dialog21.setTitle("主页设置");
                    dialog21.setMessage("是否将该 "+getResources().getString(R.string.btn21)+" 设置为主页面？");
                    dialog21.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri21));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog21.show();
                    break;
                case R.id.myweb_btn22:
                    AlertDialog.Builder dialog22 = new AlertDialog.Builder(MainActivity.this);
                    dialog22.setTitle("主页设置");
                    dialog22.setMessage("是否将该 "+getResources().getString(R.string.btn22)+" 设置为主页面？");
                    dialog22.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri22));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog22.show();
                    break;
                case R.id.myweb_btn23:
                    AlertDialog.Builder dialog23 = new AlertDialog.Builder(MainActivity.this);
                    dialog23.setTitle("主页设置");
                    dialog23.setMessage("是否将该 "+getResources().getString(R.string.btn23)+" 设置为主页面？");
                    dialog23.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri23));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog23.show();
                    break;
                case R.id.myweb_btn24:
                    AlertDialog.Builder dialog24 = new AlertDialog.Builder(MainActivity.this);
                    dialog24.setTitle("主页设置");
                    dialog24.setMessage("是否将该 "+getResources().getString(R.string.btn24)+" 设置为主页面？");
                    dialog24.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri24));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog24.show();
                    break;
                case R.id.myweb_btn25:
                    AlertDialog.Builder dialog25 = new AlertDialog.Builder(MainActivity.this);
                    dialog25.setTitle("主页设置");
                    dialog25.setMessage("是否将该 "+getResources().getString(R.string.btn25)+" 设置为主页面？");
                    dialog25.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences = getSharedPreferences("weburl", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("url", getResources().getString(R.string.uri25));
                            editor.commit();
                            Toast.makeText(MainActivity.this,"设置成功，重启应用后生效",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });dialog25.show();
                    break;
            }
            return false;
        }
    }

    class MywebViewClient extends WebViewClient {
        private Context context ;

        public MywebViewClient(Context context) {
            this.context = context ;

        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, WebResourceRequest request) {
            try {
                if (request.getUrl().toString().startsWith("http:")||request.getUrl().toString().startsWith("https:")){
                    String url=request.getUrl().toString();
                    videoURL=url;
                    view.loadUrl(url);

                    if (url.startsWith("https://m.v.qq.com/x/m/play?")||url.startsWith("http://m.v.qq.com/cover/")
                            ||url.startsWith("http://m.v.qq.com/x/m/play?")
                            ||url.startsWith("https://m.v.qq.com/x/m/play?cid=")
                            ||url.startsWith("https://m.v.qq.com/play.html?cid=")
                            ){//腾讯视频
                        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("解析视频");
                        dialog.setMessage("是否前往？");
                        dialog.setPositiveButton("是(线路1)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myweb.loadUrl("http://mimijiexi.top/?url="+videoURL);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"腾讯",Toast.LENGTH_SHORT).show();
                            }
                        }).setNeutralButton("备用线路(线路2)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myweb.loadUrl("https://jx.116kan.com/?url="+videoURL);
                            }
                        });
                        dialog.show();
                    }else if (url.startsWith("https://m.iqiyi.com/v_")
                            ||url.startsWith("https://www.iqiyi.com/v_")){//爱奇艺
                        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("解析视频");
                        dialog.setMessage("是否前往？");
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myweb.loadUrl("http://mimijiexi.top/?url="+videoURL);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(videoURL.startsWith("http:")||videoURL.startsWith("https:")) {
                                    myweb.loadUrl(videoURL);
                                    //Toast.makeText(MainActivity.this, "爱奇艺1", Toast.LENGTH_SHORT).show();
                                }else {
                                    //Toast.makeText(MainActivity.this, "爱奇艺2", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(videoURL));
                                    startActivity(intent);
                                }
                                //Toast.makeText(MainActivity.this, "爱奇艺3", Toast.LENGTH_SHORT).show();
                            }
                        }).setNeutralButton("备用线路(线路2)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myweb.loadUrl("https://jx.116kan.com/?url="+videoURL);
                            }
                        });
                        dialog.show();
                        //myweb.loadUrl("https://jx.116kan.com/?url="+url);
                        //myweb.loadUrl("http://mimijiexi.top/?url="+url);

                    }else if (url.startsWith("https://m.youku.com/alipay_video/id_")
                            ||url.startsWith("https://m.youku.com/video/id_")){//优酷
                        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("解析视频");
                        dialog.setMessage("是否前往？");
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //myweb.loadUrl("http://mimijiexi.top/?url="+videoURL);
                                myweb.loadUrl("https://jx.116kan.com/?url="+videoURL);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(videoURL.startsWith("http:")||videoURL.startsWith("https:")) {
                                    myweb.loadUrl(videoURL);
                                    //Toast.makeText(MainActivity.this, "优酷1", Toast.LENGTH_SHORT).show();
                                }else {
                                    //Toast.makeText(MainActivity.this, "优酷2", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(videoURL));
                                    startActivity(intent);
                                }
                                //Toast.makeText(MainActivity.this, "优酷3", Toast.LENGTH_SHORT).show();
                            }
                        }).setNeutralButton("备用线路(线路2)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myweb.loadUrl("http://mimijiexi.top/?url="+videoURL);
                            }
                        });
                        dialog.show();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"正在打开当前页面的应用",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(request.getUrl().toString()));
                    startActivity(intent);
                }
                return true;
            }catch (Exception e){
                return false;
            }
        }
        public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
            //判断是否是广告相关的资源链接
            if (!ADFilterTool.hasAd(context, url)) {
                //这里是不做处理的数据
                return super.shouldInterceptRequest(webView, url);
            } else {
                //有广告的请求数据，我们直接返回空数据，注：不能直接返回null
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = ADFilterTool.getClearAdDivJs(context);
            view.loadUrl(js);
            String cls = "javascript:";
            cls+="document.getElementsByClassName('dplayer-controller-mask')[0].style.display = 'none'";//class的名字
            view.loadUrl(cls);


        }


    }
    class MyWebChromeClientToMyWeb extends WebChromeClient {

        private IVideo myIVideo;

        public MyWebChromeClientToMyWeb(IVideo mIVideo) {
            this.myIVideo = mIVideo;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myIVideo != null) {
                myIVideo.onShowCustomView(view, callback);
            }

        }

        @Override
        public void onHideCustomView() {
            if (myIVideo != null) {
                myIVideo.onHideCustomView();
            }
        }
        //网页加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //super.onProgressChanged(view, newProgress);
            if (newProgress !=100) {
                String progress = newProgress + "%";
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                tv_progress.setText(progress);

            } else {
                tv_progress.setText("加载完成(点击刷新)");
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(View.GONE);

            }
    }
        //网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tv_title.setText(title);
            //获取通知管理服务
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //创建消息通知类
            NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this);
            //设置打开消息通知后该消息通知自动消失
            notification.setAutoCancel(true);
            //消息提示音
            notification.setDefaults(Notification.DEFAULT_SOUND);
            notification.setDefaults(Notification.DEFAULT_LIGHTS);
            //设置标题栏消息通知小图标
            notification.setSmallIcon(R.mipmap.weixinlogo);
            //设置下拉列表中的大图标
            notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.weixinlogo));
            //设置通知内容的主题
            notification.setContentTitle("正在运行：");
            //设置通知内容
            notification.setContentText(tv_title.getText().toString());
            //设置通知时间
            notification.setWhen(System.currentTimeMillis());
            /*//创建一个启动其他界面的Intent
            Intent intent = new Intent();
            //intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent,0);
            //点击通知栏进行界面跳转
            notification.setContentIntent(pi);
            //  清除登录通知
            //notificationManager.cancel(1);
            //System.exit(0);
            //发送通知*/
            notificationManager.notify(NOTIFYID, notification.build());

        }
    }
    private class MywebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Log.i("tag", "url="+url);
//            Log.i("tag", "userAgent="+userAgent);
//            Log.i("tag", "contentDisposition="+contentDisposition);
//            Log.i("tag", "mimetype="+mimetype);
//            Log.i("tag", "contentLength="+contentLength);
            //url= web.getUrl().toString();
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，
        // 如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，
        // 因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if (myweb.canGoBack()) {
                myweb.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public void finish() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("退出");
        dialog.setMessage("确定是否要离开？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "取消退出", Toast.LENGTH_LONG).show();
            }
        });
        if (dialog != null) {
            dialog.show();
        }
    }
    private  void showScrenInfo() {
        int width = DisplyUtil.getScreenWidth(this);
        int height = DisplyUtil.getScreenHeight(this);
        //float density = DisplyUtil.getScreenDensity(this);
        RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)slidingDrawer.getLayoutParams();
        SlidingDrawer.LayoutParams params2= (SlidingDrawer.LayoutParams) myweb_layout.getLayoutParams();
        params1.height=(int)(height*0.3);
        params1.width=(int)(width*0.75);
        params2.height=(int)(height*0.3);
    }
    private void CunWeburl(){
        sharedPreferences =getSharedPreferences("webdata_url", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        int n=0;
        if (list.size()>10){
            list.remove(10);
        }
        for (int i=0;i<list.size();i++) {
            String string=list.get(i);
            String a= String.valueOf(i);
            editor.putString(a,string);
            n+=1;
            editor.putInt("n",n);
            editor.commit();
        }

    }
    private void showWeburl(){
        sharedPreferences=getSharedPreferences("webdata_url", MODE_PRIVATE);
        int n=sharedPreferences.getInt("n",list.size());
        for (int j=0;j<n;j++){
            String b= String.valueOf(j);
            String data=sharedPreferences.getString(b,"");
            list.add(data);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item_dropdown,list);
        myweb_et_url.setAdapter(adapter);
    }
    private void clearHistory(){
        /**
         * 清除所有历史记录
         */
        sharedPreferences=getSharedPreferences("webdata_url", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        list.clear();
    }
}
