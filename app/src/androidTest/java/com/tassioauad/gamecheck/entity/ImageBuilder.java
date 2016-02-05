package com.tassioauad.gamecheck.entity;

import com.tassioauad.gamecheck.model.entity.Image;

public class ImageBuilder {

    public static final String DEFAULT_IMAGE_URL = "http://www.dingdingtv.com/wp-content/uploads/2016/01/default_post_img2.png";

    private String iconUrl;

    private String mediumUrl;

    private String screenUrl;

    private String smallUrl;

    private String superUrl;

    private String thumbUrl;

    private String tinyUrl;

    public static ImageBuilder aImage() {
        return new ImageBuilder()
                .withIconUrl(DEFAULT_IMAGE_URL)
                .withMediumUrl(DEFAULT_IMAGE_URL)
                .withScreenUrl(DEFAULT_IMAGE_URL)
                .withSmallUrl(DEFAULT_IMAGE_URL)
                .withSuperUrl(DEFAULT_IMAGE_URL)
                .withThumbUrl(DEFAULT_IMAGE_URL)
                .withTinyUrl(DEFAULT_IMAGE_URL);
    }
    
    public ImageBuilder withIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public ImageBuilder withMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
        return this;
    }

    public ImageBuilder withScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
        return this;
    }

    public ImageBuilder withSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
        return this;
    }

    public ImageBuilder withSuperUrl(String superUrl) {
        this.superUrl = superUrl;
        return this;
    }

    public ImageBuilder withThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public ImageBuilder withTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
        return this;
    }

    public Image build() {
        return new Image(iconUrl, mediumUrl, screenUrl, smallUrl, superUrl, thumbUrl, tinyUrl);
    }
}
