package com.example.user;

public class recipe_data {

    String[] ingredient;
    String[] recipe;
    int maxPage;
    int maxItem;

    public void recipe_data(String[] ingredient, String[] recipe, int maxPage, int maxItem){
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.maxPage = maxPage;
        this.maxItem = maxItem;
    }

    public String getRecipeData (int page){
        String result = new String();

        result = recipe[page];

        return result;
    }

    public int getMaxPage (){
        return maxPage;
    }

    public int getMaxItem(){
        return maxItem;
    }

}
