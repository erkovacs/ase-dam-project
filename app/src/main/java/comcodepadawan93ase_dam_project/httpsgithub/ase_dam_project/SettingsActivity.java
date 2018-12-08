package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String[] settings = {"Language", "Sound Effects", "In-Game Music", "Edit Profile", "Privacy", "Log Out"};
        ListAdapter settingsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);
        ListView listviewSettings = (ListView) findViewById(R.id.listviewSettings);
        listviewSettings.setAdapter(settingsAdapter);
    }
}
