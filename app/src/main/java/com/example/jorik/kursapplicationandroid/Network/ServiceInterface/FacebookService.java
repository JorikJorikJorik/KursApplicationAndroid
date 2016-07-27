package com.example.jorik.kursapplicationandroid.Network.ServiceInterface;

import com.example.jorik.kursapplicationandroid.Network.DTO.AlbumsDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.FriendsDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.PhotosDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.PictureDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.TaggableFriendsDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.UserDTO;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by jorik on 27.07.16.
 */

public interface FacebookService {

    @GET("me?fields=id,name,location,email,picture")
    Observable<UserDTO> infoAboutUser(@Query("access_token") String token);

    @GET("{id}")
    Observable<TaggableFriendsDTO> allFriends(@Path("id") String id, @Query("fields") String fields, @Query("access_token") String toke);

    @GET("{id}")
    Observable<AlbumsDTO> albumPhotoInfo(@Path("id") String id, @Query("fields") String fields, @Query("access_token") String toke);

    @GET("{id}")
    Observable<PhotosDTO> allPictureFromAlbum(@Path("id") String id, @Query("fields") String fields, @Query("access_token") String toke);
}
