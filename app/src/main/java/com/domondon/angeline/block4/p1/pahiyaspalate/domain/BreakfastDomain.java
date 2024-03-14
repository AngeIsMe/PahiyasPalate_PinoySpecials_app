package com.domondon.angeline.block4.p1.pahiyaspalate.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class BreakfastDomain {

    private String name;
    private String category;
    private String steps;
    private String ingredients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String views;

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    private String recipe_description;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }


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



    public BreakfastDomain(String id,String name, String category, String recipe_description, String steps,String ingredients, String views){
        this.id = id;
        this.name = name;
        this.category = category;
        this.recipe_description = recipe_description;
        this.steps = steps;
        this.ingredients = ingredients;
        this.views = views;
    }
}
