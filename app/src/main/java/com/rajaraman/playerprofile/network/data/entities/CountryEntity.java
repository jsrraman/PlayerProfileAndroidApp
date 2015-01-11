// Created by rajaraman on Dec 25, 2014

package com.rajaraman.playerprofile.network.data.entities;

public class CountryEntity {
    private String thumbnailUrl;
    private int countryId;
    private String name;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEntity() {}

    public CountryEntity(String thumbnailUrl, int countryId, String name) {
        this.thumbnailUrl = thumbnailUrl;
        this.countryId = countryId;
        this.name = name;
    }
}