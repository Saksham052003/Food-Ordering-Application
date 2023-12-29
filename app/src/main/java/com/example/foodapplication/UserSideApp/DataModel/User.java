package com.example.foodapplication.UserSideApp.DataModel;

public class User {
        public String username;
        public String userphone;
        public String useremail;
        public String userimageUrl;

        public User() {
        }

        public User(String username, String userphone, String useremail, String userimageUrl) {
            this.username = username;
            this.userphone = userphone;
            this.useremail = useremail;
            this.userimageUrl = userimageUrl;
        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserimageUrl() {
        return userimageUrl;
    }

    public void setUserimageUrl(String userimageUrl) {
        this.userimageUrl = userimageUrl;
    }
}
