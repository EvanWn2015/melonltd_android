package com.melonltd.naber.vo;

import com.google.common.base.MoreObjects;

public class AdvertisementVo {
    public String title;
    public String content_text;
    public String photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("title", title)
                .add("content_text", content_text)
                .add("photo", photo)
                .toString();
    }
}
