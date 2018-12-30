package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class User {
    public static final String TYPE_TAG = "user";
    private String user_id;
    private String userName;
    private String password;
    private String userNameSign;
    private String userEmail;
    private String role;

    public User(){
        this.userName = "";
        this.password = "";
        this.userNameSign = "";
        this.userEmail = "";
        this.role = "";
    }

    public User(String id){
        this.user_id = id;
    }

    public User(String userName, String password, String firstName, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.userNameSign = firstName;
        this.userEmail = email;
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserNameSign(String userNameSign) {
        this.userNameSign = userNameSign;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNameSign() {
        return userNameSign;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String save(DatabaseReference db){
        String id = db.push().getKey();
        db.child(id).setValue(this);
        return id;
    }

    // Update an existing instance in firebase
    public String update(DatabaseReference db){
        db.child(this.user_id).setValue(this);
        return this.user_id;
    }

}
