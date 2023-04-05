package com.example.user;

import java.util.ArrayList;

public class recipe_data {

    ArrayList<recipeCooking> cooking_list;
    ArrayList<recipeIngredient> ingredient_list;
    recipe_info info;
    int maxPage;
    int maxItem;

    public recipe_data(ArrayList<recipeCooking> cooking_list, ArrayList<recipeIngredient> ingredient_list,recipe_info info){
        this.cooking_list = cooking_list;
        this.ingredient_list = ingredient_list;
        this.maxPage = cooking_list.size();
        this.maxItem = ingredient_list.size();
        this.info = info;
    }

    public String getRecipeData (int page){
        String result = new String();

        result = cooking_list.get(page).getCooking_order();

        return result;
    }

    public String getIngredient (int page){
        String result = new String();

        result = ingredient_list.get(page).getIngredient_Name();

        return result;
    }

    public String getIngredient_cp (int page){
        String result = new String();

        result = ingredient_list.get(page).getIngredient_Name();

        return result;
    }

    public ArrayList<recipeCooking> getCooking_list() {
        return cooking_list;
    }

    public void setCooking_list(ArrayList<recipeCooking> cooking_list) {
        this.cooking_list = cooking_list;
    }

    public ArrayList<recipeIngredient> getIngredient_list() {
        return ingredient_list;
    }

    public void setIngredient_list(ArrayList<recipeIngredient> ingredient_list) {
        this.ingredient_list = ingredient_list;
    }

    public recipe_info getInfo() {
        return info;
    }

    public void setInfo(recipe_info info) {
        this.info = info;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxItem() {
        return maxItem;
    }

    public void setMaxItem(int maxItem) {
        this.maxItem = maxItem;
    }
}
