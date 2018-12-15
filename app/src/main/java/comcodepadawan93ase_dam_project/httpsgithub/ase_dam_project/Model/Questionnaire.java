package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

/*
questionnaire_id
title
nr_of_questions [EK]
date_start
date_end (if they are empty it's always available)
hash_code (unique code through which users can access questionnaire)
public (true/false)
 */

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;

public class Questionnaire {
    final public static String TYPE_TAG = "questionnaire";
    private String questionnaire_id;
    private String title;
    private long date_start;
    private long date_end;
    private String type;
    private String hash_code;
    private boolean is_public;
    private ArrayList<String> questions;

    public Questionnaire(){
    }

    public Questionnaire(String id){
        this.questionnaire_id = id;
    }

    public void setQuestionnaire_id(String questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate_start(long date_start) {
        this.date_start = date_start;
    }

    public void setDate_end(long date_end) {
        this.date_end = date_end;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHash_code(String hash_code) {
        this.hash_code = hash_code;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public Questionnaire(String title, String dateStart, String dateEnd, String type, String hashCode, boolean isPublic, ArrayList<String> questions)
    throws InvalidModelExeption {
        boolean valid = true;
        if(title == null || "".equals(title)){
            valid = false;
        }
        if(dateStart == null || "".equals(dateStart)){
            valid = false;
        }
        if(dateEnd == null || "".equals(dateEnd)){
            valid = false;
        }
        if(title == null || "".equals(title)){
            valid = false;
        }
        if(valid) {
            //this.questionnaire_id = questionnaireId;
            this.title = title;
            try {
                this.date_start = DateTimeParser.parseDate(dateStart);
                this.date_end = DateTimeParser.parseDate(dateEnd);
            } catch (Exception e){
                throw new InvalidModelExeption("Invalid data was supplied: " + e.getMessage());
            }
            this.hash_code = hashCode;
            this.is_public = isPublic;
            this.questions = questions;

        } else {
            throw new InvalidModelExeption("Invalid data was supplied, please check your input and try again.");
        }
    }

    // Save an existing instance to firebase
    public String save(DatabaseReference db){
        String id = db.push().getKey();
        db.child(id).setValue(this);
        return id;
    }

    public String update(DatabaseReference db){
        db.child(this.questionnaire_id).setValue(this);
        return this.questionnaire_id;
    }

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }

    public String getTitle() {
        return title;
    }

    public long getDate_start() {
        return date_start;
    }

    public long getDate_end() {
        return date_end;
    }

    public String getType() {
        return type;
    }

    public String getHash_code() {
        return hash_code;
    }

    public boolean isIs_public() {
        return is_public;
    }
}
