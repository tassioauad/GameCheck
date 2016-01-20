package com.tassioauad.gamecatalog.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Platform implements Parcelable {

    private Long id;

    private String name;

    private String aliases;

    private String abbreviation;

    private Company company;

    private String deck;

    private Image image;

    @SerializedName("release_date")
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.aliases);
        dest.writeString(this.abbreviation);
        dest.writeParcelable(this.company, 0);
        dest.writeString(this.deck);
        dest.writeParcelable(this.image, 0);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1);
    }

    public Platform() {
    }

    protected Platform(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.aliases = in.readString();
        this.abbreviation = in.readString();
        this.company = in.readParcelable(Company.class.getClassLoader());
        this.deck = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
    }

    public static final Creator<Platform> CREATOR = new Creator<Platform>() {
        public Platform createFromParcel(Parcel source) {
            return new Platform(source);
        }

        public Platform[] newArray(int size) {
            return new Platform[size];
        }
    };
}
