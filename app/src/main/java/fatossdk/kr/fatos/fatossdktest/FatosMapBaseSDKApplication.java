package fatossdk.kr.fatos.fatossdktest;

import android.content.Context;

import biz.fatossdk.newanavi.ANaviApplication;

/**
 * Created by kyungilwoo on 2017. 6. 16..
 */

public class FatosMapBaseSDKApplication extends ANaviApplication {

    public static final String TAG = "AMAP";
    private Context m_Context;

    @Override
    public void onCreate() {
        m_Context = this;
        super.onCreate();
    }
}
