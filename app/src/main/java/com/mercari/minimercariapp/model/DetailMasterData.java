package com.mercari.minimercariapp.model;

public class DetailMasterData {

    private String id;
    private String name;
    private String status;
    private String num_likes;
    private String num_comments;
    private String price;
    private String photo;

    public DetailMasterData() {
    }


    public DetailMasterData(String id, String name, String status, String num_likes, String num_comments, String price, String photo) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.num_likes = num_likes;
        this.num_comments = num_comments;
        this.price = price;
        this.photo = photo;
    }


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(String num_likes) {
        this.num_likes = num_likes;
    }

    public String getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(String num_comments) {
        this.num_comments = num_comments;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
