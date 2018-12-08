package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionnaireListActivity extends AppCompatActivity {

    private ListView questionnaireList;
    private FloatingActionButton fab;

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

        final QuestionnaireListActivity context = this;
        // FAB
        fab = findViewById(R.id.fab_questionnaire);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionnaireActivity.class);
                startActivity(intent);
            }
        });

    }
}
