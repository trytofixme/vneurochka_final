package com.example.vneurochka.model;

public class Message
{
    private String message, type, senderId, groupId;
    private long timestamp;
    private Boolean isSeen;

    public Message()
    {

    }

    public Message(String message, String type, String from, String groupId, long timestamp)
    {
        this.message = message;
        this.type = type;
        this.groupId = groupId;
        this.timestamp = timestamp;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public Boolean getSeen() { return isSeen; }

    public void setSeen(Boolean isSeen) { this.isSeen = isSeen; }
}
