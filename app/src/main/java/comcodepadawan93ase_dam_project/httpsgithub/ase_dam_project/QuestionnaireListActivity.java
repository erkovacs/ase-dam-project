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

public class QuestionnaireListActivity extends AppCompatActivity {
    private ListView questionnaireList;
    private FloatingActionButton fab;
    private DatabaseReference db;
    private ArrayList<Questionnaire> objQuestionnaires;
    private ArrayList<String> questionnaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set this before populating
        questionnaireList = (ListView)findViewById(R.id.questionnaire_list);
        objQuestionnaires = new ArrayList<Questionnaire>();
        // Proxy for current instance
        final QuestionnaireListActivity context = this;

        // Get Quesstionnaires from firebase
        db = FirebaseDatabase.getInstance().getReference(Questionnaire.TYPE_TAG);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateQuestionnaires(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // FAB
        fab = findViewById(R.id.fab_questionnaire);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionnaireActivity.class);
                startActivity(intent);
            }
        });

        // Set the event listener when clicking on an item
        questionnaireList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter , View view, int position, long id) {
                Intent intent = new Intent(context, QuestionnaireActivity.class);
                String questionnaireId = objQuestionnaires.get(position).getQuestionnaire_id();
                intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".questionnaire_id", questionnaireId);
                startActivity(intent);
            }
        });
    }

    // Parses the questionnaires from Firebase
    private void populateQuestionnaires(DataSnapshot dataSnapshot){
        questionnaires = new ArrayList<String>();
        for (DataSnapshot questionnaireDs : dataSnapshot.getChildren()){
            Questionnaire questionnaire = questionnaireDs.getValue(Questionnaire.class);
            // Save the Firebase key of the Questionnaire
            questionnaire.setQuestionnaire_id(questionnaireDs.getKey());
            objQuestionnaires.add(questionnaire);
            questionnaires.add(questionnaire.getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, questionnaires);

        // Populate list with existing questionnaires
        questionnaireList.setAdapter(adapter);
    }
}
