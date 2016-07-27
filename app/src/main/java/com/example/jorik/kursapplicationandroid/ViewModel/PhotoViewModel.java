package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.POJO.PhotoModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.AlbumInfoDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.AlbumsDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.DataPhotosDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.DataPictureDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.PhotosDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.PictureDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.TaggableFriendsDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.FacebookService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.FriendsAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.PhotoAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.PhotoAdapter;
import com.facebook.AccessToken;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 29.05.16.
 */

public class PhotoViewModel extends BaseObservable {

    private Context mContext;
    private AlbumsDTO mAlbumsDTO;
    private PhotosDTO mPhotosDTO;
    private TaggableFriendsDTO mTaggableFriendsDTO;


    private Subscription mSubscriptionGetAllAlbums;
    private Subscription mSubscriptionGetPhotoAlbums;
    private Subscription mSubscriptionGetAllFriends;

    private PhotoModel mPhotoModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public PhotoViewModel(Context mContext, SwipeRefreshLayout swipeRefresh) {
        this.mContext = mContext;
        mSwipeRefresh = swipeRefresh;
        mPhotoModel = new PhotoModel();
        mAlbumsDTO = new AlbumsDTO();
        mPhotosDTO = new PhotosDTO();
    }

    @Bindable
    public boolean getVisibleProgressPhoto() {
        return mPhotoModel.getVisibleProgress();
    }

    public void setVisibleProgressPhoto(boolean visibleProgress) {
        mPhotoModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressPhoto);
    }

    @Bindable
    public int getVisibleFABPhoto() {
        return mPhotoModel.getVisibleFAB();
    }

    public void setVisibleFABPhoto(int visibleFAB) {
        mPhotoModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABPhoto);
    }

    @Bindable
    public String getErrorStringPhoto() {
        return mPhotoModel.getErrorString();
    }

    public void setErrorStringPhoto(String errorString) {
        mPhotoModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringPhoto);
    }

    @Bindable
    public PhotoAdapter getPhotoAdapter() {
        return mPhotoModel.getPhotoAdapter();
    }

    public void setPhotoAdapter(PhotoAdapter mPhotoAdapter) {
        mPhotoModel.setPhotoAdapter(mPhotoAdapter);
        notifyPropertyChanged(BR.photoAdapter);
    }

    @Bindable
    public boolean getCompleteRequestPhoto() {
        return mPhotoModel.getCompleteRequest();
    }

    public void setCompleteRequestPhoto(boolean completeRequest) {
        mPhotoModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestPhoto);
    }

    //Как оно на самом деле должно работать , с урлом на фотках проблма
    public void getAllAlbumsUser() {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressPhoto(true);
        }

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        FacebookService facebookService = RestClient.getServiceInterface(FacebookService.class);
        Observable<AlbumsDTO> getInfo = facebookService.albumPhotoInfo(base.getIdUser(), mContext.getString(R.string.albums_part_url), AccessToken.getCurrentAccessToken().getToken());
        mSubscriptionGetAllAlbums = getInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlbumsDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (mAlbumsDTO != null && mAlbumsDTO.getAlbums() != null && mAlbumsDTO.getAlbums().getData() != null) {
                            List<AlbumInfoDTO> listAlbumInfoDTO = mAlbumsDTO.getAlbums().getData();
                            if (listAlbumInfoDTO.size() == 0) {
                                onError(new Throwable(mContext.getString(R.string.no_data)));
                            }
                            for (AlbumInfoDTO item : listAlbumInfoDTO) {
                                if (item.getName().equals(mContext.getString(R.string.timeline_photos)))
                                    getPhotoForAdapter(item.getId());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorStringPhoto(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(AlbumsDTO albumsDTO) {
                        mAlbumsDTO = albumsDTO;
                    }
                });
    }


    public void getPhotoForAdapter(String idAlbums) {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressPhoto(true);
        }

        FacebookService facebookService = RestClient.getServiceInterface(FacebookService.class);
        Observable<PhotosDTO> getInfo = facebookService.allPictureFromAlbum(idAlbums, mContext.getString(R.string.photo_part_url), AccessToken.getCurrentAccessToken().getToken());
        mSubscriptionGetAllAlbums = getInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhotosDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (mPhotosDTO != null && mPhotosDTO.getListDataPhotosDTO() != null) {
                            List<DataPhotosDTO> listAlbumInfoDTO = mPhotosDTO.getListDataPhotosDTO();
                            if (listAlbumInfoDTO.size() == 0) {
                                onError(new Throwable(mContext.getString(R.string.no_data)));
                            }
//                            setPhotoAdapter((new PhotoAdapter(mContext, listAlbumInfoDTO));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorStringPhoto(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(PhotosDTO photosDTO) {
                        mPhotosDTO = photosDTO;
                    }
                });
    }

    //Взял фотки френдов , почему смотреть два запроса(метода) выше

    public void friendsPhoto(){
        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressPhoto(true);
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
                            setPhotoAdapter(new PhotoAdapter(mContext, mTaggableFriendsDTO.getFriendsDTO().getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorStringPhoto(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(TaggableFriendsDTO taggableFriendsDTO) {
                        mTaggableFriendsDTO = taggableFriendsDTO;
                    }
                });
    }

    public void unsubscribe() {
        if (mSubscriptionGetAllAlbums != null)
            mSubscriptionGetAllAlbums.unsubscribe();
        if (mSubscriptionGetPhotoAlbums != null)
            mSubscriptionGetPhotoAlbums.unsubscribe();
        if (mSubscriptionGetAllFriends != null)
            mSubscriptionGetAllFriends.unsubscribe();
    }


    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressPhoto(false);
        setCompleteRequestPhoto(completed);
    }
}
