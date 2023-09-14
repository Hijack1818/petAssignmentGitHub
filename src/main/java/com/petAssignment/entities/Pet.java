package com.petAssignment.entities;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pet {
    private long id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private String status;

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("photoUrls")
    public String[] getPhotoUrls() {
        return photoUrls;
    }

    @JsonProperty("photoUrls")
    public void setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

    @JsonProperty("tags")
    public Tag[] getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Pet [id=" + id + ", category=" + category + ", name=" + name + ", photoUrls="
				+ Arrays.toString(photoUrls) + ", tags=" + Arrays.toString(tags) + ", status=" + status + "]";
	}
    
    // Constructors can also be defined if needed
}



