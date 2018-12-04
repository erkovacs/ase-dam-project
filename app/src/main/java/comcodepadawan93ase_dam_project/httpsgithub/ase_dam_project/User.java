package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

public class User {
    private  int user_id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    public User(){
    }

    public User(int id){
        this.user_id = id;
    }

    public User(int user_id, String userName, String password, String firstName, String lastName, String role) {
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        userName.hashCode();
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
