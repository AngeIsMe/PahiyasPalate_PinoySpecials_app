package com.domondon.angeline.block4.p1.pahiyaspalate.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class topTenDomain {

    private String name;
    private String category;
    private Integer views_count;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getViews_count() {

        return views_count;
    }

    public void setViews_count(Integer views_count) {
        this.views_count = views_count;
    }


    public topTenDomain(String name, String category, Integer views_count){
        this.name = name;
        this.category = category;
        this.views_count = views_count;
    }
}
