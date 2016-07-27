package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.POJO.FriendsModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.TaggableFriendsDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.FacebookService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.FriendsAdapter;
import com.facebook.AccessToken;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 29.05.16.
 */

public class FriendsViewModel extends BaseObservable {

    private Context mContext;
    private TaggableFriendsDTO mTaggableFriendsDTO;

    private Subscription mSubscriptionGetAllFriends;

    private FriendsModel mFriendsModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public FriendsViewModel(Context mContext, SwipeRefreshLayout swipeRefresh) {
        this.mContext = mContext;
        mSwipeRefresh = swipeRefresh;
        mFriendsModel = new FriendsModel();
        mTaggableFriendsDTO = new TaggableFriendsDTO();
    }

    @Bindable
    public boolean getVisibleProgressFriends() {
        return mFriendsModel.getVisibleProgress();
    }

    public void setVisibleProgressFriends(boolean visibleProgress) {
        mFriendsModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressFriends);
    }

    @Bindable
    public String getErrorStringFriends() {
        return mFriendsModel.getErrorString();
    }

    public void setErrorStringFriends(String errorString) {
        mFriendsModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringFriends);
    }

    @Bindable
    public FriendsAdapter getFriendsAdapter() {
        return mFriendsModel.getFriendsAdapter();
    }

    public void setFriendsAdapter(FriendsAdapter friendsAdapter) {
        mFriendsModel.setFriendsAdapter(friendsAdapter);
        notifyPropertyChanged(BR.friendsAdapter);
    }

    @Bindable
    public boolean getCompleteRequestFriends() {
        return mFriendsModel.getCompleteRequest();
    }

    public void setCompleteRequestFriends(boolean completeRequest) {
        mFriendsModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestFriends);
    }

    public void getFriendsList() {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressFriends(true);
        }

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        FacebookService facebookService = RestClient.getServiceInterface(FacebookService.class);
        Observable<TaggableFriendsDTO> getInfo = facebookService.allFriends(base.getIdUser(), mContext.getString(R.string.taggable_friends_part_url), AccessToken.getCurrentAccessToken().getToken());
        mSubscriptionGetAllFriends = getInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaggableFriendsDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);

                        if (mTaggableFriendsDTO != null && mTaggableFriendsDTO.getFriendsDTO() != null && mTaggableFriendsDTO.getFriendsDTO() != null) {
                            if (mTaggableFriendsDTO.getFriendsDTO().getData().size() == 0) {
                                onError(new Throwable(mContext.getString(R.string.no_data)));
                            }
                            setFriendsAdapter(new FriendsAdapter(mContext, mTaggableFriendsDTO.getFriendsDTO().getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorStringFriends(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(TaggableFriendsDTO taggableFriendsDTO) {
                        mTaggableFriendsDTO = taggableFriendsDTO;
                    }
                });
    }

    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressFriends(false);
        setCompleteRequestFriends(completed);
    }

    public void unsubscribe() {
        if (mSubscriptionGetAllFriends != null)
            mSubscriptionGetAllFriends.unsubscribe();
    }

}
