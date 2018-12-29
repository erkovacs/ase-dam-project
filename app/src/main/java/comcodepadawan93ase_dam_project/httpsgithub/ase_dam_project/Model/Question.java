package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

import com.google.firebase.database.DatabaseReference;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;

/*
question_id
title
text
type (single answer / multiple answer / freeform answer)

 */
public class Question {
    final public static String TYPE_TAG = "question";

    private String question_id;
    private String title;
    private String text;
    private String type;
    public String answear1;
    public String answear2;
    public String answear3;
    public String answear4;
    public int correct_answer;
    public int time;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Question(){
        this.title = "";
        this.text = "";
        this.type = "";
    }


    public Question(String id){
        question_id = id;
        this.title = "";
        this.text = "";
        this.type = "";
    }

    public Question(String title, String text, String type, String answear1, String answear2, String answear3, String answear4, int correct_answer, int time) throws InvalidModelExeption {
        this.title = title;
        this.text = text;
        this.type = type;
        this.answear1 = answear1;
        this.answear2 = answear2;
        this.answear3 = answear3;
        this.answear4 = answear4;
        this.time = time;
        if(correct_answer > -1 && correct_answer < 4) {
            this.correct_answer = correct_answer;
        } else {
            throw new InvalidModelExeption("Invalid correct answer provided. Should be 1 through 4 as there are 4 questions.");
        }
    }

    // Save a new instance to firebase
    public String save(DatabaseReference db){
        String id = db.push().getKey();
        db.child(id).setValue(this);
        return id;
    }

    // Update an existing instance in firebase
    public String update(DatabaseReference db){
        db.child(this.question_id).setValue(this);
        return this.question_id;
    }
}
