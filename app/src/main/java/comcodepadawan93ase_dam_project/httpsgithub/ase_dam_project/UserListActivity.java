package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.User;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.R;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class UserListActivity extends AppCompatActivity {

    private ListView userList;
    private FloatingActionButton fab;
    private DatabaseReference db;
    private ArrayList<User> objUsers;
    private ArrayList<String> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get existing questionnaires (hardcoded for now...)
        // String[] users = {"User1", "User2", "User3" };
        // ArrayAdapter adapter = new ArrayAdapter<String>(this,
         //       R.layout.activity_listview, users);

        // Populate list with existing questionnaires
        userList = (ListView)findViewById(R.id.user_list);
        //userList.setAdapter(adapter);
        objUsers = new ArrayList<User>();
        // Attach event listener to FAB
        final UserListActivity context = this;

        db = FirebaseDatabase.getInstance().getReference(User.TYPE_TAG);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateUsers(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

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
                String userId = objUsers.get(position).getUser_id();
                intent.putExtra(ProjectIdentifier.BUNDLE_PREFIX + ".user_id", userId);
                startActivity(intent);
            }
        });
    }

    private void populateUsers(DataSnapshot dataSnapshot){
        users = new ArrayList<String>();
        for (DataSnapshot userDs : dataSnapshot.getChildren()){
            User user = userDs.getValue(User.class);
            // Save the Firebase key of the User
            user.setUser_id(userDs.getKey());
            objUsers.add(user);
            users.add(user.getUserName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, users);

        // Populate list with existing users
        userList.setAdapter(adapter);
    }
}
