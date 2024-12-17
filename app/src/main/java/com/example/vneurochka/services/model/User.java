package com.example.vneurochka.services.model;

public class User {
    private String id;
    private String login;
    private String email;
    private String registrationDate;
    private String imageUrl;
    //private String displayName;
    //private String status;

    public User() {}

    public User(String id, String login, String email, String registrationDate, String imageUrl)
    {
        this.id = id;
        this.login = login;
        this.email = email;
        this.registrationDate = registrationDate;
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

    public String getUsername()
    {
        return login;
    }

    public void setUserLogin(String login)
    {
        this.login = login;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate)
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
}
