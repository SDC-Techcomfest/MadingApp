package com.example.madingapp.Models.Response;

import java.util.List;

public class MeResponse {

    private int code;
    private boolean status;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        public String userId;
        public String schoolId;
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public int gender;
        public String dateOfBirth;
        public String userImage;
        public String Token;
        public String createdBy;
        public String createdDate;
        public String modifiedBy;
        public String modifiedDate;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "userId='" + userId + '\'' +
                    ", schoolId='" + schoolId + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", gender=" + gender +
                    ", dateOfBirth='" + dateOfBirth + '\'' +
                    ", userImage='" + userImage + '\'' +
                    ", Token='" + Token + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    ", createdDate='" + createdDate + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", modifiedDate='" + modifiedDate + '\'' +
                    '}';
        }
    }
}
