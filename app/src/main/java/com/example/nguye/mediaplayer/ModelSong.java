package com.example.nguye.mediaplayer;

public class ModelSong {
    private String title,singer,category;
    private int url;

    public ModelSong(String title, String singer, String category, int url) {
        this.title = title;
        this.singer = singer;
        this.category = category;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
