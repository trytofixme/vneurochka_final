package com.example.vneurochka.model;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Group {
    private String name;
    private String description;
    private long creationDate;
    private String imageUrl;
    private List<String> users;

    public Group() {}

    public Group(String name, String description, long creationDate, String imageUrl) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.imageUrl = imageUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return this.users;
    }
}
