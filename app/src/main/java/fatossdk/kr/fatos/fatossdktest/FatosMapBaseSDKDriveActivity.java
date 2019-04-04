package fatossdk.kr.fatos.fatossdktest;

import android.os.Bundle;

import biz.fatossdk.fminterface.FMBaseActivity;
import biz.fatossdk.fminterface.FMInterface;
import biz.fatossdk.fminterface.FMRouteOption;
import biz.fatossdk.fminterface.FMRoutePos;
import biz.fatossdk.fminterface.drivefragment.AMapSDKRouteDriveInfoFragment;

/**
 * Created by kyungilwoo on 2017. 6. 20..
 */

public class FatosMapBaseSDKDriveActivity extends FMBaseActivity implements FMBaseActivity.OnFatosMapBaseActivityListener{

    private FMInterface m_FMInterface;
    private AMapSDKRouteDriveInfoFragment m_DriveRouteInfoFragment = new AMapSDKRouteDriveInfoFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatos_map_base_drive_sdk);
        setOnFatosMapBaseActivityListener(this);
        m_FMInterface = FMInterface.GetInstance();

        if( m_DriveRouteInfoFragment.isVisible() == false &&
                m_DriveRouteInfoFragment.isAdded() == false)
        {
            addAllowingStateLossFragment(android.R.id.content,
                    m_DriveRouteInfoFragment,
                    AMapSDKRouteDriveInfoFragment.FRAGMENT_TAG);
        }


        FMRoutePos startPos = new FMRoutePos();
        FMRoutePos endPos = new FMRoutePos();
        FMRouteOption routeOpt = new FMRouteOption();
//
//                startPos.dLonX = 126.91964;
//                startPos.dLatY = 37.524322;
//                startPos.strPosName = "여의도 눈썰매장(여의아이스파크)";
//                startPos.strAddr = "여의도";

        endPos.dLonX = 126.988255;
        endPos.dLatY = 37.551222;
        endPos.strPosName = "N서울타워";
        endPos.strFloor = "1F";

        m_FMInterface.FM_RouteVol2_Center(null,endPos,routeOpt);

    }

    @Override
    public void mapReady() {

    }

    @Override
    public void updatePickerInfo(String s, int i, int i1) {

    }

    @Override
    public void updateMapTouch(float v, float v1) {

    }
}
