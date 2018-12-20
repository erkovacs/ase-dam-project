package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.Property;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB.PropertyDataSource;

public class HelpActivity extends AppCompatActivity {
    private PropertyDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        datasource= new PropertyDataSource(this);
        
        fillData(); 
    }

    private void fillData() {
        datasource.open();
        // Property newProperty = datasource.addProperty(new Property("Lala", "Haha"));
        List<Property> values = datasource.getallProperty();
        datasource.close();

        //Log.d("datasource", newProperty.toString());
        //Log.d("datasource: ",values.toString());
        ArrayList<String> adaptedValues = new ArrayList<String>();
        if(values!=null){
            for(Property prop : values){
                adaptedValues.add(String.format("%d. %s : %s", prop.getProperty_id(), prop.getProperty_name(), prop.getProperty_value()));
            }
            ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, adaptedValues);
            listviewHelp.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No settings yet, please return when we're out of alpha ;).", Toast.LENGTH_LONG).show();
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


}
