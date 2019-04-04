package fatossdk.kr.fatos.fatossdktest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

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
import biz.fatossdk.fminterface.FMBaseActivity.OnFatosMapBaseActivityListener;
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
import biz.fatossdk.newanavi.manager.AMapGoogleSearchUtil;
import biz.fatossdk.newanavi.splash.FatosToast;
import biz.fatossdk.openapi.common.POIItem;

public class FatosMapBaseSDKActivity extends FMBaseActivity implements OnFatosMapBaseActivityListener {
    private ANaviApplication m_gApp;
    private Context m_Context = null;
    private Button m_test1Btn, m_test2Btn;
    private Activity m_MainActivity;
    // Used to load the 'native-lib' library on application startup.

    private FMInterface m_FMInterface;
    private FatosMapSDKSampleFragment m_SDKSampleFragment = new FatosMapSDKSampleFragment();
    private int m_iEngineInit = 0;

    // Serial key
    private String API_KEY = "5e6c686753643a73f364286b8f362d15";
    private SweetSheet mSweetSheetforMap;
    private SweetSheet mSweetSheetforRoute;
    private SweetSheet mSweetSheetforSearch;
    private SweetSheet mSweetSheetforEtc;
    private SweetSheet mSearchResSweetSheet;
    private RelativeLayout rl;
    private TextView m_txtRgInfo;
    private EditText editText;

    private ProgressDialog m_AndroidProgressDlg;
    private ArrayList<POIItem> poiSearchList= new ArrayList<POIItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_MainActivity = this;
        m_Context = this;
        m_gApp = (ANaviApplication) m_Context.getApplicationContext();
        m_FMInterface = FMInterface.GetInstance();
        setContentView(R.layout.activity_fatos_map_base_sdk);
        // 설정 값 적용
        //NativeNavi.nativeMapSetCarvata(m_MapHandle,m_gApp.getAppSettingInfo().m_nCarIcon);
        // 주야간 모드 적용
        //NativeNavi.nativeMapSetNightMode(m_gApp.getAppSettingInfo().m_nDayNightMode);
        NativeNavi.nativeMapSetNightMode(0);

//        NativeNavi.nativeSetRoutePath(m_gApp.getRoutePathInfo().m_nServiceType);
//        NativeNavi.nativeSetSite(m_gApp.getRoutePathInfo().m_nServiceType);
        NativeNavi.nativeSetMapMode(false);

        NativeNavi.nativeMapRefresh();

//        if( m_SDKSampleFragment.isVisible() == false &&
//                m_SDKSampleFragment.isAdded() == false)
//        {
//            addAllowingStateLossFragment(android.R.id.content,
//                    m_SDKSampleFragment,
//                    FatosMapSDKSampleFragment.FRAGMENT_TAG);
//        }

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

        rl = (RelativeLayout) findViewById(R.id.rl);

        setupRecyclerViewforMap();
        setupRecyclerViewforRoute();
        setupRecyclerViewforSearch();
        setupRecyclerViewforEtc();
        final ImageButton menuButtonX = (ImageButton) findViewById(R.id.button_menu);
        menuButtonX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(IsShowMenu()){
                    if(mSweetSheetforMap.isShow())
                        mSweetSheetforMap.dismiss();
                    if(mSweetSheetforRoute.isShow())
                        mSweetSheetforRoute.dismiss();
                    if(mSweetSheetforSearch.isShow())
                        mSweetSheetforSearch.dismiss();
                    if(mSweetSheetforEtc.isShow())
                        mSweetSheetforEtc.dismiss();

                    //menuButtonX.setImageResource(R.mipmap.way_btn_cancel_n);
                }
                else{
                    //menuButtonX.setImageResource(R.mipmap.ic_fatos);
                }

            }
        });
        Button menuButtonforMap = (Button) findViewById(R.id.fatos_button_1);
        menuButtonforMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mSweetSheetforMap.toggle();
                if(mSweetSheetforRoute.isShow())
                    mSweetSheetforRoute.dismiss();
                if(mSweetSheetforSearch.isShow())
                    mSweetSheetforSearch.dismiss();
                if(mSweetSheetforEtc.isShow())
                    mSweetSheetforEtc.dismiss();

