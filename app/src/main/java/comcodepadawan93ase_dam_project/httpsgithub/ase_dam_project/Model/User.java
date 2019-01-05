package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

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

    public void setUser_id(String user_id) {
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

    public String getUserNameSign() {
        return userNameSign;
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

    public static User getCurrentUser(AppCompatActivity context){
        SharedPreferences sharedPref = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String id = sharedPref.getString("user_id", "");
        String username = sharedPref.getString("username", "");
        String password = sharedPref.getString("password", "");
        String signUpName = sharedPref.getString("sign_up_name", "");
        String email = sharedPref.getString("email", "");
        String role = sharedPref.getString("role", "Student");
        User user = new User(username, password, signUpName, email, role);
        user.setUser_id(id);
        return user;
    }

}
