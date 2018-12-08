package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

/*
question_id
title
text
type (single answer / multiple answer / freeform answer)
[EK] remains to be established: will Questions be bound to a Questionnaire, or will we make a m2m table?
[AF] nu stiu ce caracteristici ar putea sa aiba in mod deosebt pentru ca practic in questionnaire vom avea un arraylist de intrebari
 */
public class Question {
    private int question_id;
    private String title;
    private String text;
    private String type;

    public Question(int id){
        question_id = id;
    }

    public Question(String title, String text, String type){
        this.title = title;
        this.text = text;
        this.type = type;
    }
}
