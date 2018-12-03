package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class QuestionnaireActivity extends AppCompatActivity {
    private Spinner questionnaireTypePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] questionnaireTypes = { "Multiple Answer", "Single Answer", "Freeform Answer"};
        questionnaireTypePicker =  (Spinner) findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionnaireTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionnaireTypePicker.setAdapter(adapter);
    }
}
