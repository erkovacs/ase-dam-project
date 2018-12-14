package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;


import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Exceptions.InvalidModelExeption;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class QuestionnaireActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // Get Firebase ref
        databaseQuestionnaire = FirebaseDatabase.getInstance().getReference(Questionnaire.TYPE_TAG);

        final QuestionnaireActivity context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup picklist
        String[] questionnaireTypes = { "Multiple Answer", "Single Answer", "Freeform Answer"};
        questionnaireTypePicker =  (Spinner) findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionnaireTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionnaireTypePicker.setAdapter(adapter);

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
        // Get existing questionnaires (hardcoded for now...)
        String[] questions = {"Question1", "Question2", "Question3" };
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questions);

        // Populate list with existing questionnaires
        questionList = (ListView)findViewById(R.id.questionnaire_question_list);
        questionList.setAdapter(adapter2);

        // Allow user to pick questions
        // setup the alert builder
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the questions:");

        // add a checkbox list
        String[] questionLabels = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        boolean[] checkedItems = {true, false, false, true, false};
        builder.setMultiChoiceItems(questionLabels, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        builder.setNegativeButton("Cancel", null);

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

        // Handle submission of instanbce
        submit = (Button) findViewById(R.id.save_questionnaire);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionnaire();
            }
        });
    }

    private String generateHashCode(int length){
        return "TEST"; // Apparently harder to generate random code than expected... hardcode for now
    }

    private void addQuestionnaire(){
        // Gather all the needed data
        title = (EditText) findViewById(R.id.title);
        String titleString = title.getText().toString().trim();
        String dateStartString = dateStart.getText().toString().trim();
        String dateEndString = dateEnd.getText().toString().trim();
        type = questionnaireTypePicker.getSelectedItem().toString();
        _hashCode = generateHashCode(4);
        ArrayList<Question> questions = new ArrayList<>();
        try {
            newQuestionnaire = new Questionnaire(titleString, dateStartString, dateEndString, type, _hashCode, isPublic, questions);
            newQuestionnaire.save(databaseQuestionnaire);
            Intent intent = new Intent(this, QuestionnaireListActivity.class);
            startActivity(intent);
        } catch (InvalidModelExeption ime){
            Toast.makeText(this, ime.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


