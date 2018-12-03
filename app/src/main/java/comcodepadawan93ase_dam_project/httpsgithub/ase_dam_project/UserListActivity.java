package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UserListActivity extends AppCompatActivity {

    private ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get existing questionnaires (hardcoded for now...)
        String[] users = {"User1", "User2", "User3" };
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, users);

        // Populate list with existing questionnaires
        userList = (ListView)findViewById(R.id.user_list);
        userList.setAdapter(adapter);
    }
}
