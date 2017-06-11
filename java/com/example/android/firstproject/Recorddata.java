package com.example.android.firstproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administer on 01-06-2017.
 */

public class Recorddata extends ArrayList<String> {

    private List<String> post_imagesList;
    private int post_imagesSize;
    private String[] post_images;
    private String full_name;
    private String post_title;
    private String post_id;

    public Recorddata(String post_id, String post_title, String full_name, String[] post_images, int post_imagesSize) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.full_name = full_name;
        this.post_images = Arrays.copyOf(post_images, post_images.length);
        this.post_imagesSize = post_imagesSize;
    }

    public Recorddata(ArrayList<String> post_imagesList) {
        this.post_imagesList = post_imagesList;
    }

    public Recorddata(String post_id, String post_title, String full_name, String[] post_images, int post_imagesSize, ArrayList<String> post_imagesList) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.full_name = full_name;
        this.post_images = Arrays.copyOf(post_images, post_imagesSize);
        this.post_imagesSize = post_imagesSize;
        this.post_imagesList =  new ArrayList<String>(post_imagesList);
//        Collections.copy(this.post_imagesList,post_imagesList);
    }

    public String[] getPost_images() {
        return Arrays.copyOf(post_images, post_images.length);
    }

    public void setPost_images(String[] post_images) {
        this.post_images = post_images;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public int getPost_imagesSize() {
        return post_imagesSize;
    }

    public void setPost_imagesSize(int post_imagesSize) {
        this.post_imagesSize = post_imagesSize;
    }

    public List<String> getPost_imagesList() {
//        return post_imagesList;
        return new ArrayList<String>(this.post_imagesList);
    }

    public void setPost_imagesList(List<String> post_imagesList) {
//        this.post_imagesList = post_imagesList;
        this.post_imagesList = new ArrayList<String>(post_imagesList);
    }
}
