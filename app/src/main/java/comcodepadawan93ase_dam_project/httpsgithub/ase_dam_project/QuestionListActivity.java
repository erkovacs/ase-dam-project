package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Question;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Questionnaire;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class QuestionListActivity extends AppCompatActivity {

    private ListView questionList;
    private FloatingActionButton fab;

    private ArrayList<String> questions;
    private ArrayList<Question> objQuestions;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final QuestionListActivity context = this;

        objQuestions = new ArrayList<Question>();
        questionList = (ListView)findViewById(R.id.question_list);

        // populate list with questions
        db = FirebaseDatabase.getInstance().getReference("question");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateQuestions(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // FAB
        fab = findViewById(R.id.fab_question);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionFormProfActivity.class);
                startActivity(intent);
            }
        });

        // Set the event listener when clicking on an item
        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter , View view, int position, long id) {
                // Start the form with the relevant question in it
                Intent intent = new Intent(context, QuestionFormProfActivity.class);
                String questionId = objQuestions.get(position).getQuestion_id();
                intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".question_id", questionId);
                startActivity(intent);
            }
        });
    }

    private void populateQuestions(DataSnapshot dataSnapshot){
        questions = new ArrayList<String>();

        for (DataSnapshot questionDs : dataSnapshot.getChildren()){
            Question question = questionDs.getValue(Question.class);
            // Save the Firebase key of the Question
            question.setQuestion_id(questionDs.getKey());
            objQuestions.add(question);
            questions.add(question.getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questions);

        // Populate list with existing questionnaires
        questionList.setAdapter(adapter);
    }
}
