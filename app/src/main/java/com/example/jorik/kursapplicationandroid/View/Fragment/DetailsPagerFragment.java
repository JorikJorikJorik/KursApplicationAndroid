package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsPagerViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentDetailsPagerBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;

/**
 * Created by jorik on 26.05.16.
 */

public class DetailsPagerFragment extends BaseFragment {

    private static final String ARGUMENTS_NUMBER_PAGES = "position_pages";
    private static final String ARGUMENTS_KIND_ENUM_PAGES = "kind_pages";
    private static final String ARGUMENTS_CHOOSE_ID = "choose_id";
    private static final String ARGUMENTS_VIEW_MODEL = "view_model";

    private FragmentDetailsPagerBinding mFragmentDetailsPagerBinding;
    private DetailsPagerViewModel mDetailsPagerViewModel;
    private KindDataBase mKindDataBase;
    private int position;
    private int chooseId;
    private DetailsViewModel detailsViewModel;

    public static Fragment newInstance(KindDataBase mKindDataBase, int position, int chooseId, DetailsViewModel detailsViewModel) {

        DetailsPagerFragment fragment = new DetailsPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENTS_NUMBER_PAGES, position);
        args.putInt(ARGUMENTS_KIND_ENUM_PAGES, mKindDataBase.getValue());
        args.putInt(ARGUMENTS_CHOOSE_ID, chooseId);
        args.putParcelable(ARGUMENTS_VIEW_MODEL, detailsViewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseId = getArguments().getInt(ARGUMENTS_CHOOSE_ID);
        position = getArguments().getInt(ARGUMENTS_NUMBER_PAGES);
        mKindDataBase = KindDataBase.fromValue(getArguments().getInt(ARGUMENTS_KIND_ENUM_PAGES));
        detailsViewModel = getArguments().getParcelable(ARGUMENTS_VIEW_MODEL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details_pager, container, false);

        mDetailsPagerViewModel = new DetailsPagerViewModel(getActivity(), mKindDataBase, position, detailsViewModel);

        mFragmentDetailsPagerBinding  = DataBindingUtil.bind(view);
        mFragmentDetailsPagerBinding.setPager(mDetailsPagerViewModel);

        mFragmentDetailsPagerBinding.detailsPagerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentDetailsPagerBinding.errorText.setOnClickListener( v -> mDetailsPagerViewModel.chooseRequestByPositionAndKind(chooseId));

        mDetailsPagerViewModel.chooseRequestByPositionAndKind(chooseId);

        return view;
    }

    @Override
    public void onDestroy() {
        mDetailsPagerViewModel.unsubscriber();
        super.onDestroy();
    }
}
