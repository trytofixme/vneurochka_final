package com.example.vneurochka.model;

public class GroupType {
    private Integer groupTypeId;
    private String groupTypeName;

    public GroupType(Integer groupTypeId, String groupTypeName) {
        this.groupTypeId = groupTypeId;
        this.groupTypeName = groupTypeName;
    }
    public Integer getGroupTypeId(){return groupTypeId;}

    public void setGroupTypeId(Integer groupTypeId){this.groupTypeId = groupTypeId;}

    public String getGroupTypeName(){return groupTypeName;}

    public void setGroupTypeName(String groupTypeName){this.groupTypeName = groupTypeName;}
}