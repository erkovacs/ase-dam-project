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

public class Questionnaire {
    final public static String TYPE_TAG = "questionnaire";
    private String questionnaire_id;
    private String title;
    private int date_start;
    private int date_end;
    private String type;
    private String hash_code;
    private boolean is_public;

    public Questionnaire(){
    }

    public Questionnaire(String id){
        this.questionnaire_id = id;
    }

    public Questionnaire(String title, String dateStart, String dateEnd, String type, String hashCode, boolean isPublic, ArrayList<Question> questions)
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
            this.date_start = 100; // dateStart;
            this.date_end = 100; // dateEnd;
            this.hash_code = hashCode;
            this.is_public = isPublic;
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

    public int getDate_start() {
        return date_start;
    }

    public int getDate_end() {
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
