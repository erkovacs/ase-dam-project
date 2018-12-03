package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class QuestionnaireActivity extends AppCompatActivity {
    private Spinner questionnaireTypePicker;
    private EditText dateStart;
    private EditText dateEnd;

    private DatePickerDialog.OnDateSetListener dateStartSetListener;
    private DatePickerDialog.OnDateSetListener dateEndSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

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
    }
}


