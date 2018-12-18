package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.Property;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDB;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDataSource;

public class ContactActivity extends AppCompatActivity {
    Button button_json;
    private Button btnSend;

    private SQLiteDatabase db;
    private PropertyDB dbHelper;
    private PropertyDataSource dataSource;

    public void ContactAddQuestions(){
        final EditText ETnameId = (EditText)findViewById(R.id.name_id);
        final EditText ETemailId=(EditText)findViewById(R.id.email_id);
        final EditText ETsubjectId=(EditText)findViewById(R.id.subject_id);
        final EditText ETquestion=(EditText)findViewById(R.id.id_question);

        final String nameid = ETnameId.getText().toString();
        final String emailid = ETemailId.getText().toString();
        final String subjectid=ETsubjectId.getText().toString();
        final String questionid=ETquestion.getText().toString();





        if(questionid.isEmpty() || questionid == null){
            ETquestion.setError("You need to insert a question");
            ETnameId.setError("You need to insert your name");
        }
        else{
           // TODO:: use insert method here to add a new feedback message. Beforehand, redo associated view.
           dataSource = new PropertyDataSource(this);
           dataSource.open();
           dataSource.addProperty(new Property("Lala", "Haha"));
           dataSource.close();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        button_json = findViewById(R.id.button_json);
        button_json.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               clickButton();
           }
       });

        //send button
        btnSend =(Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
    public void clickButton(){
        Intent intent =  new Intent(this, JSONContactHistoryActivity.class);
        startActivity(intent);
    }


}
