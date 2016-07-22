package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Model.Enum.ConditionClickItemAdapter;
import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.POJO.DetailsItemModel;
import com.example.jorik.kursapplicationandroid.Model.POJO.DetailsPageModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.FullGasDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.FullRepairDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.DriverService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverDetailsAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.FullGasAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.RepairAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 27.05.16.
 */

public class DetailsPagerViewModel extends BaseViewModel {

    private Context mContext;
    private DetailsPageModel mDetailsPageModel;
    private KindDataBase mKindDataBase;
    private int position;

    private Subscription mSubscriptionDriver;
    private Subscription mSubscriptionBus;
    private Subscription mSubscriptionGasList;
    private Subscription mSubscriptionRepairList;
    private Subscription mSubscriptionDataInfo;

    private PhotoDetailsCallback callback;
    private DetailsViewModel detailsViewModel;

    public DetailsPagerViewModel(Context mContext, KindDataBase mKindDataBase, int position, DetailsViewModel detailsViewModel) {
        this.mContext = mContext;
        this.mKindDataBase = mKindDataBase;
        this.position = position;
        this.detailsViewModel = detailsViewModel;
        mDetailsPageModel = new DetailsPageModel();
    }

    @Bindable
    public boolean getCompleted() {
        return mDetailsPageModel.getCompleted();
    }

    public void setCompleted(boolean completed) {
        mDetailsPageModel.setCompleted(completed);
        notifyPropertyChanged(BR.completed);
    }

    @Bindable
    public boolean getProgress() {
        return mDetailsPageModel.getProgress();
    }

    public void setProgress(boolean progress) {
        mDetailsPageModel.setProgress(progress);
        notifyPropertyChanged(BR.progress);
    }

    @Bindable
    public String getErrorText() {
        return mDetailsPageModel.getErrorText();
    }

    public void setErrorText(String errorText) {
        mDetailsPageModel.setErrorText(errorText);
        notifyPropertyChanged(BR.errorText);
    }

    @Bindable
    public RecyclerView.Adapter<?> getAdapter() {
        return mDetailsPageModel.getAdapter();
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        mDetailsPageModel.setAdapter(adapter);
        notifyPropertyChanged(BR.adapter);
    }


    public void chooseRequestByPositionAndKind(int id) {
        if (mKindDataBase == KindDataBase.BUS) {
            switch (position) {
                case 0:
                    getBusInfo(id);
                    break;
                case 1:
                    getGasInfo(id);
                    break;
                case 2:
                    getRepairInfo(id);
                    break;
            }
        } else {
            getDriverInfo(id);
        }
    }

