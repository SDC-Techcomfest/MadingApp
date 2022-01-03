package com.example.madingapp.Models.Response;

import java.util.List;

public class MadingResponse {
    private int code;
    private boolean status;
    private String message;
    private List<Data> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MadingResponse{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
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
