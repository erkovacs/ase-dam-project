package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.ContactQuestion;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.Property;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDataSource;

public class HelpActivity extends AppCompatActivity {
    private PropertyDataSource datasource;
    List<Property> values;
    ArrayList<String> parsedValues;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        datasource= new PropertyDataSource(this);

        parsedValues = new ArrayList<String>();

        fab = (FloatingActionButton) findViewById(R.id.fab_help);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFabClick(v);
            }
        });
        fillData();
    }

    private void fillData() {
        datasource.open();
        // Property newProperty = datasource.addProperty(new Property("Lala", "Haha"));
        values = datasource.getallProperty();
        datasource.close();

        ArrayList<String> adaptedValues = new ArrayList<String>();
        if(values!=null){
            String name = "";
            String email = "";
            String question = "";
            for(Property prop : values){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(prop.getProperty_value());
                    name = jsonObject.getString(ContactQuestion.NAME_TAG);
                    email = jsonObject.getString(ContactQuestion.EMAIL_TAG);
                    question = jsonObject.getString(ContactQuestion.QUESTION_TAG);
                } catch (JSONException jse){
                    Log.e("JSON PARSE EXCEPTION:", jse.getStackTrace().toString());
                }
                String parsedValue = String.format(getString(R.string.template_help_question), prop.getProperty_id(), prop.getProperty_name(), name, email, question);
                parsedValues.add(parsedValue);
                adaptedValues.add(parsedValue);
            }
            ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, adaptedValues);
            listviewHelp.setAdapter(adapter);
        } else {
            Toast.makeText(this, getString(R.string.help_no_settings), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    protected void onResume(){
        fillData();
        super.onResume();
    }

    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    private void handleFabClick(View v){
        if(values != null && values.size() > 0){
            //TODO:: implement the saving of the data read from the SQLite db into a text file
            for(String string : parsedValues){

            }
        } else {
            Toast.makeText(this, getString(R.string.no_help_questions), Toast.LENGTH_LONG).show();
        }
    }
}
