package com.example.hardel.revin;

public class Notice {
    private String title, subtitle, description, date, imgPath, category;

    public Notice(String title, String description, String subtitle, String date, String category, String imgPath) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.date = date;
        this.category = category;
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getCate(){
        return category;
    }

    public String getImgPath(){
        return imgPath;
    }
}
