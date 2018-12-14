package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;


import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.RandomCodeGenerator;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private Button submit;

    protected DatabaseReference databaseQuestionnaire;

    private final ArrayList<String> questions = new ArrayList<String>();
    private Questionnaire questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final QuestionnaireActivity context = this;

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

        // Setup picklist
        String[] questionnaireTypes = { "Multiple Answer", "Single Answer", "Freeform Answer"};
        questionnaireTypePicker =  (Spinner) findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionnaireTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionnaireTypePicker.setAdapter(adapter);

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

        // Render the list of questions
        questions.add("Add");
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questions);

        // Populate list with existing questionnaires
        questionList = (ListView)findViewById(R.id.questionnaire_question_list);
        questionList.setAdapter(adapter2);

        // Allow user to pick questions
        // setup the alert builder
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the questions:");

        // add a checkbox list in a popup
        final String[] questionLabels = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        final boolean[] checkedItems = {false, false, false, false, false};
        final String[] questionIds = {"1", "2", "3", "4", "5"};

        builder.setMultiChoiceItems(questionLabels, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    questions.add(questionLabels[which]);
                }
            }
        });


        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < checkedItems.length; i++){
                    if(!checkedItems[i]) questions.remove(questionLabels[i]);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,
                        R.layout.activity_listview, questions);
                // Populate list with existing questionnaires
                questionList = (ListView)findViewById(R.id.questionnaire_question_list);
                questionList.setAdapter(adapter2);
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

        // Handle switch value
        isPublicSwitch = (Switch) findViewById(R.id.switch1);
        isPublicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPublic = isChecked;
            }
        });

        // Handle submission of instance
        submit = (Button) findViewById(R.id.save_questionnaire);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionnaire(questionIds);
            }
        });
    }

    // Get a code for the Questionnaire
    private String generateHashCode(String baseCode){
        String in = hashCode() + baseCode;
        return RandomCodeGenerator.getCode(in, 4);
    }

    // Add a new questionnaire or update existing
    private void addQuestionnaire(String[] childrenIds){
        // Gather all the needed data
        String titleString = title.getText().toString().trim();
        String dateStartString = dateStart.getText().toString().trim();
        String dateEndString = dateEnd.getText().toString().trim();
        type = questionnaireTypePicker.getSelectedItem().toString();
        ArrayList<Question> objQuestions = new ArrayList<Question>();
        for(String id : childrenIds){
            objQuestions.add(new Question(id));
        }
        // Get the Hash code
        _hashCode = generateHashCode(titleString + dateStartString + dateEndString + type + objQuestions.size());
        try {
            newQuestionnaire = new Questionnaire(titleString, dateStartString, dateEndString, type, _hashCode, isPublic, objQuestions);
            if(isNew){
                newQuestionnaire.save(databaseQuestionnaire);
            } else {
                newQuestionnaire.setQuestionnaire_id(questionnaireId);
                newQuestionnaire.update(databaseQuestionnaire);
            }
            Intent intent = new Intent(this, QuestionnaireListActivity.class);
            Toast.makeText(this, "Questionnaire " + (isNew ? "created" : "updated") + " successfully!", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } catch (InvalidModelExeption ime){
            Toast.makeText(this, ime.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Populate the fields of it's an existing Qiuestionnaire
    private void populateQuestionnaire(DataSnapshot dataSnapshot) {
        try {
            Questionnaire thisQuestionnaire = dataSnapshot.getValue(Questionnaire.class);
            title.setText(thisQuestionnaire.getTitle());
            dateStart.setText(DateTimeParser.parseTimestamp(thisQuestionnaire.getDate_start()));
            dateEnd.setText(DateTimeParser.parseTimestamp(thisQuestionnaire.getDate_end()));
            // TODO:: fix this spinner also
            // questionnaireTypePicker
            isPublicSwitch.setChecked(thisQuestionnaire.isIs_public()); // bad autogenerated getter
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


