package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuestionFormProfActivity extends AppCompatActivity {
    public String question;
    public String answear1;
    public String answear2;
    public String answear3;
    public String answear4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form_prof);
    }

    public QuestionFormProfActivity(){

    }

    public QuestionFormProfActivity(String question, String answear1, String answear2, String answear3, String answear4) {
        this.question = question;
        this.answear1 = answear1;
        this.answear2 = answear2;
        this.answear3 = answear3;
        this.answear4 = answear4;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getAnswear1() { return answear1; }

    public void setAnswear1(String answear1) { this.answear1 = answear1; }

    public String getAnswear2() { return answear2; }

    public void setAnswear2(String answear2) { this.answear2 = answear2; }

    public String getAnswear3() { return answear3; }

    public void setAnswear3(String answear3) { this.answear3 = answear3; }

    public String getAnswear4() { return answear4; }

    public void setAnswear4(String answear4) { this.answear4 = answear4; }


    public void AddQuestion(View view){
        final EditText questionet = (EditText)findViewById(R.id.questionet);
        final EditText firstet=(EditText)findViewById(R.id.firstet);
        final EditText secondet=(EditText)findViewById(R.id.secondet);
        final EditText thirdet=(EditText)findViewById(R.id.thirdet);
        final EditText fourthet=(EditText)findViewById(R.id.fourthet);
        Button addquestionbtn=(Button)findViewById(R.id.addquestionbtn);

        final String Questionet = questionet.getText().toString();
        final String Firstet = firstet.getText().toString();
        final String Secondet=secondet.getText().toString();
        final String Thirdet=thirdet.getText().toString();
        final String Fourthet=fourthet.getText().toString();

        addquestionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(Questionet.isEmpty() || Questionet==null){
                  questionet.setError("You need to insert a question!");
                  firstet.setError("You need to insert at least an answer");
                  secondet.setError("You need to insert at least two answers");
              }
              else{
                  Bundle b = new Bundle();

                  b.putString("question",Questionet);
                  b.putString("first answer", Firstet);
                  b.putString("second answer",Secondet);
                  b.putString("third answer", Thirdet);
                  b.putString("fourth answer", Fourthet);

                  Intent in = new Intent(QuestionFormProfActivity.this, QuestionnaireListActivity.class);
                  in.putExtras(b);
                  startActivity(in);
              }
            }

        });





    }
}