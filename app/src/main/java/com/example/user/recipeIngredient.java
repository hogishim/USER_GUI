package com.example.user;

public class recipeIngredient {

    int idx_ing;
    String ingredient_Name;
    int total_list_ID;

    public recipeIngredient(int idx_ing, String ingredient_Name, int total_list_ID) {
        this.idx_ing = idx_ing;
        this.ingredient_Name = ingredient_Name;
        this.total_list_ID = total_list_ID;
    }

    public int getIdx_ing() {
        return idx_ing;
    }

    public void setIdx_ing(int idx_ing) {
        this.idx_ing = idx_ing;
    }

    public String getIngredient_Name() {
        return ingredient_Name;
    }

    public void setIngredient_Name(String ingredient_Name) {
        this.ingredient_Name = ingredient_Name;
    }


    public int getTotal_list_ID() {
        return total_list_ID;
    }

    public void setTotal_list_ID(int total_list_ID) {
        this.total_list_ID = total_list_ID;
    }
}
