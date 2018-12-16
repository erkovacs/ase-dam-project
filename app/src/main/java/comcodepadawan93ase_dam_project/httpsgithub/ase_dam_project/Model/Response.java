package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * response_id
 * questionnaire_id
 * completed_on
 * nr_of_correct_answ [EK] not sufficient to remeber the score, we should remember the whole playthrough
 * answers (a LONGTEXT field cotaining a JSON with the playthrough data)
 * user_id //[AF] ?? acesta va fi folosit pentru history
 */
public class Response {
    public static final String TYPE_TAG = "response";
    private String response_id;
    private String questionnaire_id;
    private long completed_on;
    private ArrayList<String> questions;
    private ArrayList<Integer> answers;
    private ArrayList<Boolean> correctAnswers;
    private int score;
    private int user_id;

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }

    public void setQuestionnaire_id(String questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public long getCompleted_on() {
        return completed_on;
    }

    public void setCompleted_on(long completed_on) {
        this.completed_on = completed_on;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Response(){
    }

    public Response(String id){
        response_id = id;
    }

    public Response(String questionnaireId, long completedOn, ArrayList<String> questions, ArrayList<Integer> answers, ArrayList<Boolean> correctAnswers, int score, int userId){
         this.questionnaire_id = questionnaireId;
         this.completed_on = completedOn;
         this.questions = questions;
         this.answers = answers;
         this.correctAnswers = correctAnswers;
         this.score = score;
         this.user_id = userId;
    }

    // Save a new instance to firebase
    public String save(DatabaseReference db){
        String id = db.push().getKey();
        db.child(id).setValue(this);
        return id;
    }

    public ArrayList<Boolean> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<Boolean> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    // Update an existing instance in firebase
    public String update(DatabaseReference db){
        db.child(this.response_id).setValue(this);
        return this.response_id;
    }
}
