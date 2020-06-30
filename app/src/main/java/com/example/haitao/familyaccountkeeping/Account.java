package com.example.haitao.familyaccountkeeping;

/**
 * Created by haitao on 2019/12/13.
 *

/**
 * Created by haitao on 2019/11/9.
 */

public class Account {
    private String name;
    private int imageId;
    public Account(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}


