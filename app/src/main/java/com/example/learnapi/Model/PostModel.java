package com.example.learnapi.Model;


public class PostModel {

    private Integer id;
    private String lastupdate;
    private String caption;
    private String video;
    private String image;
    private Integer upvotes;
    private Integer downvotes;
    private Integer views;
    private String description;
    private String ingredients;
    private String steps;
    private Integer author;
    private Integer category;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PostModel(Integer id, String lastupdate, String caption, String video, String image, Integer upvotes, Integer downvotes, Integer views, String description, String ingredients, String steps, Integer author, Integer category, String username) {
        this.id = id;
        this.lastupdate = lastupdate;
        this.caption = caption;
        this.video = video;
        this.image = image;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.views = views;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.author = author;
        this.category = category;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
