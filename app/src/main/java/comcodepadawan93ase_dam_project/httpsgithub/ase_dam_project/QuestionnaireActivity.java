package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;


import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.RandomCodeGenerator;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class QuestionnaireActivity extends AppCompatActivity {

    private boolean isNew;
    private String questionnaireId;

    private Spinner questionnaireTypePicker;
    private ListView questionList;
    private AlertDialog.Builder builder;
    private EditText dateStart;
    private EditText dateEnd;
    private boolean isPublic = false;

    private EditText title;
    private String type;
    private String _hashCode;
    private Switch isPublicSwitch;
    private Questionnaire newQuestionnaire;

    private DatePickerDialog.OnDateSetListener dateStartSetListener;
    private DatePickerDialog.OnDateSetListener dateEndSetListener;
    private ArrayAdapter<String> dropdownAdapter;

    private Button submit;
    private Button showCode;

    protected DatabaseReference databaseQuestionnaire;
    protected DatabaseReference databaseQuestion;
    private Questionnaire questionnaire;

    private QuestionnaireActivity context;

    // Questions
    private ArrayList<String> questions;
    private ArrayList<Question> objQuestions;
    private ArrayList<String> questionIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        questions = new ArrayList<String>();
        objQuestions = new ArrayList<Question>();
        questionIds = new ArrayList<String>();

        // Setup picklist
        String[] questionnaireTypes = { "Multiple Answer", "Single Answer", "Freeform Answer"};
        questionnaireTypePicker =  (Spinner) findViewById(R.id.type);
        dropdownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionnaireTypes);
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionnaireTypePicker.setAdapter(dropdownAdapter);

        // Set title
        title = (EditText) findViewById(R.id.title);

        // Add datepickers
        dateStart = (EditText) findViewById(R.id.date_start);
        dateEnd = (EditText) findViewById(R.id.date_end);

        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(QuestionnaireActivity.this, android.R.style.Theme_Material_Dialog, dateStartSetListener, year, month, day);
                dpd.show();
            }
        });

        dateStartSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = String.format("%d.%d.%d", dayOfMonth, month, year);
                dateStart.setText(date);
            }
        };

        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(QuestionnaireActivity.this, android.R.style.Theme_Material_Dialog, dateEndSetListener, year, month, day);
                dpd.show();
            }
        });

        dateEndSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = String.format("%d.%d.%d", dayOfMonth, month, year);
                dateEnd.setText(date);
            }
        };

        //
        // Handle switch value
        isPublicSwitch = (Switch) findViewById(R.id.switch1);
        isPublicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPublic = isChecked;
            }
        });

        // Render the list of questions
        questions.add("Add");
        ArrayAdapter questionsAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questions);

        // Populate list with existing questions
        questionList = (ListView)findViewById(R.id.questionnaire_question_list);
        questionList.setAdapter(questionsAdapter);

        // Allow user to pick questions
        // setup the alert builder
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the questions:");

        // Handle db logic
        // Get Firebase ref
        databaseQuestionnaire = FirebaseDatabase.getInstance().getReference(Questionnaire.TYPE_TAG);

        // Get the Intent and initialize our Questionnaire
        Intent intent = getIntent();
        questionnaireId = intent.getStringExtra(ProjectIdentifier.BUNDLE_PREFIX + ".questionnaire_id");

        // If we don't have an id we have a new Questionnaire
        isNew = questionnaireId == null;

        // If existing read the data from server
        if(!isNew){
            // Get the current Qurestionnaire
            databaseQuestionnaire.child(questionnaireId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    populateQuestionnaire(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Handle questions
        // Get all questions from firebase
        databaseQuestion = FirebaseDatabase.getInstance().getReference(Question.TYPE_TAG);
        databaseQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(questionIds);
                populateQuestions(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Handle submission of instance
        submit = (Button) findViewById(R.id.save_questionnaire);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addQuestionnaire()){
                    submit.setEnabled(false);
                }
            }
        });

        // Handle showing the code
        showCode = findViewById(R.id.show_questionnaire_code);
        if(isNew){
            showCode.setEnabled(false);
        } else {
            showCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHashCode();
                }
            });
        }
    }

    // Get a code for the Questionnaire
    private String generateHashCode(String baseCode){
        String in = hashCode() + baseCode;
        return RandomCodeGenerator.getCode(in, 6);
    }

    // Add a new questionnaire or update existing
    private boolean addQuestionnaire(){
        // Gather all the needed data
        String titleString = title.getText().toString().trim();
        String dateStartString = dateStart.getText().toString().trim();
        String dateEndString = dateEnd.getText().toString().trim();
        type = questionnaireTypePicker.getSelectedItem().toString();

        // Get the Hash code
        _hashCode = generateHashCode(titleString + dateStartString + dateEndString + type + objQuestions.size());
        try {
            newQuestionnaire = new Questionnaire(titleString, dateStartString, dateEndString, type, _hashCode, isPublic, questionIds);
            if(isNew){
                newQuestionnaire.save(databaseQuestionnaire);
            } else {
                newQuestionnaire.setQuestionnaire_id(questionnaireId);
                newQuestionnaire.update(databaseQuestionnaire);
            }
            Toast.makeText(this, "Questionnaire " + (isNew ? "created" : "updated") + " successfully!", Toast.LENGTH_LONG).show();
            return true;
        } catch (InvalidModelExeption ime){
            Toast.makeText(this, ime.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // Populate the fields of it's an existing Qiuestionnaire
    private void populateQuestionnaire(DataSnapshot dataSnapshot) {
        try {
            Questionnaire questionnaire = dataSnapshot.getValue(Questionnaire.class);
            title.setText(questionnaire.getTitle());
            _hashCode = questionnaire.getHash_code();
            dateStart.setText(DateTimeParser.parseTimestamp(questionnaire.getDate_start()));
            dateEnd.setText(DateTimeParser.parseTimestamp(questionnaire.getDate_end()));
            String type = questionnaire.getType();
            questionnaireTypePicker.setSelection(dropdownAdapter.getPosition(type));
            isPublicSwitch.setChecked(questionnaire.isIs_public()); // bad autogenerated getter
            questionIds = questionnaire.getQuestions() == null ? questionIds : questionnaire.getQuestions();
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void populateQuestions(DataSnapshot dataSnapshot){
        questions.clear();
        questions.add("Add");

        for (DataSnapshot questionDs : dataSnapshot.getChildren()){
            Question question = questionDs.getValue(Question.class);
            question.setQuestion_id(questionDs.getKey());
            objQuestions.add(question);
            if(questionIds.indexOf(question.getQuestion_id()) > -1){
                questions.add(question.getText());
            }
        }

        if(!questionIds.isEmpty()){
            ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(QuestionnaireActivity.this,
                    R.layout.activity_listview, questions);

            questionList.setAdapter(questionsAdapter);
        }

        // add a checkbox list in a popup
        final String[] questionLabels = new String[objQuestions.size()];
        final boolean[] checkedItems = new boolean[objQuestions.size()];
        for(int i = 0; i < objQuestions.size(); i++){
            Question question = objQuestions.get(i);
            questionLabels[i] = question.getText();
            checkedItems[i] = ( questionIds.indexOf(question.getQuestion_id()) > -1 );
        }

        builder.setMultiChoiceItems(questionLabels, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    questionIds.add(objQuestions.get(which).getQuestion_id());
                    questions.add(objQuestions.get(which).getText());
                } else {
                    questionIds.remove(objQuestions.get(which).getQuestion_id());
                    questions.remove(objQuestions.get(which).getText());
                }
                checkedItems[which] = isChecked;
            }
        });


        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Render out the list
                ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(QuestionnaireActivity.this,
                        R.layout.activity_listview, questions);

                questionList.setAdapter(questionsAdapter);
            }
        });

        builder.setNegativeButton("Cancel", null);

        // Take user to form on clikc
        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void showHashCode(){
        // Create an alert to allow the user to input the code
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unique Code");
        alert.setMessage("\n" + _hashCode);

        alert.setPositiveButton("COPY CODE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Place the code upon the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("hash_code", _hashCode);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "The Code was copied to the clipboard!", Toast.LENGTH_LONG).show();
                return;
            }
        });

        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alert.show();
    }
}


