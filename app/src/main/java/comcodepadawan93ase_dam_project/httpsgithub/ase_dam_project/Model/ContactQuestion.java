package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

public class ContactQuestion {
    public static final String NAME_TAG = "name";
    public static final String EMAIL_TAG = "email";
    public static final String QUESTION_TAG = "question";

    private String name;
    private String email;
    private String question;

    public ContactQuestion(){
    }

    public ContactQuestion(String name, String email, String question){
        this.name = name;
        this.email = email;
        this.question = question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String toJson(){
        return String.format("{\"name\":\"%s\",\"email\":\"%s\",\"question\":\"%s\"}", this.name, this.email, this.question);
    }
}
