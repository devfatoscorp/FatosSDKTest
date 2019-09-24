package fatossdk.kr.fatos.fatossdktest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import biz.fatossdk.config.ErrorMessage;
import biz.fatossdk.config.FatosBuildConfig;
import biz.fatossdk.fminterface.FMBaseActivity;
import biz.fatossdk.fminterface.FMDriveInfo;
import biz.fatossdk.fminterface.FMError;
import biz.fatossdk.fminterface.FMInterface;
import biz.fatossdk.fminterface.FMRouteOption;
import biz.fatossdk.fminterface.FMRoutePos;
import biz.fatossdk.fminterface.FMSortOption;
import biz.fatossdk.fminterface.RouteSummaryData;
import biz.fatossdk.fminterface.RouteSummaryDataDetail;
import biz.fatossdk.navi.NativeNavi;
import biz.fatossdk.navi.RoutePosition;
import biz.fatossdk.newanavi.ANaviApplication;
import biz.fatossdk.newanavi.base.AMapBaseFragment;
import biz.fatossdk.newanavi.manager.AMapGoogleSearchUtil;
import biz.fatossdk.newanavi.splash.FatosToast;
import biz.fatossdk.openapi.common.POIItem;

/**
 * Created by kyungilwoo on 2016. 9. 7..
 */

public class FatosMapSDKSampleLandFragment extends AMapBaseFragment {
    public static final String TAG = "AMAP";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".AMapFirstTouchFragment";

    private ANaviApplication m_gApp;
    private Context m_Context = null;
    private Button m_test1Btn, m_test2Btn, m_test5Btn;
    private Button m_test3Btn, m_test4Btn, m_test6Btn;
    private Button m_test7Btn, m_test8Btn, m_test9Btn;
    private Button m_test10Btn, m_test11Btn, m_test12Btn, m_test13Btn, m_test14Btn;
    private Activity m_MainActivity;
    private TextView m_txtRgInfo;
    private EditText editText;

    private FMInterface m_FMInterface;
    private Handler m_RgInfoHandler = new Handler(Looper.getMainLooper());

    public FatosMapSDKSampleLandFragment() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.amap_test_land_btn, parent, false);
        m_FMInterface = FMInterface.GetInstance();
        m_Context = getActivity();

        m_gApp = (ANaviApplication) m_Context.getApplicationContext();

        m_txtRgInfo = (TextView) v.findViewById(R.id.rginfo_text_view);
        m_test1Btn = (Button) v.findViewById(R.id.pos_ok_btn);
        m_test1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //m_FMInterface.FM_SearchPOI(new HttpResultHandler(FatosMapSDKSampleFragment.this),"서울",false);
