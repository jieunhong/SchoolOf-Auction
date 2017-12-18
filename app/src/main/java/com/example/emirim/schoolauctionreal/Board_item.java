package com.example.emirim.schoolauctionreal;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class Board_item {
    String title;
    String content;
    public Board_item()
    {

    }

    public Board_item(String title, String content){
        this.title=title;
        this.content=content;
    }
    public void setTitle(String mTitle){
        this.title=mTitle;
    }
    public void setContent(String mContent){
        this.content=mContent;
    }
    public String getTitle()
    {
        return title;
    }

    public  String getContent()
    {
        return content;
    }
}