//                if(IsShowMenu())
//                    menuButtonX.setImageResource(R.mipmap.way_btn_cancel_n);
//                else
//                    menuButtonX.setImageResource(R.mipmap.ic_fatos);
            }
        });

        Button menuButtonforRoute = (Button) findViewById(R.id.fatos_button_2);
        menuButtonforRoute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSweetSheetforRoute.toggle();
                if(mSweetSheetforMap.isShow())
                    mSweetSheetforMap.dismiss();
                if(mSweetSheetforSearch.isShow())
                    mSweetSheetforSearch.dismiss();
                if(mSweetSheetforEtc.isShow())
                    mSweetSheetforEtc.dismiss();

//                if(IsShowMenu())
//                    menuButtonX.setImageResource(R.mipmap.way_btn_cancel_n);
//                else
//                    menuButtonX.setImageResource(R.mipmap.ic_fatos);
            }
        });


        Button menuButtonforSearch = (Button) findViewById(R.id.fatos_button_3);
        menuButtonforSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mSweetSheetforSearch.toggle();
                if(mSweetSheetforMap.isShow())
                    mSweetSheetforMap.dismiss();
                if(mSweetSheetforRoute.isShow())
                    mSweetSheetforRoute.dismiss();
                if(mSweetSheetforEtc.isShow())
                    mSweetSheetforEtc.dismiss();


//                if(IsShowMenu())
//                    menuButtonX.setImageResource(R.mipmap.way_btn_cancel_n);
//                else
//                    menuButtonX.setImageResource(R.mipmap.ic_fatos);
            }
        });


        Button menuButtonforEtc = (Button) findViewById(R.id.fatos_button_4);
        menuButtonforEtc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mSweetSheetforEtc.toggle();
                if(mSweetSheetforMap.isShow())
                    mSweetSheetforMap.dismiss();
                if(mSweetSheetforRoute.isShow())
                    mSweetSheetforRoute.dismiss();
                if(mSweetSheetforSearch.isShow())
                    mSweetSheetforSearch.dismiss();