/**
                FMRoutePos startPos = new FMRoutePos();
                FMRoutePos endPos = new FMRoutePos();
                FMRouteOption routeOpt = new FMRouteOption();

                startPos.dLonX = 126.765561;
                startPos.dLatY = 37.807109;
                startPos.strPosName = "Gate A";
                startPos.strFloor = "1F";

                endPos.dLonX = 126.767723;
                endPos.dLatY =37.809287;
                endPos.strPosName = "모듈 지상";
                endPos.strFloor = "1F";

                routeOpt.carType = 1; // 1은 임시 값



                 * LGD 처럼 특수한 경우만 구별한다

                switch(m_gApp.getRoutePathInfo().m_nServiceType) {
                    case FatosBuildConfig.FATOS_SITE_IS_LGD:
                        m_FMInterface.FM_RouteVol2_LGD_Dev(startPos,endPos,routeOpt);
                        break;
                    default:
                        m_FMInterface.FM_RouteVol2_Hybrid(new HttpResultHandler(FatosMapSDKSampleFragment.this),startPos,endPos,routeOpt);
                        break;
                }
                 */
                /**
                 * 경로 취소
                 */
                m_FMInterface.FM_CancelRoute();
                ((FatosMapBaseSDKLandActivity) getActivity()).updateCancelRoute();

                //((FatosMapBaseSDKLandActivity) getActivity()).showTbtLayout(true);

            }
        });

        m_test2Btn = (Button) v.findViewById(R.id.pos_cancel_btn);
        m_test2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //m_FMInterface.FM_SearchPOI_Hybrid(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),"서울",false);
                //((FatosMapBaseSDKLandActivity) getActivity()).showTbtLayout(false);
                double dLonX = 126.767723;
                double dLatY =  37.809287;
                //m_FMInterface.FM_SetMapFireObjPosition(FMBaseActivity.onFatosMapListener,1,1,dLonX, dLatY,null);
                m_FMInterface.FM_SetMapPosition(FMBaseActivity.onFatosMapListener,0,dLonX,dLatY);
            }
        });

        /*

         */

        m_test3Btn = (Button) v.findViewById(R.id.search_local_btn);
        m_test3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NativeNavi.nativeIsRoute())
                {
                    ((FatosMapBaseSDKLandActivity) getActivity()).manualReRouteMenu();
                    FatosToast.ShowFatosYellow(getResources().getString(R.string.string_reroute_status));
                }
            }
        });

        m_test4Btn = (Button) v.findViewById(R.id.route_local_btn);
        m_test4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FMRoutePos endPos = new FMRoutePos();
                FMRouteOption routeOpt = new FMRouteOption();

                endPos.dLonX = 126.997219;
                endPos.dLatY = 37.2664398;
                endPos.strPosName = "모듈 지상";
                endPos.strFloor = "1F";

                routeOpt.carType = 1; // 1은 임시 값

                /**
                 * LGD 처럼 특수한 경우만 구별한다
                 */
                switch(m_gApp.getRoutePathInfo().m_nServiceType) {
                    case FatosBuildConfig.FATOS_SITE_IS_LGD:
                        m_FMInterface.FM_RouteVol2_LGD_Dev(null,endPos,routeOpt);
                        break;
                    default:
                        m_FMInterface.FM_RouteVol2_Hybrid(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),null,endPos,routeOpt);
                        break;
                }
            }
        });

        m_test5Btn = (Button) v.findViewById(R.id.get_driveinfo_btn);
        m_test5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FMDriveInfo driveinfo = m_FMInterface.FM_GetDriveInfo();
                double[] latlon = new double[2];

                m_FMInterface.FM_GetMapCenterPos(latlon);

                String strRgInfo;

                strRgInfo = "### speed : " + driveinfo.getM_iGpsSpeed()+ " \n" +
                        "### isroute : " + driveinfo.isM_bIsRoute()+ " \n" +
                        "### cur pos  : " + driveinfo.getM_strCurPosName()+ " \n" +
                        "### cur lonx : " + driveinfo.getM_fCurLonX()+ " \n" +
                        "### cur laty : " + driveinfo.getM_fCurLatY()+ " \n" +
