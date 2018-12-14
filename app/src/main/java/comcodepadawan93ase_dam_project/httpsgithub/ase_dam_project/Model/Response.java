package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model;

/**
 * response_id
 * questionnaire_id
 * completed_on
 * nr_of_correct_answ [EK] not sufficient to remeber the score, we should remember the whole playthrough
 * answers (a LONGTEXT field cotaining a JSON with the playthrough data)
 * user_id //[AF] ?? acesta va fi folosit pentru history
 */
public class Response {
    private int response_id;
    private String questionnaire_id;
    private Questionnaire questionnaire;
    private int completed_on;
    private String answers;
    private int user_id;
    private User user;

    public Response(int id){
        response_id = id;
    }

    public Response(String questionnaireId, int completedOn, String answers, int userId){
         // response_id ;
         this.questionnaire_id = questionnaireId;
         this.questionnaire = new Questionnaire(questionnaireId);
         this.completed_on = completedOn;
         this.answers = answers;
         this.user_id = userId;
         this.user = new User();
    }
}
