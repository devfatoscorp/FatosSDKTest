package fatossdk.kr.fatos.fatossdktest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import biz.fatossdk.fminterface.FMActivity;
import biz.fatossdk.nativeMap.FatosMapMoveView;
import biz.fatossdk.nativeMap.MapAnimation;
import biz.fatossdk.newanavi.base.AMapBaseFragment;


public class FatosSubMapFragment extends AMapBaseFragment
        implements FatosMapBaseSDKSubActivity.OnFatosMapListener, FatosMapMoveView.OnFatosMapListener {
    public static final String TAG = "AMAP";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".FatosMainMapFragment";

    private FatosMapMoveView mView = null;

    @Override
    public void onCustomZoomInOutEnd(boolean bEnd) {

    }

    @Override
    public void moveMapCurrentPos() {

    }

    @Override
    public void mapCenterUpdate() {
        if(mView != null)
            mView.mapMoveCurPos();
    }

    @Override
    public void setMapMode() {

    }

    @Override
    public void onMapAnimation(MapAnimation aniInfo) {
        if(mView != null)
            mView.setAniInfo(aniInfo);
    }

    @Override
    public void onCustomZoomInOut() {

    }

    @Override
    public void onMapLevelInOut(float fLevel) {

    }


    @Override
    public void onMapDrawPinImg(double x, double y, int nPinType) {
        if(mView != null)
            mView.setPinImg(x,y,nPinType);
    }

    @Override
    public void onSetMapStatus(int i) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new FatosMapMoveView(this.getActivity());
        mView.setOnFatosMapListener(this);
        ((FMActivity)getActivity()).setOnFatosMapListener(this);

        //this.getActivity().setContentView(mView);
    }

    public FatosMapMoveView getMapView() {
        return mView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getMapView();
        //View v = inflater.inflate(R.layout.dialog_map_rpsearch, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*
        if (activity instanceof OnFatosMapListener) {
            Log.d("Annv - Fragment", "activity " + activity.getLocalClassName());
            mCallback = (OnFatosMapListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
        */
    }

    @Override
    public void onUpdateMapStatus(int nStatus) {

    }

    @Override
    public void onUpdateMapMode(int nStatus) {
        if((getActivity()) != null)
            ((FatosMapBaseSDKSubActivity)getActivity()).onUpdateMapMode(nStatus);
    }

}
