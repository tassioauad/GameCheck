package com.tassioauad.gamecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Game implements Parcelable {

    private Integer id;

    private String name;

    private String description;

    private String aliases;

    private Image image;

    private String deck;

    @SerializedName("original_release_date")
    private Date originalReleaseDate;

    private List<Platform> platforms;

    @SerializedName("expected_release_day")
    private Integer expectedReleaseDay;

    @SerializedName("expected_release_month")
    private Integer expectedReleaseMonth;

    @SerializedName("expected_release_year")
    private Integer expectedReleaseYear;

    public Game(Integer id, String name, String aliases, Image image, String deck,
                Date originalReleaseDate, List<Platform> platforms, Integer expectedReleaseDay,
                Integer expectedReleaseMonth, Integer expectedReleaseYear, String description) {
        this.id = id;
        this.name = name;
        this.aliases = aliases;
        this.image = image;
        this.deck = deck;
        this.originalReleaseDate = originalReleaseDate;
        this.platforms = platforms;
        this.expectedReleaseDay = expectedReleaseDay;
        this.expectedReleaseMonth = expectedReleaseMonth;
        this.expectedReleaseYear = expectedReleaseYear;
        this.description = description;
    }

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

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public Date getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public Date getExpectedReleaseDate() {
        if(expectedReleaseDay != null && expectedReleaseMonth != null && expectedReleaseYear != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(expectedReleaseDay, expectedReleaseMonth, expectedReleaseYear);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public void setOriginalReleaseDate(Date originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.aliases);
        dest.writeParcelable(this.image, 0);
        dest.writeString(this.deck);
        dest.writeLong(originalReleaseDate != null ? originalReleaseDate.getTime() : -1);
        dest.writeTypedList(platforms);
        dest.writeValue(this.expectedReleaseDay);
        dest.writeValue(this.expectedReleaseMonth);
        dest.writeValue(this.expectedReleaseYear);
    }

    protected Game(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.aliases = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.deck = in.readString();
        long tmpOriginalReleaseDate = in.readLong();
        this.originalReleaseDate = tmpOriginalReleaseDate == -1 ? null : new Date(tmpOriginalReleaseDate);
        this.platforms = in.createTypedArrayList(Platform.CREATOR);
        this.expectedReleaseDay = (Integer) in.readValue(Integer.class.getClassLoader());
        this.expectedReleaseMonth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.expectedReleaseYear = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
