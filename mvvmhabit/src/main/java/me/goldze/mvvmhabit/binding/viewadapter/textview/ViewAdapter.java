package me.goldze.mvvmhabit.binding.viewadapter.textview;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import me.goldze.mvvmhabit.R;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"textColor"}, requireAll = false)
    public static void setTextColor(TextView textView, int color) {
        textView.setTextColor(textView.getContext().getResources().getColor(color));
    }
}