//                        "### maptouch lonx : " + driveinfo.getM_fMapTouchLonX()+ " \n" +
//                        "### maptouch laty : " + driveinfo.getM_fMapTouchLatY()+ " \n" +
                        "### angle : " + driveinfo.getM_nCurAngle()+ " \n" +
                        "### gpsStatus : " + driveinfo.getM_nGpsStatus()+ " \n" +
                        "### mmStatus : " + driveinfo.getM_nMMStatus()+ " \n" +
                        "### 진입불가도로 : " + driveinfo.isM_bCannotEnterRoad()+ " \n" +
                        "### 진입불가도로 코드 : " + driveinfo.getM_bCannotEnterRoadCode()+ " \n" +
                        "### 게이트 주변 거리(m): : " + driveinfo.getM_nGateNearbyDist()+ " \n" +
                        "### 지하도로 :  " + driveinfo.getM_nFloor()+ " \n" +
                        "### 목적지 남은거리: : " + updateTotalRemainDist(driveinfo.getM_nTotalRemainderDist())+ " \n" +
                        "### 목적지 남은시간 :  " + updateTotalRemainTime(driveinfo.getM_nServiceLinkRemainderTime())+ " \n" +
                        "### 중심좌표 :  " + latlon[0] + ", " + latlon[1] + "거리 : " + driveinfo.getM_nTripDist() + "시간 : "+driveinfo.getM_nTripTime() + " \n" +
                        "### 경유지 수 :  " + driveinfo.getnViaTotalCount() +" \n";


                for(int i = 0 ; i < driveinfo.getnViaTotalCount() ; i++)
                {
                    strRgInfo += "### 경유지[" + i + "] 시간(초):" + driveinfo.getListViaRemainderTime()[i] +" \n";
                    strRgInfo += "### 경유지[" + i + "] 거리(미터):" + driveinfo.getListViaRemainderDist()[i] +" \n";
                }

                m_txtRgInfo.setText(strRgInfo);
                Log.e(TAG,"### DriveInfo :" + strRgInfo);

                m_FMInterface.FM_GetAddress(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),latlon[1], latlon[0]);
            }
        });

        m_test6Btn = (Button) v.findViewById(R.id.pos_recommentword_btn);
        m_test6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_FMInterface.FM_RecommendWord(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),"ㄱ");
            }
        });

        m_test7Btn = (Button) v.findViewById(R.id.test_btn_7);
        m_test7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RouteSummaryData> summaryData = m_FMInterface.FM_RouteSummary(FMBaseActivity.onFatosMapListener);
                Log.d(TAG,"## sum data : " + summaryData.size());
            }
        });

        m_test8Btn = (Button) v.findViewById(R.id.test_btn_8);
        m_test8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_FMInterface.FM_StartRGService(FMBaseActivity.onFatosMapListener);
                ((FMBaseActivity)getActivity()).mapMoveCurrnetPostion();
            }
        });

        m_test9Btn  = (Button) v.findViewById(R.id.test_btn_9);
        m_test9Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_FMInterface.FM_SelectRoute(16);
            }
        });

        m_test10Btn = (Button) v.findViewById(R.id.test_btn_10);
        m_test10Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RouteSummaryDataDetail> summaryData = m_FMInterface.FM_RouteSummaryDetail(0,0);
                Log.d(TAG,"## sum data : " + summaryData.size());

            }
        });

        m_test11Btn = (Button) v.findViewById(R.id.test_btn_11);
        m_test11Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartMain = new Intent(m_Context, FatosMapBaseSDKSubActivity.class);
                startActivity(intentStartMain);
            }
        });

        m_test12Btn = (Button) v.findViewById(R.id.test_btn_12);
        m_test12Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double dLonX = 127.877723;
                double dLatY =  35.419287;
                m_FMInterface.FM_SetMapPosition(FMBaseActivity.onFatosMapListener,0,dLonX,dLatY);

                //m_FMInterface.FM_MoveCurPosition(FMBaseActivity.onFatosMapListener);
            }
        });
        /**
         * 경유지 탐색 관련
         */
        m_test13Btn = (Button) v.findViewById(R.id.test_btn_13);
        m_test13Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 경유지 정보 좌표 List
                List<RoutePosition> positionList = m_FMInterface.FM_GetViaPOIList();
                positionList.clear();

                /**
                 * positionList의 출발지 정보 현위치 일 때는 x,y 좌표를 0으로 설정한다.
                 */
                RoutePosition positionList0 = new RoutePosition();
                positionList0.x = 0;
                positionList0.y = 0;
                positionList0.name = "출발지";
                positionList.add(positionList0);

                RoutePosition positionList1 = new RoutePosition();

                //,
                positionList1.x = 127.0691513;
                positionList1.y = 37.251683;
                positionList1.name = "이천시법원";
                positionList1.addr = "경기 이천시 안흥동";
                positionList1.bPassingPoint = false;

                positionList.add(positionList1);

                RoutePosition positionList2 = new RoutePosition();
                positionList2.x = 127.0445743;
                positionList2.y = 37.261629;
                positionList2.name = "이천시청";
                positionList2.addr = "경기 이천시 중리동";
                positionList2.bPassingPoint = false;

                positionList.add(positionList2);

                RoutePosition positionList3 = new RoutePosition();

                positionList3.x = 126.997219;
                positionList3.y = 37.2664398;
                positionList3.name = "이천종합버스터미널";
                positionList3.addr = "경기 이천시 안흥동";
                positionList2.bPassingPoint = true;

                positionList.add(positionList3);
