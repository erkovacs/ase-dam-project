package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class SingleAnswerActivity extends AppCompatActivity {

    private SingleAnswerActivity context;

    private ArrayList<String> questionIds;

    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvNoQuestion;
    private TextView tvCountdown;
    private RadioButton firstAnswer;
    private RadioButton secondAnswer;
    private RadioButton thirdAnswer;
    private RadioButton fourthAnswer;
    private Button btnConfirm;

    private int currentQuestionIndex;
    private Question currentQuestion;

    private DatabaseReference questionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_answer);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentQuestionIndex = 0;

        // Get form elements
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvNoQuestion  = findViewById(R.id.tvNoQuestion);
        tvCountdown = findViewById(R.id.tvCountdown);
        firstAnswer = findViewById(R.id.question_answer_1);
        secondAnswer = findViewById(R.id.question_answer_2);
        thirdAnswer = findViewById(R.id.question_answer_3);
        fourthAnswer = findViewById(R.id.question_answer_4);
        btnConfirm = findViewById(R.id.btnConfirm);

        Intent intent = getIntent();
        questionIds = intent.getStringArrayListExtra(ProjectIdentifier.BUNDLE_PREFIX + ".question_ids");

        questionDatabase = FirebaseDatabase.getInstance().getReference(Question.TYPE_TAG);

        if(questionIds != null && !questionIds.isEmpty()){
            advance(currentQuestionIndex);
        } else {
            Toast.makeText(this, "This questionnaire has no questions.", Toast.LENGTH_LONG).show();
            finish();
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                advance(currentQuestionIndex);
            }
        });

    }

    // Main game loop. Query current question and populate form
    private void advance(int index){
        // If we advanced past the last question, go to the Stats page.
        if(index >= questionIds.size()){
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
            return;
        }
        String questionId = questionIds.get(index);
        questionDatabase.child(questionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateQuestionForm(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateQuestionForm(DataSnapshot dataSnapshot){
        try {
            Question thisQuestion = dataSnapshot.getValue(Question.class);

            tvQuestion.setText(thisQuestion.getTitle());
            firstAnswer.setText(thisQuestion.answear1);
            secondAnswer.setText(thisQuestion.answear2);
            thirdAnswer.setText(thisQuestion.answear3);
            fourthAnswer.setText(thisQuestion.answear4);

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
