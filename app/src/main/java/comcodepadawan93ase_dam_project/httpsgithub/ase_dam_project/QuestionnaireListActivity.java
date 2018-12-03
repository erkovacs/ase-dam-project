package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionnaireListActivity extends AppCompatActivity {

    private ListView questionnaireList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get existing questionnaires (hardcoded for now...)
        String[] questionnaires = {"Questionnaire1", "Questionnaire2", "Questionnaire3" };
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questionnaires);

        // Populate list with existing questionnaires
        questionnaireList = (ListView)findViewById(R.id.questionnaire_list);
        questionnaireList.setAdapter(adapter);
    }
}
