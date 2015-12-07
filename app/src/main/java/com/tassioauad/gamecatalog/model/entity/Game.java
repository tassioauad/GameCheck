package com.tassioauad.gamecatalog.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Game {

    private Integer id;

    private String name;

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
}
