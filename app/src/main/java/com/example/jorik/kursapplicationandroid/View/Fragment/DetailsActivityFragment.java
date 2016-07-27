package com.example.jorik.kursapplicationandroid.View.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsPagerViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.DetailsViewModel;
import com.example.jorik.kursapplicationandroid.databinding.FragmentDetailsBinding;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment  {

    public static final String CHOOSE_ITEM_ID = "id_choose_item";
    public static final String LIST_PHOTO = "list_photo";

    private DetailsViewModel mDetailsViewModel;
    private FragmentDetailsBinding mFragmentDetailsBinding;
    private int chooseId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        chooseId = getActivity().getIntent().getIntExtra(CHOOSE_ITEM_ID, 0);
        ArrayList<String> photoList = getActivity().getIntent().getStringArrayListExtra(LIST_PHOTO);

        mDetailsViewModel = new DetailsViewModel(getActivity(), photoList, getChildFragmentManager(), chooseId);
        mFragmentDetailsBinding = DataBindingUtil.bind(view);
        mFragmentDetailsBinding.setDetails(mDetailsViewModel);


        mFragmentDetailsBinding.detailsViewPager.setAdapter(mDetailsViewModel.createAdapterViewPage());
        mFragmentDetailsBinding.detailsNestedscroll.setFillViewport(true);

        mFragmentDetailsBinding.detailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDetailsViewModel.changePage(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}


