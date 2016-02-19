package com.tassioauad.gamecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Company implements Parcelable {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public Company() {
    }

    protected Company(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel source) {
            return new Company(source);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}
