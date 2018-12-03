package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

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
        EditText questionet = (EditText)findViewById(R.id.questionet);
        EditText firstet=(EditText)findViewById(R.id.firstet);
        EditText secondet=(EditText)findViewById(R.id.secondet);
        EditText thirdet=(EditText)findViewById(R.id.thirdet);
        EditText fourthet=(EditText)findViewById(R.id.fourthet);

        if(questionet != null && firstet !=null && secondet != null){
            if("".equals(questionet.getText().toString()) || "".equals(firstet.getText().toString()) || "".equals(secondet.getText().toString())){
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this);
                builder.setTitle("Error");
                builder.setMessage("Both fields are mandatory!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else{

            QuestionFormProfActivity questionFormProfActivity = new QuestionFormProfActivity(questionet.getText().toString(),
                    firstet.getText().toString(),secondet.getText().toString(),thirdet.getText().toString(),fourthet.getText().toString());

            Intent intent = new Intent();
            intent.putExtra("ques", (Serializable) questionFormProfActivity);
            setResult(RESULT_OK, intent);
            finish();
        }



    }
}