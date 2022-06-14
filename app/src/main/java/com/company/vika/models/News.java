package com.company.vika.models;

import android.net.Uri;

public class News {
    private final String Title, Description, Image, Data;

    public News(String title, String description, String image, String Data) {
        Title = title;
        this.Data = Data;
        Description = description;
        Image = image;
    }

    public String getData() {
        return Data;
    }

    public String getDescription() {
        return Description;
    }

    public String getTitle() {
        return Title;
    }

    public Uri getImage(){
        return Uri.parse(Image);
    }
}
