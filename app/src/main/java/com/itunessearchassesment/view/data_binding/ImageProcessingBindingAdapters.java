package com.itunessearchassesment.view.data_binding;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class ImageProcessingBindingAdapters {

    private static void finishRequest(ImageView imageView, RequestBuilder<Drawable> request, boolean circular, float corners, Drawable fallback) {
        if (circular) {
            request = request.circleCrop();
        } else if (corners > 0) {
            request = request.transform(new CenterCrop(), new RoundedCorners(Math.round(corners)));
        }
        if (fallback != null) {
            request.placeholder(fallback);
        }

        request.diskCacheStrategy(DiskCacheStrategy.ALL);
        request.transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    @BindingAdapter(value = {"image", "circular", "corners", "fallback"}, requireAll = false)
    public static void loadImage(ImageView imageView, int resId, boolean circular, float corners, Drawable fallback) {
        if (resId == 0) {
            imageView.setImageDrawable(fallback);
        } else {
            finishRequest(imageView, Glide.with(imageView).load(resId), circular, corners, fallback);
        }
    }


    @BindingAdapter(value = {"image", "circular", "corners", "fallback"}, requireAll = false)
    public static void loadImage(ImageView imageView, Drawable drawable, boolean circular, float corners, Drawable fallback) {
        if (drawable == null) {
            imageView.setImageDrawable(fallback);
        } else {
            finishRequest(imageView, Glide.with(imageView).load(drawable), circular, corners, fallback);
        }
    }


    @BindingAdapter(value = {"image", "circular", "corners", "fallback"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, boolean circular, float corners, Drawable fallback) {
        if (url == null) {
            imageView.setImageDrawable(fallback);
        } else {
            finishRequest(imageView, Glide.with(imageView).load(url), circular, corners, fallback);
        }
    }

    @BindingAdapter(value = {"image", "circular", "corners", "fallback"}, requireAll = false)
    public static void loadImage(ImageView imageView, Uri uri, boolean circular, float corners, Drawable fallback) {
        if (uri == null) {
            imageView.setImageDrawable(fallback);
        } else {
            finishRequest(imageView, Glide.with(imageView).load(uri), circular, corners, fallback);
        }
    }
}
