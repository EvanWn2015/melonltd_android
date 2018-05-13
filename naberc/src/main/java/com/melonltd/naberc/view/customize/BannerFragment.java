package com.melonltd.naberc.view.customize;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.melonltd.naberc.R;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;


public class BannerFragment extends Fragment {
    private SimpleDraweeView bannerDraweeView;
    private TextView titleTextView, contentTextView;
    private String title = "", content = "";
    private Uri uri;
    private static BannerFragment BANNER_FRAGMENT = null;

    public static BannerFragment getInstance() {
        if (BANNER_FRAGMENT == null) {
            BANNER_FRAGMENT = new BannerFragment();
        }
        return BANNER_FRAGMENT;
    }

    public static BannerFragment newInstance(Bundle bundle, String title, String content, String uri) {
        BannerFragment fragment = new BannerFragment();
        fragment.title = title;
        fragment.content = content;
        fragment.uri = Uri.parse(uri);
        fragment.setArguments(bundle);
        return fragment;
    }

    private BannerFragment setParameter(Bundle bundle, String title, String content, String uri) {
        this.title = title;
        this.content = content;
        this.uri = Uri.parse(uri);
        this.setArguments(bundle);
        return this;
    }

    public Builder Builder() {
        return new Builder();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_banner, container, false);
        getView(v);
        setValue();
        return v;
    }

    private void getView(View v) {
        bannerDraweeView = v.findViewById(R.id.bannerDraweeView);
        titleTextView = v.findViewById(R.id.titleTextView);
        contentTextView = v.findViewById(R.id.contentTextView);
    }

    private void setValue() {
        bannerDraweeView.setImageURI(uri);
        titleTextView.setText(this.title);
        contentTextView.setText(this.content);
    }

    public static class Builder {
        String title = "", content = "", uri = "";
        BannerFragment fragment;
        Bundle bundle;

        Builder() {
            this.fragment = BannerFragment.newInstance(null, title, content, uri);
        }

        public Builder setBundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setImageURI(String uri) {
            this.uri = uri;
            return this;
        }

        public BannerFragment build() {
            return this.fragment.setParameter(this.bundle, this.title, this.content, this.uri);
        }

    }
}