//
//                RoutePosition positionList4 = new RoutePosition();
//                positionList4.x = 127.44687558;
//                positionList4.y = 37.27780168;
//                positionList4.name = "이천시법원";
//                positionList4.addr = "경기 이천시 중리동";
//                positionList4.bPassingPoint = false;
//
//                positionList.add(positionList4);
//
//                RoutePosition positionList5 = new RoutePosition();
//                positionList5.x = 127.02779627;
//                positionList5.y = 37.49721313;
//                positionList5.name = "강남역[2호선]";
//                positionList5.addr = "서울 강남구 역삼동";
//                positionList5.bPassingPoint = false;
//
//                positionList.add(positionList5);

                m_FMInterface.FM_RouteVol2_Via(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),positionList);
            }
        });

        m_test14Btn = (Button) v.findViewById(R.id.test_btn_14);
        m_test14Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                m_FMInterface.FM_SetMapAirMode(FMInterface.MAPMODE_AIR_ON_BUILDING_ON); // 항공모드 on 건물 on
//                m_FMInterface.FM_SetMapAirMode(FMInterface.MAPMODE_AIR_ON_BUILDING_OFF); // 항공모드 on 건물 off
//                m_FMInterface.FM_SetMapAirMode(FMInterface.MAPMODE_AIR_OFF_BUILDING_ON); // 항공모드 off 건물 on

                if (m_FMInterface.FM_GoNextGoal()) {
                    ((FatosMapBaseSDKLandActivity) getActivity()).manualReRouteMenu();
                    FatosToast.ShowFatosYellow(getResources().getString(R.string.string_reroute_status));
                }

            }
        });

        editText = (EditText)v.findViewById(R.id.sdk_poiname_edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().length() > 0 && FatosBuildConfig.getBuildSearchResultMode() == 1)
                {
                    final String strData = s.toString();
                    m_FMInterface.FM_RecommendWord(new HttpResultHandler(FatosMapSDKSampleLandFragment.this),strData);
                    /*
                    m_routeApi.autoComplete(strData, new Route.AutoCompleteListenerCallback() {
                        @Override
                        public void onAutoComplete(ArrayList<String> poiItem) {
                            if (poiItem == null) {
                                setHandlerMessage(MESSAGE_ERROR);
                                return;
                            }
                            if( poiItem.size() < 0)
                            {
                                setHandlerMessage(MESSAGE_ERROR);
                                return;
                            }
                            arDessert.clear();

                            Log.d("autoComplete", "poiItem.size() : " + poiItem.size());
                            for (int i = 0; i < poiItem.size(); i++) {
                                Log.d("autoComplete", "poiItem.size() : " + i + " : " + poiItem.get(i));
                                arDessert.add(poiItem.get(i));
                            }
                        }
                    });
                    setHandlerMessage(MESSAGE_STATE_POI_AUTO);
                    */
                }
                else
                {
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        m_RgInfoHandler.postDelayed(mRGInfoDisplay,1000);
        return v;
    }
    private final Runnable mRGInfoDisplay = new Runnable() {
        @Override
        public void run() {
            FMDriveInfo driveinfo = m_FMInterface.FM_GetDriveInfo();

            String strRgInfo;
            double[] latlon = new double[2];
            m_FMInterface.FM_GetMapCenterPos(latlon);
            strRgInfo = "### speed : " + driveinfo.getM_iGpsSpeed()+ " \n" +
                    "### isroute : " + driveinfo.isM_bIsRoute()+ " \n" +
                    "### cur pos  : " + driveinfo.getM_strCurPosName()+ " \n" +
                    "### cur lonx : " + driveinfo.getM_fCurLonX()+ " \n" +
                    "### cur laty : " + driveinfo.getM_fCurLatY()+ " \n" +
//                    "### maptouch lonx : " + driveinfo.getM_fMapTouchLonX()+ " \n" +
//                    "### maptouch laty : " + driveinfo.getM_fMapTouchLatY()+ " \n" +
                    "### angle : " + driveinfo.getM_nCurAngle()+ " \n" +
                    "### gpsStatus : " + driveinfo.getM_nGpsStatus()+ " \n" +
                    "### mmStatus : " + driveinfo.getM_nMMStatus()+ " \n" +
                    "### 진입불가도로 : " + driveinfo.isM_bCannotEnterRoad()+ " \n" +
                    "### 진입불가도로 코드 : " + driveinfo.getM_bCannotEnterRoadCode()+ " \n" +
                    "### 게이트 주변 거리(m): : " + driveinfo.getM_nGateNearbyDist()+ " \n" +
                    "### 지하도로 :  " + driveinfo.getM_nFloor()+ " \n" +
                    "### 목적지 남은거리: : " + updateTotalRemainDist(driveinfo.getM_nTotalRemainderDist())+ " \n" +
                    "### 목적지 남은시간 :  " + updateTotalRemainTime(driveinfo.getM_nServiceLinkRemainderTime())+ " \n" +
                    "### 중심좌표 :  " + latlon[0] + ", " + latlon[1] + "거리 : " + driveinfo.getM_nTripDist() + "시간 : "+driveinfo.getM_nTripTime() + " \n" +
                    "### 경유지 수 :  " + driveinfo.getnViaTotalCount() +" \n";

            for(int i = 0 ; i < driveinfo.getnViaTotalCount() ; i++)
            {
                strRgInfo += "### 경유지[" + i + "] 시간(초):" + driveinfo.getListViaRemainderTime()[i] +" \n";
                strRgInfo += "### 경유지[" + i + "] 거리(미터):" + driveinfo.getListViaRemainderDist()[i] +" \n";
            }


            m_txtRgInfo.setText(strRgInfo);

            m_RgInfoHandler.removeCallbacks(mRGInfoDisplay);
            m_RgInfoHandler.removeCallbacksAndMessages(null);
            m_RgInfoHandler.postDelayed(mRGInfoDisplay,1000);
        }
    };



    public String updateTotalRemainDist(int distance) {
        String strResult = "";
        if (distance >= 1000) {
            float km = (float) distance / 1000.f;

            if (km >= 100) {
                strResult = String.format("%d km", (int) km);
            } else if (km >= 10) {
                strResult = String.format("%.1f km", (float) distance / 1000);
            } else {
                strResult = String.format("%.1f km", (float) distance / 1000);
            }
        } else {
            if (distance <= 0) {
                distance = 0;
            }
            if (distance >= 10) {
                distance = distance - (distance % 10);
                strResult = String.format("%d m", distance);
            } else {
                strResult = String.format("%d m", distance);
            }
        }

        return strResult;
    }

    /**
     # m_gApp.getAppSettingInfo().m_bArriveTime
     m_bArriveTime = true : 도착 예정시간
     false: 남은 시간
     */
    public String updateTotalRemainTime(int secTime) {

        String strResult = "", strTemp;
        Calendar now = Calendar.getInstance();
//		Date curDate = now.getTime();
        if(secTime < 60)
            secTime = 60;

        if(m_gApp.getAppSettingInfo().m_bArriveTime)
        {
            Calendar tmp = (Calendar) now.clone();
            tmp.add(Calendar.SECOND, secTime);
            Calendar arriveCal = tmp;

            DateFormat outputFormat = new SimpleDateFormat("hh:mm a",new Locale("en", "us"));
            strTemp = outputFormat.format(arriveCal.getTime());
            strResult = strTemp.replace("AM", "am").replace("PM","pm");
            //Log.i("WOO", formattedTime);
        }
        else
        {
            int minutes = 0;
            minutes = (int) (secTime / 60);
            if (60 > minutes) {
                strResult = String.format("%02dmin", minutes);
            } else {
                int hour = (int) (minutes / 60);
                int extraMin = minutes % 60;
                if (extraMin == 0) {
                    strResult = String.format("%02dhour", hour);
                } else {
                    strResult = String.format("%02dh %02dm", hour, extraMin);
                }
            }
        }

        return strResult;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    static class HttpResultHandler extends Handler {
        private final WeakReference<FatosMapSDKSampleLandFragment> mActivity;

        HttpResultHandler(FatosMapSDKSampleLandFragment activity) {
            mActivity = new WeakReference<FatosMapSDKSampleLandFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FatosMapSDKSampleLandFragment activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        String result = msg.getData().getString(AMapGoogleSearchUtil.RESULT);
        ArrayList<String> searchList = new ArrayList<String>();

        if (result.equals(FMError.FME_SUCCESS_SEARCH_SUCCESS)) {
            ArrayList<POIItem> searchResultPOI = m_FMInterface.FM_GetSearchResult(FMSortOption.FM_SORT_BY_ACCURACY);

//            for(int i = 0; i < searchResultPOI.size(); i++)
//            {
//                Log.e(TAG,"### " + i + " : " + searchResultPOI.get(i).upperAddrName + " " + searchResultPOI.get(i).middleAddrName + " " + searchResultPOI.get(i).lowerAddrName + " " + searchResultPOI.get(i).detailAddrName);
//            }
            if(searchResultPOI != null)
                FatosToast.ShowFatosYellow("FME_SUCCESS_SEARCH_SUCCESS Size : " + searchResultPOI.size());
            else
                FatosToast.ShowFatosYellow("searchResultPOI is null");
        }
        else if(result.equals(FMError.FME_MESSAGE_STATE_POI_AUTO))
        {
            ArrayList<String> arDessert = m_FMInterface.FM_GetRecommentWordResult();
            if(arDessert != null)
                FatosToast.ShowFatosYellow("FME_MESSAGE_STATE_POI_AUTO Size : " + arDessert.size());
            else
                FatosToast.ShowFatosYellow("arDessert is null");
        }
        else if(result.equals(FMError.FME_MESSAGE_SEARCH_ERROR))
        {
            FatosToast.ShowFatosYellow("FME_MESSAGE_SEARCH_ERROR");
        }
        else if(result.equals(FMError.FME_SUCCESS_ROUTE_SUCCESS))
        {
            ((FMBaseActivity)getActivity()).mapMoveCurrnetPostion();
            boolean bLocal = msg.getData().getBoolean(ErrorMessage.COMM_TYPE);
            FatosToast.ShowFatosYellow("FME_SUCCESS_ROUTE_SUCCESS : Local(" + bLocal+")");
        }
        else if(result.equals(FMError.FME_ERROR_ROUTE))
        {
            boolean bLocal = msg.getData().getBoolean(ErrorMessage.COMM_TYPE);
            FatosToast.ShowFatosYellow("FME_ERROR_ROUTE : Local(" + bLocal+")");
        }
        else if(result.equals(FMError.FME_MESSAGE_FIX_SEARCH_SUCCESS))
        {
            String strAddr = msg.getData().getString(ErrorMessage.ADDR_SEARCH_SUCCESS_RESULT);
            FatosToast.ShowFatosYellow(strAddr);
        }
        else{
            FatosToast.ShowFatosYellow(result);
        }
    }


    @Override
    public void onDestroy() {
        m_RgInfoHandler.removeCallbacks(mRGInfoDisplay);
        m_RgInfoHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
