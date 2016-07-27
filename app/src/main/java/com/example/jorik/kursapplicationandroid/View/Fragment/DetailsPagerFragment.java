package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsPagerViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentDetailsPagerBinding;

import java.util.ArrayList;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsPagerFragment extends Fragment {

    private static final String ARGUMENTS_CHOOSE_ID = "choose_id";
    private static final String ARGUMENTS_PHOTO_LIST = "list_photo";
    private static final String ARGUMENTS_VIEW_MODEL = "view_model";

    private FragmentDetailsPagerBinding mFragmentDetailsPagerBinding;
    private DetailsPagerViewModel mDetailsPagerViewModel;
    private int chooseId;
    private ArrayList<String> photoList;
    private ViewModelCallback mCallback;
    private DetailsViewModel mViewModel;

    public static Fragment newInstance(ArrayList<String> list, int chooseId, DetailsViewModel viewModel) {

        DetailsPagerFragment fragment = new DetailsPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_CHOOSE_ID, chooseId);
        args.putStringArrayList(ARGUMENTS_PHOTO_LIST, list);
        args.putParcelable(ARGUMENTS_VIEW_MODEL, viewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseId = getArguments().getInt(ARGUMENTS_CHOOSE_ID);
        photoList = getArguments().getStringArrayList(ARGUMENTS_PHOTO_LIST);
        mViewModel = getArguments().getParcelable(ARGUMENTS_VIEW_MODEL);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details_pager, container, false);

        mDetailsPagerViewModel = new DetailsPagerViewModel(getActivity(), photoList);

        mFragmentDetailsPagerBinding = DataBindingUtil.bind(view);
        mFragmentDetailsPagerBinding.setPager(mDetailsPagerViewModel);

        mDetailsPagerViewModel.createPhoto(chooseId);

        mCallback = (ViewModelCallback )mViewModel;
        mCallback.getViewModel(mDetailsPagerViewModel);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface ViewModelCallback{
        public void getViewModel(DetailsPagerViewModel viewModel);
    }
}
