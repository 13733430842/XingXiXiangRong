package com.zhangheng.xingxixiangrong;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by 张恒 on 2020/10/24 0024.
 */
public class ADFilterTool {
    public static boolean hasAd (Context context , String url ) {
        Resources res = context . getResources ( ) ;
        String [ ] adUrls = res . getStringArray ( R.array.adUrls) ;
        for ( String adUrl : adUrls ) {
            if ( url . contains ( adUrl ) ) {
                return true ;
            }
        }
        return false ;
    }

    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for(int i=0;i<adDivs.length;i++){

            js += "var adDiv"+i+"= document.getElementById('"+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
        }
        return js;
    }
}
