package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.POJO.UserModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.UserDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.FacebookService;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 01.06.16.
 */

public class UserInfoViewModel extends BaseObservable {

    private Context mContext;
    private UserModel mUserModel;
    private UserDTO mUserDTO;
    private Subscription mSubscriptionUserInfo;

    public UserInfoViewModel(Context context) {
        mContext = context;
        mUserModel = new UserModel();
        mUserDTO = new UserDTO();
    }

    @Bindable
    public String getName() {
        return mUserModel.getName();
    }

    public void setName(String name) {
        mUserModel.setName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return mUserModel.getEmail();
    }

    public void setEmail(String email) {
        mUserModel.setEmail(email);
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhoto() {
        return mUserModel.getPhoto();
    }

    public void setPhoto(String photoUrl) {
        mUserModel.setPhoto(photoUrl);
        notifyPropertyChanged(BR.photo);
    }

    @Bindable
    public String getCoordinate() {
        return mUserModel.getCoordinate();
    }

    public void setCoordinate(String coordinate) {
        mUserModel.setCoordinate(coordinate);
        notifyPropertyChanged(BR.coordinate);
    }

    public void getInformationUserFromFacebook(String accessToken) {

        FacebookService facebookService = RestClient.getServiceInterface(FacebookService.class);
        Observable<UserDTO> getInfo = facebookService.infoAboutUser(accessToken);
        mSubscriptionUserInfo = getInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserDTO>() {
                    @Override
                    public void onCompleted() {
                        fillData(mUserDTO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        fillData(mUserDTO);
                    }

                    @Override
                    public void onNext(UserDTO userDTO) {
                        mUserDTO = userDTO;
                    }
                });
    }

    private void fillData(UserDTO userDTO) {
        if (userDTO != null) {
            setName(userDTO.getName());
            setEmail(userDTO.getEmail());
            setPhoto(userDTO.getPicture() != null ? userDTO.getPicture().getDataPictureDTO().getUrl() != null ? userDTO.getPicture().getDataPictureDTO().getUrl() : null : null);
            setCoordinate(userDTO.getLocation() == null ? null : userDTO.getLocation().getName());
        } else {
            ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
            setName(base.getName());
            setEmail(base.getEmail());
            setPhoto(base.getImageUser());
            setCoordinate(base.getLocation() == null ? null : userDTO.getLocation().getName());

        }
    }

    public void unsubscribe() {
        if (mSubscriptionUserInfo != null)
            mSubscriptionUserInfo.unsubscribe();
    }

}
