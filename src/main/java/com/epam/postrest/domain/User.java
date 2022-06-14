package com.epam.postrest.domain;

import java.util.Random;

public class User {

    private Long Id;
    private String name;
    private String email;
    private String gender;
    private String status;

    public User() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
            "Id=" + Id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", gender='" + gender + '\'' +
            ", status='" + status + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;

        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) {
            return false;
        }
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) {
            return false;
        }
        if (getGender() != null ? !getGender().equals(user.getGender()) : user.getGender() != null) {
            return false;
        }
        return getStatus() != null ? getStatus().equals(user.getStatus()) : user.getStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    public void randomGenerateProperties() {
        Random r = new Random();

        String alphabet = "abcdefgjklmnoprstuivbcxr";
        StringBuilder randomChars = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            randomChars.append((alphabet.charAt(r.nextInt(alphabet.length()))));
        }

        this.name = randomChars.toString();
        this.email = randomChars + "@z.com";
        this.gender = "male";
        this.status = "active";

    }

}
