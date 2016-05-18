package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServerRequestAllData;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 17.05.16.
 */
public class BusViewModel extends BaseObservable implements ServerRequestAllData {

    private Context mContext;
    private List<BusDTO> listBusDTO = new ArrayList<>();
    private BusAdapter busAdapter;
    private static final String TAG = BusViewModel.class.getSimpleName();
    private Subscription mSubscription;


    public BusViewModel(Context context) {
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void getAllDataRequest() {
        BusService mBusService = RestClient.getServiceInterface(BusService.class);
        Observable<List<BusDTO>> busDTOObservable;busDTOObservable =  mBusService.getBusList();
        mSubscription = busDTOObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Long.compare(item1.getBusId(),item2.getBusId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<BusDTO>() {
                    @Override
                    public void onCompleted() {
                        busAdapter = new BusAdapter(mContext, listBusDTO);
                        setBusAdapter(busAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                    }
                    @Override
                    public void onNext(BusDTO busDTO) {
                        listBusDTO.add(busDTO);
                    }
                });
        setSubscription(mSubscription);
    }

    @Override
    public void unsubscribeRequest() {
        if(getSubscription() != null){
            mSubscription.unsubscribe();
        }
    }

    public Subscription getSubscription() {
        return mSubscription;
    }

    public void setSubscription(Subscription subscription) {
        mSubscription = subscription;
    }
    @Bindable
    public BusAdapter getBusAdapter() {
        return busAdapter;
    }

    public void setBusAdapter(BusAdapter busAdapter) {
        this.busAdapter = busAdapter;
        notifyPropertyChanged(BR.busAdapter);
    }


    @BindingAdapter("bind:adapter")
    public static void setAdapter(RecyclerView recyclerView, BusAdapter busAdapter){
        if(busAdapter != null) recyclerView.setAdapter(busAdapter);
    }
}
