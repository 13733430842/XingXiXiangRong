package utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by 张恒 on 2020/10/5 0005.
 */

public class Utils {
    //获取屏幕宽度
    public static int getScreenWidth(Context context){
        //获取窗口管理服务
        WindowManager wn= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //屏幕参数对象
        DisplayMetrics outMetrics=new DisplayMetrics();
        //获取屏幕参数
        wn.getDefaultDisplay().getMetrics(outMetrics);
        //返回屏幕宽度
        return outMetrics.widthPixels;
    }
}
