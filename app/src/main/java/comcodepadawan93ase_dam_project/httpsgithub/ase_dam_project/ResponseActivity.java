package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.Response;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.DateTimeParser;

public class ResponseActivity extends AppCompatActivity {

    private DatabaseReference responseDatabase;
    private DatabaseReference questionnaireDatabase;
    private DatabaseReference userDatabase;

    private ListView responseList;
    private ResponseActivity context;
    private ArrayList<String> reponseStrings;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        reponseStrings = new ArrayList<String>();
        responseList = findViewById(R.id.response_list);

        // Get firebases
        responseDatabase = FirebaseDatabase.getInstance().getReference(Response.TYPE_TAG);
        questionnaireDatabase = FirebaseDatabase.getInstance().getReference(Questionnaire.TYPE_TAG);

        responseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateResponse(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateResponse(DataSnapshot dataSnapshot){
        for(DataSnapshot responseDs : dataSnapshot.getChildren()){
            Response response = responseDs.getValue(Response.class);
            addCurrentQuestionnaireString(response.getQuestionnaire_id(), response);
        }
    }

    private void addCurrentQuestionnaireString(String id, final Response response){
        final Response theResponse = response;
        questionnaireDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Questionnaire questionnaire = dataSnapshot.getValue(Questionnaire.class);
                    reponseStrings.add("For questionnaire '" + questionnaire.getTitle() + "' taken on " +
                            DateTimeParser.parseTimestamp(theResponse.getCompleted_on()) +
                            " by user '" + theResponse.getUser_name() + "', the score was : " + theResponse.getScore());

                } catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }
                // Needs to run here because structure of data is not optimal for this format
                adapter = new ArrayAdapter<String>(context,
                        R.layout.activity_listview, reponseStrings);
                responseList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
