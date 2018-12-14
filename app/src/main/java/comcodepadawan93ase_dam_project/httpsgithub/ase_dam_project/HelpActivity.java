package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        List<Property> values = datasource.getallProperty();
        datasource.close();

        if(values!=null){
            ArrayAdapter<Property> adapter = new ArrayAdapter<Property>(this,R.layout.activity_help, R.id.label,values);
            String[] help = {"Rule Book", "Learn how to play", "Support", "Terms of Use", "Privacy Policy"};
            ListAdapter helpAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,help);
            ListView listviewHelp = (ListView) findViewById(R.id.listviewHelp);
            listviewHelp.setAdapter(helpAdapter);
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
