package com.domondon.angeline.block4.p1.pahiyaspalate.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;

public class BreakfastDomain {

    private String id;
    private String name;
    private String category;
    private String recipe_description;
    private String level;
    private String author;
    private String steps;
    private String ingredients;
    private String views;
    private String imageUrl; // Add image URL field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BreakfastDomain(String id, String name, String category, String recipe_description, String level, String steps, String ingredients, String views, String author, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.recipe_description = recipe_description;
        this.level = level;
        this.author = author;
        this.steps = steps;
        this.ingredients = ingredients;
        this.views = views;
        this.imageUrl = imageUrl; // Assign the image URL
    }
}
