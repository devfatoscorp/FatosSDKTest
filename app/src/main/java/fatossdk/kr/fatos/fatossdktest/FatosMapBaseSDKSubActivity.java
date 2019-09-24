package fatossdk.kr.fatos.fatossdktest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import biz.fatossdk.config.ErrorMessage;
import biz.fatossdk.fminterface.FMActivity;
import biz.fatossdk.fminterface.FMError;
import biz.fatossdk.fminterface.FMInterface;
import biz.fatossdk.newanavi.ANaviApplication;
import biz.fatossdk.newanavi.manager.AMapGoogleSearchUtil;

public class FatosMapBaseSDKSubActivity extends FMActivity{
    private ANaviApplication m_gApp;
    private Context m_Context = null;
    private FMInterface m_FMInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_Context = this;
        m_gApp = (ANaviApplication) m_Context.getApplicationContext();
        m_FMInterface = FMInterface.GetInstance();
        setContentView(R.layout.activity_fatos_submap_base_sdk);

        m_FMInterface.FM_SetRouteDriveListener(new ANaviApplication.MapStatusListener() {
            @Override
            public void onRouteFinish() {

            }

            @Override
            public void onMapMove(boolean bMapMove) {
                Log.e(TAG,"### FatosMapBaseSDKSubActivity onMapMove");
                double[] latlon = new double[2];

                m_FMInterface.FM_GetSubMapCenterPos(latlon);
                m_FMInterface.FM_GetAddress(new HttpResultHandler(FatosMapBaseSDKSubActivity.this),latlon[1], latlon[0]);

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
            public void updateObjPickerInfo(int nType, String strKey, String strName, double nLong, double nLat) {

            }

            @Override
            public void updateMapTouch(float fX, float fY) {

            }

            @Override
            public void updateMapLongTouch(float fX, float fY) {

            }

            @Override
            public void updateMapAngle(float nAngle) {

            }
        });
    }
    static class HttpResultHandler extends Handler {
        private final WeakReference<FatosMapBaseSDKSubActivity> mActivity;

        HttpResultHandler(FatosMapBaseSDKSubActivity activity) {
            mActivity = new WeakReference<FatosMapBaseSDKSubActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FatosMapBaseSDKSubActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }


    private void handleMessage(Message msg) {
        String result = msg.getData().getString(AMapGoogleSearchUtil.RESULT);
        ArrayList<String> searchList = new ArrayList<String>();

        if(result.equals(FMError.FME_MESSAGE_FIX_SEARCH_SUCCESS))
        {
            String strAddr = msg.getData().getString(ErrorMessage.ADDR_SEARCH_SUCCESS_RESULT);

            Toast.makeText(m_Context,strAddr,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(m_Context,result,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
