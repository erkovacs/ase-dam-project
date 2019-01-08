package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Response;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseQuestionnaire;
    private String currentCode;
    private Questionnaire currentQuestionnaire;
    private ArrayList<String> questionIds;

   Button btnLogIn, btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity context = this;
        questionIds = new ArrayList<String>();

        // Get Firebase Ref
        databaseQuestionnaire = FirebaseDatabase.getInstance().getReference(Questionnaire.TYPE_TAG);
        Button btnPlayGame = findViewById(R.id.btnPlay);

        // Set click on the button
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an alert to allow the user to input the code
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Join game room");
                alert.setMessage("Enter your unique code :");

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        // Validate code
                        if(!"".equals(value) && value != null){
                            // Make the value globally available
                            currentCode = value;

                            // Query for the provided value
                            Query queryRef = databaseQuestionnaire.orderByChild("hash_code").equalTo(value);
                            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    populateQuestionnaire(dataSnapshot);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        } else {
                            Toast.makeText(context, "You need to provide a code to enter the game.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

                alert.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                alert.show();
            }
        });
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });
        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInfo();
            }
        });
    }
    private void openSignIn(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);

    }
    private void deleteInfo(){
        SharedPreferences preferences = getSharedPreferences("userSignUpInfo", 0);
          if( preferences.contains("username") && preferences.contains("password")){
            preferences.edit().remove("username");
            preferences.edit().remove("password");
       }else{
              Toast.makeText(this, "you are already logged out!", Toast.LENGTH_LONG).show();
          }
    }
  private boolean checkQuestionnaireTime() {
        boolean validTime = false;
        long startingTime = currentQuestionnaire.getDate_start();
        long endingTime = currentQuestionnaire.getDate_end();
        long currentTime = System.currentTimeMillis();

        if (startingTime <= currentTime && currentTime <= endingTime) {
            validTime = true;
        } else {
            String text = null;
            try {
                text = "This questionnaire is only available between " +
                        DateTimeParser.parseTimestamp(startingTime) +
                        " and " +
                        DateTimeParser.parseTimestamp(endingTime);
            } catch (Exception e ){
                text = e.getMessage();
            }
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        return validTime;
    }

    private void startQuiz(){
        Intent intent = new Intent(MainActivity.this, SingleAnswerActivity.class);
        intent.putStringArrayListExtra(ProjectIdentifier.BUNDLE_PREFIX + ".question_ids", questionIds);
        intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".questionnaire_id", currentQuestionnaire.getQuestionnaire_id());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Upon tapping on a menu item, display a list of entities if applicable
        boolean retval = false;
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.users_menu_item:
                intent = new Intent(this, UserListActivity.class);
                retval = true;
                break;
            case R.id.questionnaires_menu_item:
                intent = new Intent(this, QuestionnaireListActivity.class);
                retval = true;
                break;
            case R.id.questions_menu_item:
                intent = new Intent(this, QuestionListActivity.class);
                retval = true;
                break;
            // This was initially referred to as History but the model is called Response so there's
            // the souurce of the mismatch
            case R.id.history_menu_item:
                intent = new Intent(this, ResponseActivity.class);
                retval = true;
                break;
            case R.id.settings_menu_item:
                intent = new Intent(this, SettingsActivity.class);
                retval = true;
                break;
            case R.id.contact_menu_item:
                intent = new Intent(this, ContactActivity.class);
                retval = true;
                break;
            case R.id.about_menu_item:
                intent = new Intent(this, AboutActivity.class);
                retval = true;
                break;
            case R.id.help_menu_item:
                intent = new Intent(this, HelpActivity.class);
                retval = true;
                break;
            default:
                retval = super.onOptionsItemSelected(item);
        }
        if(retval)
            this.startActivity(intent);
        return retval;
    }

    private void populateQuestionnaire(DataSnapshot dataSnapshot){
        // Instantiate the current Questionnaire and start the next activity, feeding the questions into that
        try {
            for (DataSnapshot questionnaireSnapshot: dataSnapshot.getChildren()) {
                currentQuestionnaire = questionnaireSnapshot.getValue(Questionnaire.class);
            }
            if(currentQuestionnaire != null) {
                questionIds = currentQuestionnaire.getQuestions() == null ? questionIds : currentQuestionnaire.getQuestions();
            }

            if(currentQuestionnaire != null && currentQuestionnaire.getHash_code().equals(currentCode)){
                // Check time too
                if(checkQuestionnaireTime()){
                    startQuiz();
                } else {
                    return;
                }
            } else {
               Toast.makeText(this, currentCode, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
