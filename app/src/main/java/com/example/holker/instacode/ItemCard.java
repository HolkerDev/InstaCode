package com.example.holker.instacode;

import android.graphics.Bitmap;

public class ItemCard {

    Bitmap profilePhoto;
    String name;
    Bitmap background;
    long followers;

    public ItemCard(Bitmap profilePhoto, String name, Bitmap background, long followers) {
        this.profilePhoto = profilePhoto;
        this.name = name;
        this.background = background;
        this.followers = followers;
    }

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBackground() {
        return background;
    }

    public long getFollowers() {
        return followers;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }
}
