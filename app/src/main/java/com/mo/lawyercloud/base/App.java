package com.mo.lawyercloud.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by Mohaifeng on 2018/2/2.
 */

public class App extends Application {
    private static Context mAPPcontext;

    //解决jar重复的一句话跟build.gradle里是配套的
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAPPcontext = this;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
//                .methodCount(2)         // （可选）要显示的方法行数。 默认2
//                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//                .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag("LWYUN")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static Context getAPPcontext(){
        return mAPPcontext;
    }
}
