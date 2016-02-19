package com.tassioauad.gamecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("medium_url")
    private String mediumUrl;

    @SerializedName("screen_url")
    private String screenUrl;

    @SerializedName("small_url")
    private String smallUrl;

    @SerializedName("super_url")
    private String superUrl;

    @SerializedName("thumb_url")
    private String thumbUrl;

    @SerializedName("tiny_url")
    private String tinyUrl;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getSuperUrl() {
        return superUrl;
    }

    public void setSuperUrl(String superUrl) {
        this.superUrl = superUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iconUrl);
        dest.writeString(this.mediumUrl);
        dest.writeString(this.screenUrl);
        dest.writeString(this.smallUrl);
        dest.writeString(this.superUrl);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.tinyUrl);
    }

    public Image() {
    }

    public Image(String iconUrl, String mediumUrl, String screenUrl, String smallUrl, String superUrl, String thumbUrl, String tinyUrl) {
        this.iconUrl = iconUrl;
        this.mediumUrl = mediumUrl;
        this.screenUrl = screenUrl;
        this.smallUrl = smallUrl;
        this.superUrl = superUrl;
        this.thumbUrl = thumbUrl;
        this.tinyUrl = tinyUrl;
    }

    protected Image(Parcel in) {
        this.iconUrl = in.readString();
        this.mediumUrl = in.readString();
        this.screenUrl = in.readString();
        this.smallUrl = in.readString();
        this.superUrl = in.readString();
        this.thumbUrl = in.readString();
        this.tinyUrl = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
