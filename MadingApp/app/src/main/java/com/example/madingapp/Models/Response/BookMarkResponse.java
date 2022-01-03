package com.example.madingapp.Models.Response;

import java.util.List;

public class BookMarkResponse {
    private int code;
    private boolean status;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<Bookmark> bookmarks;

        public List<Bookmark> getBookmarks() {
            return bookmarks;
        }

        public void setBookmarks(List<Bookmark> bookmarks) {
            this.bookmarks = bookmarks;
        }
    }

    public class Bookmark {
        private String bookMarkId;
        private User user;
        private Mading mading;
        private String createdBy;
        private String createdDate;
        private String modifiedBy;
        private String modifiedDate;

        public String getBookMarkId() {
            return bookMarkId;
        }

        public void setBookMarkId(String bookMarkId) {
            this.bookMarkId = bookMarkId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Mading getMading() {
            return mading;
        }

        public void setMading(Mading mading) {
            this.mading = mading;
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
    }

    public class User {
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
    }

    public class Mading {
        private String madingId;
        private String cateogryId;
        private String title;
        private String description;
        private String madingImage;
        private int likes;
        private int dislikes;
        private String reportId;
        private int reportCount;
        private String createdBy;
        private String authors;
        private String createdDate;
        private String modifiedBy;
        private String modifiedDate;

        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }

        public String getMadingId() {
            return madingId;
        }

        public void setMadingId(String madingId) {
            this.madingId = madingId;
        }

        public String getCateogryId() {
            return cateogryId;
        }

        public void setCateogryId(String cateogryId) {
            this.cateogryId = cateogryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMadingImage() {
            return madingImage;
        }

        public void setMadingImage(String madingImage) {
            this.madingImage = madingImage;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getDislikes() {
            return dislikes;
        }

        public void setDislikes(int dislikes) {
            this.dislikes = dislikes;
        }

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public int getReportCount() {
            return reportCount;
        }

        public void setReportCount(int reportCount) {
            this.reportCount = reportCount;
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
                    "madingId='" + madingId + '\'' +
                    ", cateogryId='" + cateogryId + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", madingImage='" + madingImage + '\'' +
                    ", likes=" + likes +
                    ", dislikes=" + dislikes +
                    ", reportId='" + reportId + '\'' +
                    ", reportCount=" + reportCount +
                    ", createdBy='" + createdBy + '\'' +
                    ", authors='" + authors + '\'' +
                    ", createdDate='" + createdDate + '\'' +
                    ", modifiedBy='" + modifiedBy + '\'' +
                    ", modifiedDate='" + modifiedDate + '\'' +
                    '}';
        }
    }
}
