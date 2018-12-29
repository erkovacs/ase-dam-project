package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class QuestionFormProfActivity extends AppCompatActivity {
    public String question;
    public String answear1;
    public String answear2;
    public String answear3;
    public String answear4;

    public Switch answerCorrect1;
    public Switch answerCorrect2;
    public Switch answerCorrect3;
    public Switch answerCorrect4;
    private ArrayList<Switch> answerSwitches;

    private int onSwitch;

    private boolean isNew;

    private DatabaseReference databaseQuestion;
    private QuestionFormProfActivity context;
    private String questionId;

    private Button addquestionbtn;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form_prof);
        context = this;

        // Get the correct answer Switches
        answerCorrect1 = findViewById(R.id.answer_correct_1);
        answerCorrect2 = findViewById(R.id.answer_correct_2);
        answerCorrect3 = findViewById(R.id.answer_correct_3);
        answerCorrect4 = findViewById(R.id.answer_correct_4);
        answerSwitches = new ArrayList<Switch>(Arrays.asList(answerCorrect1, answerCorrect2, answerCorrect3, answerCorrect4));

        // Get Firebase ref
        databaseQuestion = FirebaseDatabase.getInstance().getReference(Question.TYPE_TAG);

        Intent intent = getIntent();
        questionId = intent.getStringExtra(ProjectIdentifier.BUNDLE_PREFIX + ".question_id");

        isNew = questionId == null;
        if(!isNew){
            // Get the current Qurestionnaire
            databaseQuestion.child(questionId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    populateQuestion(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Init button
        addquestionbtn = (Button) findViewById(R.id.addquestionbtn);
        addquestionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }

        });

        // Apply logic to switches
        int i = 0;
        for (Switch answerSwitch : answerSwitches){
            answerSwitch.setTag(i);
            answerSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetSwitches(v);
                }
            });
            i++;
        }
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


    public void addQuestion() {
        final EditText questionet = (EditText) findViewById(R.id.questionet);
        final EditText firstet = (EditText) findViewById(R.id.firstet);
        final EditText secondet = (EditText) findViewById(R.id.secondet);
        final EditText thirdet = (EditText) findViewById(R.id.thirdet);
        final EditText fourthet = (EditText) findViewById(R.id.fourthet);
        final EditText timeet = (EditText) findViewById(R.id.question_time);

        final String questionetString = questionet.getText().toString();
        final String firstetString = firstet.getText().toString();
        final String secondetString = secondet.getText().toString();
        final String thirdetString = thirdet.getText().toString();
        final String fourthetString = fourthet.getText().toString();
        final String timeString = timeet.getText().toString();
        int timeInt = 0;
        if(!"".equals(timeString)){
            try{
                timeInt = Integer.parseInt(timeString);
                timeInt = Math.abs(timeInt);
            }catch (Exception e){
                timeet.setError(e.getMessage());
            }
        }
        // Get time in millis
        if(timeInt != 0){
            timeInt *= 1000;
        }

        if (questionetString.isEmpty() || questionetString == null) {
            questionet.setError("You need to insert a question!");
            firstet.setError("You need to insert at least an answer");
            secondet.setError("You need to insert at least two answers");
        } else {
            try {
                Question question = new Question(questionetString, questionetString, "", firstetString, secondetString, thirdetString, fourthetString, onSwitch, timeInt);
                if(isNew) {
                    question.save(databaseQuestion);
                } else {
                    question.setQuestion_id(questionId);
                    question.update(databaseQuestion);
                }
                Toast.makeText(this, "Question " + (isNew ? "created" : "updated") + " successfully!", Toast.LENGTH_LONG).show();
                finish();
            } catch (InvalidModelExeption ime){
                Toast.makeText(context, ime.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void populateQuestion(DataSnapshot dataSnapshot){
        // Populate the form with the appropriate data
        try {
            Question thisQuestion = dataSnapshot.getValue(Question.class);
            final EditText questionet = (EditText) findViewById(R.id.questionet);
            final EditText firstet = (EditText) findViewById(R.id.firstet);
            final EditText secondet = (EditText) findViewById(R.id.secondet);
            final EditText thirdet = (EditText) findViewById(R.id.thirdet);
            final EditText fourthet = (EditText) findViewById(R.id.fourthet);
            final EditText timeet = (EditText) findViewById(R.id.question_time);

            questionet.setText(thisQuestion.getTitle());
            firstet.setText(thisQuestion.answear1);
            secondet.setText(thisQuestion.answear2);
            thirdet.setText(thisQuestion.answear3);
            fourthet.setText(thisQuestion.answear4);
            int timeInMillis = thisQuestion.time;
            if(timeInMillis != 0){
                timeet.setText(String.format("%d", (timeInMillis / 1000)));
            }

            answerSwitches.get(thisQuestion.getCorrect_answer()).setChecked(true);

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void resetSwitches(View v){

        int tag = Integer.parseInt(v.getTag().toString());
        Log.d("TAG SWITCH", tag + "");
        onSwitch = tag;
        int i = 0;
        for(Switch _switch : answerSwitches){
            if(i != tag){
                _switch.setChecked(false);
            } else {
                _switch.setChecked(true);
            }
            i++;
        }
    }
}