    private void getDriverInfo(int id) {

        setProgress(true);

        callback = detailsViewModel;

        List<DriverDTO> driverInfo = new ArrayList<>();
        DriverService driverService = RestClient.getServiceInterface(DriverService.class);
        Observable<DriverDTO> driverDTOObservable = driverService.getDriver(id);
        mSubscriptionDriver = driverDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DriverDTO>() {
                    @Override
                    public void onCompleted() {
                        showOrHideResult(true);
                        if (driverInfo.size() == 1)
                            callback.setPictureUserDetails(driverInfo.get(0).getImage());
                        createInfoAdapter(convertDriverToStringValue(driverInfo.get(0)), KindDataBase.DRIVER);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showOrHideResult(false);
                        setErrorText(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(DriverDTO driverDTOs) {
                        driverInfo.add(driverDTOs);
                    }
                });
    }

    private void getBusInfo(int id) {
        setProgress(true);

        List<BusDTO> busInfo = new ArrayList<>();
        BusService busService = RestClient.getServiceInterface(BusService.class);
        Observable<BusDTO> busDTOObservable = busService.getBus(id);
        mSubscriptionBus = busDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BusDTO>() {
                    @Override
                    public void onCompleted() {
                        showOrHideResult(true);
                        createInfoAdapter(convertBusToStringValue(busInfo.get(0)), KindDataBase.BUS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showOrHideResult(false);
                        setErrorText(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(BusDTO busDTO) {
                        busInfo.add(busDTO);
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getRepairInfo(int id) {
        setProgress(true);

        List<FullRepairDTO> repairInfo = new ArrayList<>();
        BusService busService = RestClient.getServiceInterface(BusService.class);
        Observable<List<FullRepairDTO>> repairDTOListObservable = busService.getRepairListById(id);
        mSubscriptionRepairList = repairDTOListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getRepairDTO().getServiceListId(), item2.getRepairDTO().getServiceListId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<FullRepairDTO>() {
                    @Override
                    public void onCompleted() {
                        showOrHideResult(true);
                        if (repairInfo.size() == 0)
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        setAdapter(new RepairAdapter(mContext, repairInfo));
                    }

                    @Override
                    public void onError(Throwable e) {
                        showOrHideResult(false);
                        setErrorText(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(FullRepairDTO fullRepairDTO) {
                        repairInfo.add(fullRepairDTO);
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getGasInfo(int id) {
        setProgress(true);

        List<FullGasDTO> gasInfo = new ArrayList<>();
        BusService busService = RestClient.getServiceInterface(BusService.class);
        Observable<List<FullGasDTO>> gasDTOListObservable = busService.getGasListById(id);
        mSubscriptionGasList = gasDTOListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getGasDTO().getGasListId(), item2.getGasDTO().getGasListId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<FullGasDTO>() {
                    @Override
                    public void onCompleted() {
                        showOrHideResult(true);
                        if (gasInfo.size() == 0)
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        setAdapter(new FullGasAdapter(mContext, gasInfo));
                    }

                    @Override
                    public void onError(Throwable e) {
                        showOrHideResult(false);
                        setErrorText(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(FullGasDTO fullGasDTO) {
                        gasInfo.add(fullGasDTO);
                    }
                });

    }

    private List<String> convertDriverToStringValue(DriverDTO value) {
        List<String> listData = new ArrayList<>();
        listData.add(value.getSecondname());
        listData.add(String.format("%s %S", value.getQualification(), mContext.getString(R.string.class_details)));
        listData.add(String.format("%d %s", value.getExperience(), mContext.getString(R.string.year)));
        listData.add(String.format("%d %s",value.getSalary(), mContext.getString(R.string.grn)));
        return listData;
    }

    private List<String> convertBusToStringValue(BusDTO value) {
        List<String> listData = new ArrayList<>();
        listData.add(Integer.toString(value.getNumber()));
        listData.add(value.getModel());
        listData.add(value.getCondition());
        return listData;
    }

    private void createInfoAdapter(List<String> data, KindDataBase kind) {

        Resources resources = mContext.getResources();
        TypedArray typedArray = resources.obtainTypedArray(kind.equals(KindDataBase.DRIVER) ? R.array.details_image_icon_driver  : R.array.details_image_icon_bus);

        List<Drawable> valueIcon = new ArrayList<>();

        for(int i = 0; i < data.size(); i++){
            valueIcon.add(typedArray.getDrawable(i));
        }

        List<DetailsItemModel> valueList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
            mSubscriptionDataInfo = Observable.zip(Observable.just(valueIcon.get(i)), Observable.just(data.get(i)), DetailsItemModel::new)
                    .subscribe(new Subscriber<DetailsItemModel>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DetailsItemModel detailsItemModel) {
                            valueList.add(detailsItemModel);
                        }
                    });

        if (valueList.size() != 0)
            setAdapter(new DriverDetailsAdapter(mContext, valueList));
    }

    private void showOrHideResult(boolean completed) {
        setCompleted(completed);
        setProgress(false);
    }

    public void unsubscriber() {
        if (mSubscriptionDriver != null)
            mSubscriptionDriver.unsubscribe();
        if (mSubscriptionBus != null)
            mSubscriptionBus.unsubscribe();
        if (mSubscriptionGasList != null)
            mSubscriptionGasList.unsubscribe();
        if (mSubscriptionRepairList != null)
            mSubscriptionRepairList.unsubscribe();
        if(mSubscriptionDataInfo != null){
            mSubscriptionDataInfo.unsubscribe();
        }
    }

    public interface PhotoDetailsCallback {
        void setPictureUserDetails(String url);
    }

}
