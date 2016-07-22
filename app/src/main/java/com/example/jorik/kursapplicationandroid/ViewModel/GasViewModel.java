package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.ResponseData;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateWork;
import com.example.jorik.kursapplicationandroid.Model.POJO.GasModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.FullGasDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.GasService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateGasDataActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.FullGasAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 29.05.16.
 */

public class GasViewModel extends BaseObservable {

    private static final String TAG = GasViewModel.class.getSimpleName();
    private static final int OK_RESPONSE = 200;

    private Context mContext;
    private List<FullGasDTO> mListFullGasDTO;

    private GasService mGasService;
    private Subscription mSubscriptionGetAllFullGas;
    private Subscription mSubscriptionDeleteFullGas;

    private ResponseData mResponseData;
    private GasModel mGasModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public GasViewModel(Context mContext, SwipeRefreshLayout mSwipeRefresh, Rights rights) {
        this.mContext = mContext;
        this.mSwipeRefresh = mSwipeRefresh;
        mGasModel = new GasModel();
        mListFullGasDTO = new ArrayList<>();
        mGasService = RestClient.getServiceInterface(GasService.class);
        setVisibleFABGas(View.INVISIBLE);
        setRightsGas(rights);
    }

    @Bindable
    public boolean getVisibleProgressGas() {
        return mGasModel.getVisibleProgress();
    }

    public void setVisibleProgressGas(boolean visibleProgress) {
        mGasModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressGas);
    }

    @Bindable
    public int getVisibleFABGas() {
        return mGasModel.getVisibleFAB();
    }

    public void setVisibleFABGas(int visibleFAB) {
        mGasModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABGas);
    }

    @Bindable
    public String getErrorStringGas() {
        return mGasModel.getErrorString();
    }

    public void setErrorStringGas(String errorString) {
        mGasModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringGas);
    }

    @Bindable
    public FullGasAdapter getFullGasAdapter() {
        return mGasModel.getFullAdapter();
    }

    public void setFullGasAdapter(FullGasAdapter fullGasAdapter) {
        mGasModel.setFullAdapter(fullGasAdapter);
        notifyPropertyChanged(BR.fullGasAdapter);
    }

    @Bindable
    public Rights getRightsGas() {
        return mGasModel.getRights();
    }

    public void setRightsGas(Rights rights) {
        mGasModel.setRights(rights);
        notifyPropertyChanged(BR.rightsGas);
    }

    @Bindable
    public boolean getCompleteRequestGas() {
        return mGasModel.getCompleteRequest();
    }

    public void setCompleteRequestGas(boolean completeRequest) {
        mGasModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestGas);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDataGas() {

        mResponseData = ResponseData.ERROR;

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressGas(true);
        }
        if (mListFullGasDTO != null) {
            mListFullGasDTO.clear();
        }
        Observable<List<FullGasDTO>> fullGasDTOObservable = getRightsGas() == Rights.WATCH ? mGasService.getAllFullGasList() : mGasService.getAllFullGasListForUser(ApplicationDataBase.getInstance().getSelectDataBase().getNumberUser());
        mSubscriptionGetAllFullGas = fullGasDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getGasDTO().getGasListId(), item2.getGasDTO().getGasListId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<FullGasDTO>() {
                    @Override
                    public void onCompleted() {
                        mResponseData = ResponseData.DATA;
                        hideProgressAndRefresh(mResponseData);
                        if (mListFullGasDTO.size() == 0) {
                            mResponseData = ResponseData.EMPTY;
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        }
                        setFullGasAdapter(new FullGasAdapter(mContext, mListFullGasDTO));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        errorAction(e);
                    }

                    @Override
                    public void onNext(FullGasDTO fullGasDTO) {
                        mListFullGasDTO.add(fullGasDTO);
                    }
                });


    }

    public void deleteGas(int id) {
        Observable<Integer> responseGasObservable = mGasService.deleteGasList(mListFullGasDTO.get(id).getGasDTO().getGasListId());
        mSubscriptionDeleteFullGas = responseGasObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        if (mListFullGasDTO.size() == 0) {
                            mResponseData = ResponseData.EMPTY;
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        if(mResponseData == ResponseData.EMPTY) {
                            errorAction(e);
                        }
                        deleteToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        deleteToast(integer == OK_RESPONSE ? mContext.getString(R.string.success_delete_gas) : mContext.getString(R.string.error_delete_gas) + integer.toString());
                    }
                });

    }

    public void unsubscribeRequest() {
        if (mSubscriptionGetAllFullGas != null)
            mSubscriptionGetAllFullGas.unsubscribe();
        if (mSubscriptionDeleteFullGas != null)
            mSubscriptionDeleteFullGas.unsubscribe();
    }

    private void deleteToast(String response) {
        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressAndRefresh(ResponseData mResponseData) {

        boolean completed = mResponseData.equals(ResponseData.DATA) || mResponseData.equals(ResponseData.EMPTY);

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressGas(false);
        setCompleteRequestGas(mResponseData.equals(ResponseData.DATA));
        setVisibleFABGas(completed ? getRightsGas() == Rights.CHANGE ? base.getStateWork() == StateWork.WORK_TODAY ? View.VISIBLE : View.INVISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    private void errorAction(Throwable e){
        hideProgressAndRefresh(mResponseData);
        setErrorStringGas(e.toString() + mContext.getString(R.string.tap_for_refresh));
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateGasDataActivity.class));
    }
}
