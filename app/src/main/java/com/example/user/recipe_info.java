package com.example.user;

public class recipe_info {

    int ID;
    String Name;
    String Url;
    String tag;

    public recipe_info(int ID,String Url,String Name,String tag){
        this.ID = ID;
        this.Name = Name;
        this.Url = Url;
        this.tag = tag;
    }

    public recipe_info(String Name, String Url){
        this.Name = Name;
        this.Url = Url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
