package fatossdk.kr.fatos.fatossdktest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;

import biz.fatossdk.config.FatosBuildConfig;
import biz.fatossdk.fminterface.FMBaseActivity;
import biz.fatossdk.fminterface.FMInterface;
import biz.fatossdk.navi.NativeNavi;
import biz.fatossdk.newanavi.ANaviApplication;

public class FatosSDKMainActivity extends FMBaseActivity {
    private boolean m_bEngineInit = false;
    private ANaviApplication m_gApp;
    private Context m_Context = null;
    private Activity m_MainActivity;

    private FMInterface m_FMInterface;
    private int m_iEngineInit = 0;
    Button m_test1Btn,m_test2Btn,m_test3Btn;
    // LGD용 Serial key
    private String API_KEY = "le6c68w34235327ll364286b8f362d12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FatosBuildConfig.buildFATOSauto = false;
        FatosBuildConfig.setBuildSearchResultMode(ANaviApplication.TMAP_SEARCH_MODE);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fatos_sdkmain);

        m_MainActivity = this;
        m_Context = this;

        m_test1Btn = (Button) findViewById(R.id.fatos_button_1);
        m_test1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartMain = new Intent(m_Context, FatosMapBaseSDKActivity.class);
                startActivity(intentStartMain);
            }
        });

        m_test2Btn = (Button) findViewById(R.id.fatos_button_2);
        m_test2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


        m_test3Btn = (Button) findViewById(R.id.fatos_button_3);
        m_test3Btn.setVisibility(View.GONE);
        m_test3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartMain = new Intent(m_Context, FatosMapBaseSDKLandActivity.class);
                startActivity(intentStartMain);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionResultWRITE_EXTERNAL_STORAGE = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionResultACCESS_FINE_LOCATION = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionResultACCESS_READ_PHONE= checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
//            int permissionResultRECORD_AUDIO = checkSelfPermission(Manifest.permission.RECORD_AUDIO);

            if (permissionResultWRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_DENIED
                    || permissionResultACCESS_FINE_LOCATION == PackageManager.PERMISSION_DENIED
                    || permissionResultACCESS_READ_PHONE == PackageManager.PERMISSION_DENIED) {
//                    || permissionResultRECORD_AUDIO == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE}, 1000);
                /*
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FatosSplashVol2Activity.this);
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                                    }
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(FatosSplashVol2Activity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();

                }
                //최초로 권한을 요청할 때
                else {
                    // CALL_PHONE 권한을 Android OS 에 요청한다.
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                }
                */
            }
            else {
                try {
                    InitProcess();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
        else {
            try {
                InitProcess();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 사용자가 권한을 허용했는지 거부했는지 체크
     * @param requestCode   1000번
     * @param permissions   개발자가 요청한 권한들
     * @param grantResults  권한에 대한 응답들
     *                    permissions와 grantResults는 인덱스 별로 매칭된다.     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean bAllGranted = true;
        if (requestCode == 1000) {
            for(int i = 0; i < grantResults.length ; i++)
            {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(FatosSDKMainActivity.this, "App 실행에 필요한 권한이 설정 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    bAllGranted = false;
                    break;
                }
            }

            if(bAllGranted) {
                try {
                    InitProcess();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void InitProcess() throws IOException {
        m_iEngineInit = initFatosNaviEngine();
        if(m_iEngineInit != 1)
            return;

        m_MainActivity = this;
        m_Context = this;
        m_gApp = (ANaviApplication) m_Context.getApplicationContext();
        FMInterface.CreateInstance(m_Context);
        m_FMInterface = FMInterface.GetInstance();
        m_FMInterface.FM_Init(m_Context, API_KEY, new ANaviApplication.MapStatusListener() {
            @Override
            public void onRouteFinish() {

            }

            @Override
            public void onMapMove(boolean bMapMove) {
                // Log.e(TAG,"## onMapMove : " + bMapMove);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//
//        finish();
//        Process.killProcess(Process.myPid());
    }


    @Override
    protected void onDestroy() {
        if(NativeNavi.isLoaded())
        {
            if (NativeNavi.nativeIsRoute())
            {
                NativeNavi.nativeCancelRoute();
            }
        }

        releaseFatosAuto();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        else
        {
            finishAffinity();
        }

        System.exit(0);
        Process.killProcess(Process.myPid());

        super.onDestroy();
    }
}
