package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Response;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class SingleAnswerActivity extends AppCompatActivity {

    private final int FEEDBACK_DELAY_MILLIS = 3000;
    private final int DEFAULT_QUESTION_TIMEOUT_MILLIS = 30000;
    private int score;
    private int chosenAnswer;

    private CountDownTimer timer;

    private SingleAnswerActivity context;

    private String questionnaireId;
    private ArrayList<Integer> chosenAnswers;
    private ArrayList<String> questionIds;
    private ArrayList<Boolean> correctAnswers;
    private Question thisQuestion;

    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvNoQuestion;
    private TextView tvCountdown;
    private RadioButton firstAnswer;
    private RadioButton secondAnswer;
    private RadioButton thirdAnswer;
    private RadioButton fourthAnswer;
    private Button btnConfirm;
    private ProgressBar answerTimerProgressBar;

    private ArrayList<RadioButton> answers;

    private int currentQuestionIndex;
    private Question currentQuestion;

    private DatabaseReference questionDatabase;
    private DatabaseReference responseDatabase;

    private boolean isInGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_answer);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentQuestionIndex = 0;
        score = 0;
        isInGame = false;

        // init arrays
        chosenAnswers = new ArrayList<Integer>();
        correctAnswers = new ArrayList<Boolean>();

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
        answerTimerProgressBar = findViewById(R.id.answer_timer_progress);

        // Get the data that was passed in
        Intent intent = getIntent();
        questionIds = intent.getStringArrayListExtra(ProjectIdentifier.BUNDLE_PREFIX + ".question_ids");
        questionnaireId = intent.getStringExtra(ProjectIdentifier.BUNDLE_PREFIX + ".questionnaire_id");
        questionDatabase = FirebaseDatabase.getInstance().getReference(Question.TYPE_TAG);

        // Advance if we have questions
        if(questionIds != null && !questionIds.isEmpty()){
            advance(currentQuestionIndex);
        } else {
            Toast.makeText(this, "This questionnaire has no questions.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Advance if user clicks confitm button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleConfirm();
            }
        });

        // Attach event handlers
        answers = new ArrayList<RadioButton>(Arrays.asList(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer));
        int i = 0;
        for(RadioButton answer : answers){
            answer.setTag(i);
            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleChoice(v);
                }
            });
            i++;
        }

        // Get firebase ref to send a response
        responseDatabase = FirebaseDatabase.getInstance().getReference(Response.TYPE_TAG);
    }

    @Override
    public void onBackPressed() {
        if (!isInGame) {
            super.onBackPressed();
        }
    }

    // Main game loop. Query current question and populate form
    private void advance(int index){

        // If we advanced past the last question, go to the Stats page.
        if(index >= questionIds.size()){

            // Send response to Firebase
            long now = (new Date().getTime());
            Response response = new Response(questionnaireId, now, questionIds, chosenAnswers, correctAnswers, score, 1);
            response.save(responseDatabase);

            Intent intent = new Intent(this, StatsActivity.class);
            // pass score an no of questio into stats
            intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".total_questions", questionIds.size());
            intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".total_score", score);
            startActivity(intent);
            finish();
            return;
        }

        // Now we are in game!
        isInGame = true;
        String questionId = questionIds.get(index);
        questionDatabase.child(questionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateQuestionForm(dataSnapshot);
                int timeToAnswer = thisQuestion.getTime();
                if(timeToAnswer > 0){
                    startTimer(timeToAnswer);
                } else {
                    startTimer(DEFAULT_QUESTION_TIMEOUT_MILLIS);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateQuestionForm(DataSnapshot dataSnapshot){
        try {
            thisQuestion = dataSnapshot.getValue(Question.class);

            tvQuestion.setText(thisQuestion.getTitle());
            firstAnswer.setText(thisQuestion.answear1);
            secondAnswer.setText(thisQuestion.answear2);
            thirdAnswer.setText(thisQuestion.answear3);
            fourthAnswer.setText(thisQuestion.answear4);
            tvScore.setText("Score : " + score);
            tvNoQuestion.setText(String.format("Question: %d/%d", currentQuestionIndex + 1, questionIds.size()));
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleChoice(View v){
        RadioButton clickedButton = (RadioButton)v;
        int correctAnswer = thisQuestion.getCorrect_answer();
        // Uncheck all but the one clicked and set the chosen answer, and update game variables
        int tag = Integer.parseInt(v.getTag().toString());
        chosenAnswers.add(tag);
        chosenAnswer = tag;

        // Also provide feedback
        if(correctAnswer == chosenAnswer) {
            correctAnswers.add(true);
            score++;
        } else {
            correctAnswers.add(false);
        }
        int i = 0;
        for(RadioButton answer : answers){
            if(i != tag){
                answer.setChecked(false);
            }
            i++;
        }
    }

    private void resetChoice(){
        // Uncheck all and reset feedback
        for(RadioButton answer : answers){
            answer.setTextColor(Color.BLACK);
            answer.setChecked(false);
        }
    }

    private void handleConfirm(){
        currentQuestionIndex++;
        provideFeedback();
        btnConfirm.setEnabled(false);
        new CountDownTimer(FEEDBACK_DELAY_MILLIS, 1000) {
            public void onFinish() {
                // Don't count down anymore while showing feedback
                timer.cancel();
                // Advance question Index
                advance(currentQuestionIndex);
                // Reset answers
                resetChoice();
                // Enable button again
                btnConfirm.setEnabled(true);
                // Stop existing timer
                timer.cancel();
            }

            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
    private void provideFeedback(){
        int correctAnswer = thisQuestion.getCorrect_answer();
        int i = 0;
        for (RadioButton answer : answers) {
            if (i == correctAnswer) {
                answer.setTextColor(Color.GREEN);
            } else if (i != correctAnswer && answer.isChecked()) {
                answer.setTextColor(Color.RED);
            }
            i++;
        }
    }

    private void startTimer(final int timeToAnswerMillis){
        timer = new CountDownTimer(timeToAnswerMillis, 100) {
            public void onFinish() {
                // Confirm submission on timer expiry
                handleConfirm();
            }

            public void onTick(long millisUntilFinished) {
                // Decrease the progress bar and display time left to user
                int percentSpent = (int) ((float)millisUntilFinished / (float)timeToAnswerMillis * 100);
                answerTimerProgressBar.setProgress(percentSpent);
                tvCountdown.setText(DateTimeParser.parseMillisToMinsAndSecs(millisUntilFinished));
            }
        };

        timer.start();
    }
}
