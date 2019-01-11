package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.ContactQuestion;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.Property;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDataSource;

public class HelpActivity extends AppCompatActivity {
    private PropertyDataSource datasource;
    List<Property> values;
    ArrayList<String> parsedValues;
    private FloatingActionButton fab;
    private static String OUTPUT_FILE = "outfile.txt";

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
                    Log.e("JSON PARSE EXCEPTION:", jse.getMessage());
                }
                String parsedValue = String.format(getString(R.string.template_help_question), prop.getProperty_id(), prop.getProperty_name(), name, email, question);
                parsedValues.add(parsedValue);
            }
            ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, parsedValues);
            listviewHelp.setAdapter(adapter);
        } else {
            Toast.makeText(this, getString(R.string.help_no_settings), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    private void writeToFile(String data, String filename, Context context) {
        File sdcard = getFilesDir();
        File file = new File(sdcard, filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleFabClick(View v){
        if(parsedValues != null && parsedValues.size() > 0){
            StringBuilder report = new StringBuilder();
            String separator =  System.getProperty("line.separator");
            for(String string : parsedValues){
                report.append(string);
                report.append(separator);
            }
            writeToFile(report.toString(), OUTPUT_FILE,this);
            Toast.makeText(this, String.format(getString(R.string.successfully_saved_file), OUTPUT_FILE), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.no_help_questions), Toast.LENGTH_LONG).show();
        }
        showReportFromFile(OUTPUT_FILE);
    }

    private void showReportFromFile(String filename){
        File sdcard = getFilesDir();
        File file = new File(sdcard, filename);

        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String separator =  System.getProperty("line.separator");
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append(separator);
            }
            br.close();
        } catch (IOException e) {
           Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.text_faqs));
        alert.setMessage(text.toString());

        alert.setPositiveButton(getString(R.string.main_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                return;
            }
        });

        alert.setNegativeButton(getString(R.string.main_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alert.show();
    }
}