//                if(IsShowMenu())
//                    menuButtonX.setImageResource(R.mipmap.way_btn_cancel_n);
//                else
//                    menuButtonX.setImageResource(R.mipmap.ic_fatos);
            }
        });



        editText = (EditText)findViewById(R.id.sdk_poiname_edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().length() > 0)
                {
                    final String strData = s.toString();
                    m_FMInterface.FM_RecommendWord(new HttpResultHandler(FatosMapBaseSDKActivity.this),strData);

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

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_SEND:
                        if(editText.getText().toString().length()<1)
                        {
                            hideKeyBoard(editText);
                            return false;
                        }
                        m_AndroidProgressDlg = ProgressDialog.show(m_Context, "Fire SDK", "목적지 검색 중...",
                                true, false);

                        m_FMInterface.FM_SearchPOI_Hybrid(new HttpResultHandler(FatosMapBaseSDKActivity.this),editText.getText().toString(),false);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        m_txtRgInfo = (TextView) findViewById(R.id.rginfo_text_view);
        m_RgInfoHandler.postDelayed(mRGInfoDisplay,1000);

    }

    private boolean IsShowMenu(){
        boolean bShow = false;
        if(mSweetSheetforEtc.isShow())
            bShow = true;
        if(mSweetSheetforMap.isShow())
            bShow = true;
        if(mSweetSheetforRoute.isShow())
            bShow = true;
        if(mSweetSheetforSearch.isShow())
            bShow = true;

        return bShow;
    }

    public void hideKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)getSystemService(m_Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setOnFatosMapBaseActivityListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    private void setupRecyclerViewforMap() {
        final ArrayList<MenuEntity> list = new ArrayList<>();

        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.curpos_2_5_2;
        menuEntity1.title = "Map move";

        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.mipmap.curpos_2_5_2;
        menuEntity2.title = "Sub Map view";


//        MenuEntity menuEntity3 = new MenuEntity();
//        menuEntity3.iconId = R.mipmap.curpos_2_5_2;
//        menuEntity3.title = "전신주 보기";
//
//        MenuEntity menuEntity4 = new MenuEntity();
//        menuEntity4.iconId = R.mipmap.curpos_2_5_2;
//        menuEntity4.title = "위험지역 보기(사용자 폴리곤)";


        list.add(menuEntity1);
        list.add(menuEntity2);
//        list.add(menuEntity3);
//        list.add(menuEntity4);

        mSweetSheetforMap = new SweetSheet(rl);

        mSweetSheetforMap.setMenuList(list);
        mSweetSheetforMap.setDelegate(new RecyclerViewDelegate(true).setContentHeight(800));
        mSweetSheetforMap.setBackgroundEffect(new BlurEffect(8));
        mSweetSheetforMap.setBackgroundClickEnable(false);

        mSweetSheetforMap.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                if(position < 0)
                    return false;
                double dLonX = 127.877723;
                double dLatY = 35.419287;
                switch (position) {
                    case 0: // 재난 위치 보기
                        dLonX = 126.767723;
                        dLatY =  37.809287;
                        m_FMInterface.FM_SetMapPosition(FMBaseActivity.onFatosMapListener,0,dLonX,dLatY);
                        break;
                    case 1: // Sub Map 보기
                        Intent intentStartMain = new Intent(m_Context, FatosMapBaseSDKSubActivity.class);
                        startActivity(intentStartMain);
                        break;
                    case 2: //
                    {
                        dLonX = 127.877723;
                        dLatY = 35.419287;
                        m_FMInterface.FM_SetMapPosition(FMBaseActivity.onFatosMapListener,0, dLonX, dLatY);
                    }
                    break;
                    case 3: //
                    {
                        dLonX = 126.9999831;
                        dLatY = 37.5676009;
                        m_FMInterface.FM_SetMapPosition(FMBaseActivity.onFatosMapListener,0, dLonX, dLatY);
                    }
                    break;
                }
                if(mSweetSheetforMap.isShow())
                {
                    mSweetSheetforMap.dismiss();
                }
                return false;
            }
        });
    }

    private void setupRecyclerViewforRoute() {
        final ArrayList<MenuEntity> list = new ArrayList<>();

        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.curpos_2_5_2;
        menuEntity1.title = "Route Via";

        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.mipmap.curpos_2_5_2;
        menuEntity2.title = "Route Cancel";

        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.iconId = R.mipmap.curpos_2_5_2;
        menuEntity3.title = "Get DriveInfo";

        MenuEntity menuEntity4 = new MenuEntity();
        menuEntity4.iconId = R.mipmap.curpos_2_5_2;
        menuEntity4.title = "Route";

        MenuEntity menuEntity5 = new MenuEntity();
        menuEntity5.iconId = R.mipmap.curpos_2_5_2;
        menuEntity5.title = "ReRoute";

        MenuEntity menuEntity7 = new MenuEntity();
        menuEntity7.iconId = R.mipmap.curpos_2_5_2;
        menuEntity7.title = "Get Route Summary";

        MenuEntity menuEntity8 = new MenuEntity();
        menuEntity8.iconId = R.mipmap.curpos_2_5_2;
        menuEntity8.title = "Start RouteGudiance";

        MenuEntity menuEntity9 = new MenuEntity();
        menuEntity9.iconId = R.mipmap.curpos_2_5_2;
        menuEntity9.title = "Select Route";

        MenuEntity menuEntity10 = new MenuEntity();
        menuEntity10.iconId = R.mipmap.curpos_2_5_2;
        menuEntity10.title = "Route Summary Detail";


        list.add(menuEntity1);
        list.add(menuEntity2);
        list.add(menuEntity3);
        list.add(menuEntity4);
        list.add(menuEntity5);
        list.add(menuEntity7);
        list.add(menuEntity8);
        list.add(menuEntity9);
        list.add(menuEntity10);

        mSweetSheetforRoute = new SweetSheet(rl);

        mSweetSheetforRoute.setMenuList(list);
        mSweetSheetforRoute.setDelegate(new RecyclerViewDelegate(true).setContentHeight(800));
        mSweetSheetforRoute.setBackgroundEffect(new BlurEffect(8));
        mSweetSheetforRoute.setBackgroundClickEnable(false);

        mSweetSheetforRoute.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                if(position < 0)
                    return false;

                switch (position) {
                    case 0: // 경로 탐색 - (경유지 고정)
                        m_AndroidProgressDlg = ProgressDialog.show(m_Context, "Fire SDK", "경로 탐색 중...",
                                true, false);
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

                        positionList1.x = 127.46534606;
                        positionList1.y = 37.28005176;
                        positionList1.name = "이천시법원";
                        positionList1.addr = "경기 이천시 안흥동";
                        positionList1.bPassingPoint = false;

                        positionList.add(positionList1);

                        RoutePosition positionList2 = new RoutePosition();
                        positionList2.x = 127.43412687;
                        positionList2.y = 37.27288535;
                        positionList2.name = "이천시청";
                        positionList2.addr = "경기 이천시 중리동";
                        positionList2.bPassingPoint = false;

                        positionList.add(positionList2);

                        RoutePosition positionList3 = new RoutePosition();
                        positionList3.x = 127.46534606;
                        positionList3.y = 37.28005176;
                        positionList3.name = "이천종합버스터미널";
                        positionList3.addr = "경기 이천시 안흥동";
                        positionList2.bPassingPoint = true;

                        positionList.add(positionList3);

                        RoutePosition positionList4 = new RoutePosition();
                        positionList4.x = 127.44687558;
                        positionList4.y = 37.27780168;
                        positionList4.name = "이천시법원";
                        positionList4.addr = "경기 이천시 중리동";
                        positionList4.bPassingPoint = false;

                        positionList.add(positionList4);

                        RoutePosition positionList5 = new RoutePosition();
                        positionList5.x = 127.02779627;
                        positionList5.y = 37.49721313;
                        positionList5.name = "강남역[2호선]";
                        positionList5.addr = "서울 강남구 역삼동";
                        positionList5.bPassingPoint = false;

                        positionList.add(positionList5);

                        m_FMInterface.FM_RouteVol2_Via(new HttpResultHandler(FatosMapBaseSDKActivity.this),positionList);
                        break;
                    case 1: // 경로취소
                        m_FMInterface.FM_CancelRoute();
                        updateCancelRoute();
                        break;
                    case 2: // 주행 정보 가져오기
                        GetDriveInfo_Sample();
                        break;
                    case 3: // 경로탐색 - (GPS 기준)
                        m_AndroidProgressDlg = ProgressDialog.show(m_Context, "Fire SDK", "경로 탐색 중...",
                                true, false);

                        FMRoutePos endPos = new FMRoutePos();
                        FMRouteOption routeOpt = new FMRouteOption();

                        endPos.dLonX = 126.767723;
                        endPos.dLatY =37.809287;
                        endPos.strPosName = "모듈 지상";
                        endPos.strFloor = "1F";

                        routeOpt.carType = 1; // 1은 임시 값

                        m_FMInterface.FM_RouteVol2_Hybrid(new HttpResultHandler(FatosMapBaseSDKActivity.this),null,endPos,routeOpt);

                        break;
                    case 4: // 수동 재탐색
                        manualReRouteMenu();
                        FatosToast.ShowFatosYellow(getResources().getString(R.string.string_reroute_status));
                        break;
                    case 5: // 경로요약
                        ArrayList<RouteSummaryData> summaryData = m_FMInterface.FM_RouteSummary(FMBaseActivity.onFatosMapListener);
                        break;
                    case 6: // 경로안내시작
                        m_FMInterface.FM_StartRGService(FMBaseActivity.onFatosMapListener);
                        mapMoveCurrnetPostion();
                        break;
                    case 7: //경로선택
                        m_FMInterface.FM_SelectRoute(FMInterface.ROUTE_OPTION1);
                        break;
                    case 8: // 경로 정보 가져오기
                        ArrayList<RouteSummaryDataDetail> summaryDatas = m_FMInterface.FM_RouteSummaryDetail(0,0);
                        break;

                }
                if(mSweetSheetforRoute.isShow())
                {
                    mSweetSheetforRoute.dismiss();
                }
                return false;
            }
        });
    }

    private void setupRecyclerViewforSearch() {
        final ArrayList<MenuEntity> list = new ArrayList<>();

        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.curpos_2_5_2;
        menuEntity1.title = "Recommended words";

//        MenuEntity menuEntity2 = new MenuEntity();
//        menuEntity2.iconId = R.mipmap.curpos_2_5_2;
//        menuEntity2.title = "전신주 검색";
//
//        MenuEntity menuEntity3 = new MenuEntity();
//        menuEntity3.iconId = R.mipmap.curpos_2_5_2;
//        menuEntity3.title = "주소 검색(경상도)";


        list.add(menuEntity1);
//        list.add(menuEntity2);
//        list.add(menuEntity3);

        mSweetSheetforSearch = new SweetSheet(rl);

        mSweetSheetforSearch.setMenuList(list);
        mSweetSheetforSearch.setDelegate(new RecyclerViewDelegate(true).setContentHeight(800));
        mSweetSheetforSearch.setBackgroundEffect(new BlurEffect(8));
        mSweetSheetforSearch.setBackgroundClickEnable(false);

        mSweetSheetforSearch.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                if(position < 0)
                    return false;

                switch (position) {
                    case 0: // 추천어 가져오기
                        m_FMInterface.FM_RecommendWord(new HttpResultHandler(FatosMapBaseSDKActivity.this),"ㄱ");
                        break;
                    case 1: // 전신주 검색

                        break;
                    case 2: // 주소 검색

                        break;
                }
                if(mSweetSheetforSearch.isShow())
                {
                    mSweetSheetforSearch.dismiss();
                }
                return false;
            }
        });
    }
    private void setupRecyclerViewforEtc() {
        final ArrayList<MenuEntity> list = new ArrayList<>();

        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.mipmap.curpos_2_5_2;
        menuEntity1.title = "TBT Show";

        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.mipmap.curpos_2_5_2;
        menuEntity2.title = "TBT Hide";


        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.iconId = R.mipmap.curpos_2_5_2;
        menuEntity3.title = "Next via";

        list.add(menuEntity1);
        list.add(menuEntity2);
        list.add(menuEntity3);


        mSweetSheetforEtc = new SweetSheet(rl);

        mSweetSheetforEtc.setMenuList(list);
        mSweetSheetforEtc.setDelegate(new RecyclerViewDelegate(true).setContentHeight(800));
        mSweetSheetforEtc.setBackgroundEffect(new BlurEffect(8));
        mSweetSheetforEtc.setBackgroundClickEnable(false);

        mSweetSheetforEtc.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                if(position < 0)
                    return false;

                switch (position) {
                    case 0: // TBT 정보 Show
                        showTbtLayout(true);
                        break;
                    case 1: // TBT 정보 Hide
                        showTbtLayout(false);
                        break;
                    case 2: // 다음경유지
                    {
                        if (m_FMInterface.FM_GoNextGoal()) {
                            manualReRouteMenu();
                            FatosToast.ShowFatosYellow(getResources().getString(R.string.string_reroute_status));
                        }
                    }
                    break;

                }
                if(mSweetSheetforEtc.isShow())
                {
                    mSweetSheetforEtc.dismiss();
                }
                return false;
            }
        });

    }


    private void GetDriveInfo_Sample(){
        FMDriveInfo driveinfo = m_FMInterface.FM_GetDriveInfo();
        double[] latlon = new double[2];

        m_FMInterface.FM_GetMapCenterPos(latlon);

        String strRgInfo;

        strRgInfo = "### speed : " + driveinfo.getM_iGpsSpeed()+ " \n" +
                "### isroute : " + driveinfo.isM_bIsRoute()+ " \n" +
                "### cur pos  : " + driveinfo.getM_strCurPosName()+ " \n" +
                "### cur lonx : " + driveinfo.getM_fCurLonX()+ " \n" +
                "### cur laty : " + driveinfo.getM_fCurLatY()+ " \n" +
                "### maptouch lonx : " + driveinfo.getM_fMapTouchLonX()+ " \n" +
                "### maptouch laty : " + driveinfo.getM_fMapTouchLatY()+ " \n" +
                "### angle : " + driveinfo.getM_nCurAngle()+ " \n" +
                "### gpsStatus : " + driveinfo.getM_nGpsStatus()+ " \n" +
                "### mmStatus : " + driveinfo.getM_nMMStatus()+ " \n" +
                "### 지하도로 :  " + driveinfo.getM_nFloor()+ " \n" +
                "### 목적지 남은거리: : " + updateTotalRemainDist(driveinfo.getM_nTotalRemainderDist())+ " \n" +
                "### 목적지 남은시간 :  " + updateTotalRemainTime(driveinfo.getM_nServiceLinkRemainderTime())+ " \n" +
                "### 중심좌표 :  " + latlon[0] + ", " + latlon[1] + " \n" +
                "### 경유지 수 :  " + driveinfo.getnViaTotalCount() +" \n";


        for(int i = 0 ; i < driveinfo.getnViaTotalCount() ; i++)
        {
            strRgInfo += "### 경유지[" + i + "] 시간(초):" + driveinfo.getListViaRemainderTime()[i] +" \n";
            strRgInfo += "### 경유지[" + i + "] 거리(미터):" + driveinfo.getListViaRemainderDist()[i] +" \n";
        }

        m_txtRgInfo.setText(strRgInfo);
        Log.e(TAG,"### DriveInfo :" + strRgInfo);

        m_FMInterface.FM_GetAddress(new HttpResultHandler(FatosMapBaseSDKActivity.this),latlon[1], latlon[0]);
    }




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


    static class HttpResultHandler extends Handler {
        private final WeakReference<FatosMapBaseSDKActivity> mActivity;

        HttpResultHandler(FatosMapBaseSDKActivity activity) {
            mActivity = new WeakReference<FatosMapBaseSDKActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FatosMapBaseSDKActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    private ArrayList<POIItem> searchResultPOI;
    private void handleMessage(Message msg) {
        String result = msg.getData().getString(AMapGoogleSearchUtil.RESULT);
        ArrayList<String> searchList = new ArrayList<String>();

        if (result.equals(FMError.FME_SUCCESS_SEARCH_SUCCESS)) {

            if (m_AndroidProgressDlg != null && m_AndroidProgressDlg.isShowing())
                m_AndroidProgressDlg.dismiss();

            searchResultPOI = m_FMInterface.FM_GetSearchResult(FMSortOption.FM_SORT_BY_ACCURACY);

//            for(int i = 0; i < searchResultPOI.size(); i++)
//            {
//                Log.e(TAG,"### " + i + " : " + searchResultPOI.get(i).upperAddrName + " " + searchResultPOI.get(i).middleAddrName + " " + searchResultPOI.get(i).lowerAddrName + " " + searchResultPOI.get(i).detailAddrName);
//            }
            if(searchResultPOI != null)
                FatosToast.ShowFatosYellow("FME_SUCCESS_SEARCH_SUCCESS Size : " + searchResultPOI.size());
            else
                FatosToast.ShowFatosYellow("searchResultPOI is null");

            if(searchResultPOI.size() > 0)
            {
                final ArrayList<MenuEntity> list = new ArrayList<>();
                for(int i = 1 ; i < searchResultPOI.size(); i++)
                {
                    MenuEntity menuEntity1 = new MenuEntity();
                    menuEntity1.iconId = R.drawable.pin_goal;
                    menuEntity1.title = searchResultPOI.get(i).getPOIName() +"("+ searchResultPOI.get(i).getPOIAddress() + ")";

                    list.add(menuEntity1);
                }
                mSearchResSweetSheet = new SweetSheet(rl);

                mSearchResSweetSheet.setMenuList(list);
                mSearchResSweetSheet.setDelegate(new RecyclerViewDelegate(true));
                mSearchResSweetSheet.setBackgroundEffect(new BlurEffect(8));
                mSearchResSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                    @Override
                    public boolean onItemClick(int position, MenuEntity menuEntity1) {
                        //list.get(position).titleColor = 0xff5823ff;
                        //((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                        //Toast.makeText(FatosMapBaseSDKActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();

                        m_AndroidProgressDlg = ProgressDialog.show(m_Context, "Fire SDK", "경로 탐색 중...",
                                true, false);


                        FMRoutePos endPos = new FMRoutePos();
                        FMRouteOption routeOpt = new FMRouteOption();

                        endPos.dLonX = searchResultPOI.get(position+1).getFrontLongitudeX();
                        endPos.dLatY =searchResultPOI.get(position+1).getFrontLatitudeY();
                        endPos.strPosName = searchResultPOI.get(position+1).getPOIName();
                        endPos.strFloor = "1F";

                        routeOpt.carType = 1; // 1은 임시 값

                        m_FMInterface.FM_RouteVol2_Hybrid(new HttpResultHandler(FatosMapBaseSDKActivity.this),null,endPos,routeOpt);

                        if(mSearchResSweetSheet.isShow())
                        {
                            mSearchResSweetSheet.dismiss();
                        }
                        return false;
                    }
                });

                mSearchResSweetSheet.toggle();
            }

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
            mapMoveCurrnetPostion();
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

        if (m_AndroidProgressDlg != null && m_AndroidProgressDlg.isShowing())
            m_AndroidProgressDlg.dismiss();
    }
    private Handler m_RgInfoHandler = new Handler(Looper.getMainLooper());
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
                    "### maptouch lonx : " + driveinfo.getM_fMapTouchLonX()+ " \n" +
                    "### maptouch laty : " + driveinfo.getM_fMapTouchLatY()+ " \n" +
                    "### angle : " + driveinfo.getM_nCurAngle()+ " \n" +
                    "### gpsStatus : " + driveinfo.getM_nGpsStatus()+ " \n" +
                    "### mmStatus : " + driveinfo.getM_nMMStatus()+ " \n" +
                    "### 진입불가도로 : " + driveinfo.isM_bCannotEnterRoad()+ " \n" +
                    "### 진입불가도로 코드 : " + driveinfo.getM_bCannotEnterRoadCode()+ " \n" +
                    "### 게이트 주변 거리(m): : " + driveinfo.getM_nGateNearbyDist()+ " \n" +
                    "### 지하도로 :  " + driveinfo.getM_nFloor()+ " \n" +
                    "### 목적지 남은거리: : " + updateTotalRemainDist(driveinfo.getM_nTotalRemainderDist())+ " \n" +
                    "### 목적지 남은시간 :  " + updateTotalRemainTime(driveinfo.getM_nServiceLinkRemainderTime())+ " \n" +
                    "### 중심좌표 :  " + latlon[0] + ", " + latlon[1] + " \n" +
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
}




