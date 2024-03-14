package com.domondon.angeline.block4.p1.pahiyaspalate.domain;

public class SnacksDomain {
    private String name;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SnacksDomain(String name, String category){

        this.name = name;
        this.category = category;
    }
}
