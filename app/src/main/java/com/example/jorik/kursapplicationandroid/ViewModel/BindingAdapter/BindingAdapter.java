package com.example.jorik.kursapplicationandroid.ViewModel.BindingAdapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorik.kursapplicationandroid.Utils.CircleTransformer;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jorik on 27.05.16.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter("adapter")
    public static <VH extends RecyclerView.ViewHolder, V extends RecyclerView.Adapter<VH>> void setAdapter(RecyclerView recyclerView, V t) {
        if (t != null) recyclerView.setAdapter(t);
    }

    @android.databinding.BindingAdapter("error")
    public static void errorValidator(TextInputLayout textInputLayout, String error) {
        textInputLayout.setErrorEnabled(error != null);
        textInputLayout.setError(error);
    }

    @android.databinding.BindingAdapter("enable")
    public static void enableSend(FloatingActionButton floatingActionButton, boolean enableSendButton) {
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(enableSendButton ? "#00CC33" : "#CC0033")));
        floatingActionButton.setEnabled(enableSendButton);
    }

    @android.databinding.BindingAdapter("titleImage")
    public static void setTitileImage(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @android.databinding.BindingAdapter("imageDrawable")
    public static void setTitileImage(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @android.databinding.BindingAdapter("dateFormat")
    public static void setDateformat(TextView textView, Date date) {
        String result = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            result = dateFormat.format(date);
        } catch (Exception ex) {
        }
        textView.setText(result);
    }

    @android.databinding.BindingAdapter("onClick")
    public static void onClickListner(View view, Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @android.databinding.BindingAdapter("onRefresh")
    public static void onRefreshListner(SwipeRefreshLayout refreshLayout, Runnable runnable) {
        refreshLayout.setOnRefreshListener(runnable::run);
    }

    @android.databinding.BindingAdapter({"loadSrc", "loadPlaceholder", "loadError", "loadCircleFlag"})
    public static void loadImage(ImageView imageView, String src, Drawable placeholder, Drawable error, boolean circle) {
        Picasso.with(imageView.getContext())
                .load(src)
                .placeholder(placeholder)
                .error(error)
                .transform(new CircleTransformer(circle))
                .into(imageView);
    }

}
