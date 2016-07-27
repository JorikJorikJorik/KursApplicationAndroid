package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.Model.POJO.RegistrationModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.UserDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.FacebookService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 05.06.16.
 */

public class RegistrationViewModel extends BaseObservable {


    private Context mContext;
    private RegistrationModel mRegistrationModel;
    private Subscription mSubscriptionUserInfo;

    private UserDTO mUserDTO;

    public RegistrationViewModel(Context context) {
        mContext = context;
        mRegistrationModel = new RegistrationModel();
        mUserDTO = new UserDTO();
    }


    private void writeInDataBaseAndMove(UserDTO userDTO) {

        if (userDTO != null) {

            ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
            base.setIdUser(userDTO.getId());
            base.setName(userDTO.getName());
            base.setImageUser(userDTO.getPicture().getDataPictureDTO().getUrl());
            base.setStateApplication(StateApplication.ENTER);
            base.setLocation(userDTO.getLocation() == null ? null : userDTO.getLocation().getName());
            base.setEmail(userDTO.getEmail());
            base.save();

            Intent moveToWork = new Intent(mContext, MainActivity.class);
            mContext.startActivity(moveToWork);
        }
    }

    @Bindable
    public String getErrorStringWork() {
        return mRegistrationModel.getErrorString();
    }

    public void setErrorStringWork(String errorString) {
        mRegistrationModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringWork);
    }

    private void getInformationUserFromFacebook(String accessToken) {

        FacebookService facebookService = RestClient.getServiceInterface(FacebookService.class);
        Observable<UserDTO> getInfo = facebookService.infoAboutUser(accessToken);
        mSubscriptionUserInfo = getInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserDTO>() {
                    @Override
                    public void onCompleted() {
                        writeInDataBaseAndMove(mUserDTO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        writeInDataBaseAndMove(mUserDTO);
                    }

                    @Override
                    public void onNext(UserDTO userDTO) {
                        mUserDTO = userDTO;
                    }
                });
    }

    public FacebookCallback<LoginResult> createFacebookLoginCallback(AccessToken accessToken) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (accessToken != null)
                    getInformationUserFromFacebook(accessToken.getToken());
            }

            @Override
            public void onCancel() {
                setErrorStringWork(mContext.getString(R.string.user_cancel_login));
            }

            @Override
            public void onError(FacebookException error) {
                setErrorStringWork(error.toString());
            }
        };
    }

    public void unsubscribe() {
        if (mSubscriptionUserInfo != null)
            mSubscriptionUserInfo.unsubscribe();
    }


}
