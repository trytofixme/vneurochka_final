package com.example.vneurochka.model;

import java.util.Date;

public class User {
    private String id;
    private String email;
    private String displayName;
    private Date registrationDate;
    private String imageUrl;
    private String status;

    public User() {}

    public User(String id, String email, String displayName, Date registrationDate, String imageUrl,
                String status) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.registrationDate = registrationDate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String login)
    {
        this.displayName = login;
    }

    public Date getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
