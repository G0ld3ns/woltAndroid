package com.example.woltandroid.model;



import java.time.LocalDate;



public class Review {

    private int id;
    private int rating;
    private String reviewText;
    private LocalDate dateCreated;

    private User commentOwner;

    private User feedBack;

    private Chat chat;

    public Review(int id, int rating, String reviewText, LocalDate dateCreated, User commentOwner, User feedBack, Chat chat) {
        this.id = id;
        this.rating = rating;
        this.reviewText = reviewText;
        this.dateCreated = dateCreated;
        this.commentOwner = commentOwner;
        this.feedBack = feedBack;
        this.chat = chat;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(User commentOwner) {
        this.commentOwner = commentOwner;
    }

    public User getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(User feedBack) {
        this.feedBack = feedBack;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return commentOwner.getLogin() + ": " + reviewText;
    }
}
