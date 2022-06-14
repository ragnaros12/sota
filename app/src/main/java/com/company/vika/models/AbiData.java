package com.company.vika.models;

import java.util.List;

public class AbiData {
    private String title;
    private boolean isTitle;

    public AbiData(String title, boolean isTitle) {
        this.title = title;
        this.isTitle = isTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
