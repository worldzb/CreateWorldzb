package com.example.demo;
import android.util.Log;

/**
 * Created by worldzb on 2017/11/8.
 */

public class L {
    private static final String TAG="hello worldzb";
    private static boolean debug=true;
    public static void e(String msg){
        if (debug)
            Log.e(TAG,msg);

    }
}
