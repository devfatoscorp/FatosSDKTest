package fatossdk.kr.fatos.fatossdktest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import biz.fatossdk.fminterface.FMBaseActivity;
import biz.fatossdk.nativeMap.FatosMainMapView;
import biz.fatossdk.nativeMap.MapAnimation;
import biz.fatossdk.newanavi.base.AMapBaseFragment;


public class FatosRouteMapFragment extends AMapBaseFragment
        implements FatosMapBaseSDKActivity.OnFatosMapListener, FatosMainMapView.OnFatosMapListener {
    public static final String TAG = "AMAP";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".FatosRouteMapFragment";

    private FatosMainMapView mView = null;

    @Override
    public void onUpdateMapMode(int nStatus) {
        if((getActivity()) != null)
            ((FMBaseActivity)getActivity()).onUpdateMapMode(nStatus);
    }

    @Override
    public void onUpdateMapScaleInfo(int nStatus) {

    }

    @Override
    public void onUpdateFps(float fFps, float level) {

    }


    @Override
    public void onUpdateFirstMapTouch() {
        if((getActivity()) != null) {
            ((FMBaseActivity) getActivity()).onUpdateFirstMapTouch();
        }

    }

    @Override
    public void onUpdateTwoTouchCenter(float fX, float fY) {
        if((getActivity()) != null) {
            ((FMBaseActivity) getActivity()).onUpdateTwoTouchCenter(fX, fY);
        }

    }

    @Override
    public void onUpdateTwoTouchUp(boolean bTilt) {
        if((getActivity()) != null) {
            ((FMBaseActivity) getActivity()).onUpdateTwoTouchUp(bTilt);
        }
    }

    @Override
    public void onUpdateMapAngle(float nAngle) {
        if((getActivity()) != null) {
            ((FMBaseActivity) getActivity()).onUpdateMapAngle(nAngle);
        }
    }

    @Override
    public void onUpdatePickerInfo(String strID, int nLong, int nLat){
        if((getActivity()) != null) {
            ((FMBaseActivity) getActivity()).onUpdatePickerInfo(strID,nLong,nLat);
        }
    }

    @Override
    public void onUpdateMapTouch(float fX, float fY) {

    }

    @Override
    public void onUpdateMapLongTouch(float v, float v1) {

    }

    @Override
    public void onMapReady() {
        ((FMBaseActivity)getActivity()).onMapReady();
    }
    @Override
    public void onCustomZoomInOutEnd(boolean bEnd) {
        if(mView != null)
            mView.onCustomZoomInOutEnd(bEnd);
    }

    @Override
    public void moveMapCurrentPos() {
        if(mView != null)
            mView.mapMoveCurPos();
    }

    @Override
    public void mapMoveDirectCurPos() {

    }

    @Override
    public void mapCenterUpdate() {
        if(mView != null)
            mView.mapCenterUpdate();
    }

    @Override
    public void setMapMode() {
        if(mView != null)
            mView.setMapMode();
    }

    @Override
    public void onMapAnimation(MapAnimation aniInfo) {
        if(mView != null)
            mView.setAniInfo(aniInfo);
    }

    @Override
    public void onCustomZoomInOut() {
        if(mView != null)
            mView.onCustomZoomInOut();
    }

    @Override
    public void onMapDrawPinImg(double x, double y, int nPinType) {
        if(mView != null)
            mView.setPinImg(x,y,nPinType);
    }

    @Override
    public void onSetMapStatus(int nStatus) {

    }

    @Override
    public void onMapLevelInOut(float fLevel) {
        if(mView != null)
            mView.onMapLevelInOut(fLevel);
    }

    @Override
    public void onMapLevelInOut(float v, float v1) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new FatosMainMapView(this.getActivity());
        mView.setOnFatosMapListener(this);
        ((FMBaseActivity)getActivity()).setOnFatosMapListener(this);

        //this.getActivity().setContentView(mView);
    }

    public FatosMainMapView getMapView() {
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


}
