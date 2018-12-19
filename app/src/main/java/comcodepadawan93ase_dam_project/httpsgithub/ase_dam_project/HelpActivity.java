package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        /*String[] help = {"Rule Book", "Learn how to play", "Support", "Terms of Use", "Privacy Policy"};
        ListAdapter helpAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, help);
        ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
        listviewHelp.setAdapter(helpAdapter);*/
        
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
        for(Property prop : values){
            adaptedValues.add(String.format("%d. %s : %s", prop.getProperty_id(), prop.getProperty_name(), prop.getProperty_value()));
        }
        if(values!=null){
            ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, adaptedValues);
            listviewHelp.setAdapter(adapter);
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
