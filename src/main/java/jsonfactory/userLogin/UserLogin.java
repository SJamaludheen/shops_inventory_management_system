package jsonfactory.userLogin;

import com.google.gson.annotations.Expose;

public class UserLogin {

    @Expose
    private String password;
    @Expose
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
