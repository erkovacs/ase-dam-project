package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.oob.SignUp;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.R;

public class UserListActivity extends AppCompatActivity {

    private ListView userList;
    private FloatingActionButton fab;

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

        // Attach event listener to FAB
        final UserListActivity context = this;
        // FAB
        fab = findViewById(R.id.fab_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Set the event listener when clicking on an item
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter , View view, int position, long id) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
