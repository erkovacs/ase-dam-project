package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.ContactQuestion;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.Property;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDB;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDataSource;

public class ContactActivity extends AppCompatActivity {
    //switch button for DarkMode
    private Switch myswitch;

    Button button_json;
    private Button btnSend;

    private SQLiteDatabase db;
    private PropertyDB dbHelper;
    private PropertyDataSource dataSource;

    public boolean contactAddQuestions(){
        final EditText ETnameId = (EditText)findViewById(R.id.name_id);
        final EditText ETemailId=(EditText)findViewById(R.id.email_id);
        final EditText ETsubjectId=(EditText)findViewById(R.id.subject_id);
        final EditText ETquestion=(EditText)findViewById(R.id.id_question);

        final String nameid = ETnameId.getText().toString();
        final String emailid = ETemailId.getText().toString();
        final String subjectid=ETsubjectId.getText().toString();
        final String questionid=ETquestion.getText().toString();

        if(questionid.isEmpty() || questionid == null){
            ETquestion.setError(getString(R.string.contact_insert_question));
            ETnameId.setError(getString(R.string.contact_insert_name));
            return false;
        } else {
            ContactQuestion question = new ContactQuestion(nameid, emailid, questionid);
           dataSource = new PropertyDataSource(this);
           dataSource.open();
           dataSource.addProperty(new Property("Question:", question.toJson()));
           dataSource.close();
           return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //switch button methods for DarkMode
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

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
                if(contactAddQuestions()) {
                    Intent intent = new Intent(ContactActivity.this, HelpActivity.class);
                    startActivity(intent);
                }
            }
        });

        //switch button methods for DarkMode
        myswitch=findViewById(R.id.myswitch);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });
    }
    //restart application with default theme white
    public void restartApp () {
        Intent i = new Intent(getApplicationContext(), ContactActivity.class);
        startActivity(i);
        finish();
    }

    public void clickButton(){
        Intent intent =  new Intent(this, JSONContactHistoryActivity.class);
        startActivity(intent);
    }


}
