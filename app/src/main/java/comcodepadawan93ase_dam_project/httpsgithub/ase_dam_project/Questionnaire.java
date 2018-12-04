package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

/*
questionnaire_id
title
nr_of_questions [EK]
date_start
date_end (if they are empty it's always available)
hash_code (unique code through which users can access questionnaire)
public (true/false)
 */

public class Questionnaire {
    private int questionnaire_id;
    private String title;
    private int date_start;
    private int date_end;
    private String hash_code;
    private boolean is_public;

    public Questionnaire(int id){
        this.questionnaire_id = id;
    }

    public Questionnaire(String title, int dateStart, int dateEnd, String hashCode, boolean isPublic){
        //this.questionnaire_id = questionnaireId;
        this.title = title;
        this.date_start = dateStart;
        this.date_end = dateEnd;
        this.hash_code = hashCode;
        this.is_public = isPublic;
    }
}
