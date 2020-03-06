package com.secondhand;

/**
 * 用户id和phone的Pojo
 * Created by LeiMing on 2020/2/18 19:31
 */
public class UserInfo {
    private Long id;

    private String phone;

    private String name;

    private Integer sex;

    private Integer age;

    private String userhead;

    public UserInfo() {
    }

    public UserInfo(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public UserInfo(Long id,String phone,String name,Integer age,Integer sex,String userhead){
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.userhead = userhead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserhead() {
        return userhead;
    }

    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }
}
