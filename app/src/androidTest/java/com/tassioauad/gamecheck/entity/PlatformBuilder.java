package com.tassioauad.gamecheck.entity;


import com.tassioauad.gamecheck.model.entity.Company;
import com.tassioauad.gamecheck.model.entity.Image;
import com.tassioauad.gamecheck.model.entity.Platform;

import java.util.Date;

public class PlatformBuilder {

    public static final Long DEFAULT_ID = 94l;

    public static final String DEFAULT_NAME = "Commodore";

    private Long id;

    private String name;

    private String aliases;

    private String abbreviation;

    private Company company;

    private String deck;

    private Image image;

    private Date releaseDate;

    private String description;
    
    public static PlatformBuilder aPlatform() {
        return new PlatformBuilder()
                .withId(DEFAULT_ID)
                .withName(DEFAULT_NAME);
    }

    public PlatformBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PlatformBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PlatformBuilder withAliases(String aliases) {
        this.aliases = aliases;
        return this;
    }

    public PlatformBuilder withAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public PlatformBuilder withCompany(Company company) {
        this.company = company;
        return this;
    }

    public PlatformBuilder withDeck(String deck) {
        this.deck = deck;
        return this;
    }

    public PlatformBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public PlatformBuilder withReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public PlatformBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Platform build() {
        return new Platform(id, name, aliases, abbreviation, company, deck, image, releaseDate, description);
    }
}
