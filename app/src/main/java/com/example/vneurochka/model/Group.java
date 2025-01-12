package com.example.vneurochka.model;

import java.util.List;

public class Group {
    private String id;
    private String name;
    private String description;
    private long creationDate;
    private String imageUrl;
    private List<String> userIds;

    public Group() {}

    public Group(String id, String name, String description, long creationDate, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.imageUrl = imageUrl;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }
}
