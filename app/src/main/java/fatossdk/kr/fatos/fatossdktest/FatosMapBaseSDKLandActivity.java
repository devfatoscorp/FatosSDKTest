package fatossdk.kr.fatos.fatossdktest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import biz.fatossdk.config.FatosBuildConfig;
import biz.fatossdk.fminterface.FMBaseActivity;
import biz.fatossdk.fminterface.FMBaseActivity.OnFatosMapBaseActivityListener;
import biz.fatossdk.fminterface.FMInterface;
import biz.fatossdk.navi.NativeNavi;
import biz.fatossdk.newanavi.ANaviApplication;

public class FatosMapBaseSDKLandActivity extends FMBaseActivity implements OnFatosMapBaseActivityListener {
    private ANaviApplication m_gApp;
    private Context m_Context = null;
    private Button m_test1Btn, m_test2Btn;
    private Activity m_MainActivity;
    // Used to load the 'native-lib' library on application startup.

    private FMInterface m_FMInterface;
    private FatosMapSDKSampleLandFragment m_SDKSampleFragment = new FatosMapSDKSampleLandFragment();
    private int m_iEngineInit = 0;

    // Serial key
    private String API_KEY = "5e6c686753643a73f364286b8f362d15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_MainActivity = this;
        m_Context = this;
        m_gApp = (ANaviApplication) m_Context.getApplicationContext();
        m_FMInterface = FMInterface.GetInstance();
        setContentView(R.layout.activity_fatos_map_base_sdk_land);
        // 설정 값 적용
        //NativeNavi.nativeMapSetCarvata(m_MapHandle,m_gApp.getAppSettingInfo().m_nCarIcon);
        // 주야간 모드 적용
        //NativeNavi.nativeMapSetNightMode(m_gApp.getAppSettingInfo().m_nDayNightMode);
        NativeNavi.nativeMapSetNightMode(0);

//        NativeNavi.nativeSetRoutePath(m_gApp.getRoutePathInfo().m_nServiceType);
//        NativeNavi.nativeSetSite(m_gApp.getRoutePathInfo().m_nServiceType);
        NativeNavi.nativeSetMapMode(false);

        NativeNavi.nativeMapRefresh();

        if( m_SDKSampleFragment.isVisible() == false &&
                m_SDKSampleFragment.isAdded() == false)
        {
            addAllowingStateLossFragment(android.R.id.content,
                    m_SDKSampleFragment,
                    FatosMapSDKSampleFragment.FRAGMENT_TAG);
        }

        if(NativeNavi.nativeIsRoute())
            NativeNavi.nativeStartRouteGuidance();


        m_FMInterface.FM_SetMainMapViewListener(new ANaviApplication.MapStatusListener() {
            @Override
            public void onRouteFinish() {
                Log.e(TAG,"### onRouteFinish");
            }

            @Override
            public void onMapMove(boolean bMapMove) {
                Log.e(TAG,"### FatosMapBaseSDKActivity onMapMove");

                m_FMInterface.FM_AutoSetMapCenter(bMapMove);
            }

            @Override
            public void onReRouting() {

            }

            @Override
            public void onCycleReRouting() {

            }

            @Override
            public void onInControlLineArea() {

            }

            @Override
            public void mapReady() {

            }

            @Override
            public void updateObjPickerInfo(int i, String s, String s1, double v, double v1) {

            }

            @Override
            public void updateMapTouch(float v, float v1) {

            }

            @Override
            public void updateMapLongTouch(float v, float v1) {

            }

            @Override
            public void updateMapAngle(float v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnFatosMapBaseActivityListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if(ANaviApplication.m_MapHandle != 0) {
//            if(NativeNavi.nativeIsInitNavi()){
//                NativeNavi.nativeMapDestroyGL(ANaviApplication.m_MapHandle);
//                NativeNavi.nativeDestroyMapHandle(ANaviApplication.m_MapHandle);
//                ANaviApplication.m_MapHandle = 0;
//            }
//        }

        m_FMInterface.FM_CancelRoute();

    }

    @Override
    public void mapReady() {
        switch(m_gApp.getRoutePathInfo().m_nServiceType) {
            case FatosBuildConfig.FATOS_SITE_IS_LGD:
                m_FMInterface.FM_UpdateLGDLayer();
                break;
        }



    }

    @Override
    public void updatePickerInfo(String s, int i, int i1) {

    }

    @Override
    public void updateMapTouch(float v, float v1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void updateCancelRoute() {
        updateCancelRouteLayout();
    }
}
