package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionListActivity extends AppCompatActivity {

    private ListView questionList;
    private FloatingActionButton fab;

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

        final QuestionListActivity context = this;
        // FAB
        fab = findViewById(R.id.fab_question);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionFormProfActivity.class);
                startActivity(intent);
            }
        });
    }
}
