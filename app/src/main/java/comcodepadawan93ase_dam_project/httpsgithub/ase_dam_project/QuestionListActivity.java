package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionListActivity extends AppCompatActivity {

    private ListView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get existing questions (hardcoded for now...)
        String[] questions = {"Question 1", "Question 2", "Question 3" };
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questions);

        // Populate list with existing questionnaires
        questionList = (ListView)findViewById(R.id.question_list);
        questionList.setAdapter(adapter);
    }
}
