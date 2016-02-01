package com.tassioauad.gamecheck.entity;


import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.model.entity.Image;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameBuilder {

    public static final Integer DEFAULT_ID = 999;

    public static final String DEFAULT_NAME = "raider";

    private Integer id;

    private String name;

    private String aliases;

    private Image image;

    private String deck;

    private Date originalReleaseDate;

    private List<Platform> platforms = new ArrayList<>();

    private Integer expectedReleaseDay;

    private Integer expectedReleaseMonth;

    private Integer expectedReleaseYear;

    private String description;

    public static GameBuilder aGame() {
        return new GameBuilder()
                .withId(DEFAULT_ID)
                .withName(DEFAULT_NAME);
    }

    public GameBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public GameBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GameBuilder withAliases(String aliases) {
        this.aliases = aliases;
        return this;
    }

    public GameBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public GameBuilder withDeck(String deck) {
        this.deck = deck;
        return this;
    }

    public GameBuilder withOriginalReleaseDate(Date originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
        return this;
    }

    public GameBuilder withPlatformList(List<Platform> platforms) {
        this.platforms = platforms;
        return this;
    }

    public GameBuilder withPlatform(Platform platform) {
        platforms.add(platform);
        return this;
    }

    public GameBuilder withExpectedReleaseDay(Integer expectedReleaseDay) {
        this.expectedReleaseDay = expectedReleaseDay;
        return this;
    }

    public GameBuilder withExpectedReleaseMonth(Integer expectedReleaseMonth) {
        this.expectedReleaseMonth = expectedReleaseMonth;
        return this;
    }

    public GameBuilder withExpectedReleaseYear(Integer expectedReleaseYear) {
        this.expectedReleaseYear = expectedReleaseYear;
        return this;
    }

    public GameBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Game build() {
        return new Game(id, name, aliases, image, deck, originalReleaseDate, platforms,
                expectedReleaseDay, expectedReleaseMonth, expectedReleaseYear, description);
    }
}
