package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.User;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.RandomCodeGenerator;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b;
    Toast toast;
    boolean validUser;
    private String chosenRole;
    Button login;
    String logInCondition = "@stud.ase.ro";
    public static final String TYPE_TAG = "SignUpActivity";
    EditText etUserName, etPassword, etName, etEmail ;
    DatabaseReference userDatabase;
    private boolean isNewUser;
    private SignUpActivity context;
    private String userId;
    private Spinner spinner_createUser;
    private ArrayAdapter<String> roleSpinnerAdapter;

    User userBeforeUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etEmail =  findViewById(R.id.etEmail);
        context = this;
        setContentView(R.layout.activity_signup);

        // Set views
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etEmail =  findViewById(R.id.etEmail);

        // Get User Database reference
        userDatabase = FirebaseDatabase.getInstance().getReference(User.TYPE_TAG);

        // Retrieve user Id
        Intent intent = getIntent();
        userId = intent.getStringExtra(ProjectIdentifier.BUNDLE_PREFIX + ".user_id");
        isNewUser = userId == null;

        if(!isNewUser){
            // Get the current Qurestionnaire
            userDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    populateUser(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }

        final String[] UserRole = new String[] { "Professor", "Student"};
        spinner_createUser =  (Spinner) findViewById(R.id.spinner_createUser);
        roleSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UserRole);
        roleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_createUser.setAdapter(roleSpinnerAdapter);
        spinner_createUser.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                chosenRole = (String) parent.getItemAtPosition(position);
                if(chosenRole.isEmpty()){
                    b.setError(getString(R.string.sign_up_bad_role));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        login =(Button) findViewById(R.id.button_logIn);
        Button b = (Button) findViewById(R.id.button_CreateUser);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if(isUserValid()) {
                   saveInfo(v);
               }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogIn();
            }
        });
    }

    private boolean isUserValid(){
        boolean valid = true;
        final String userName = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        final String signUpName = etName.getText().toString();
        final String email = etEmail.getText().toString();

        if (userName.isEmpty() || userName.length() > 30|| userName.length() <6) {
            etUserName.setError(getString(R.string.sign_up_bad_user_name));
            valid = false;
        }
        if (password.length() < 6 || password.isEmpty()) {
            etPassword.setError(getString(R.string.sign_up_bad_password));
            valid = false;

        }
        if (signUpName.isEmpty() || signUpName.length() > 30) {
            etName.setError(getString(R.string.sign_up_bad_name));
            valid = false;
        }
        if (email.isEmpty() || (email.contains(logInCondition)== false)){
            etEmail.setError(getString(R.string.sign_up_bad_email));
            valid = false;
        }
        return valid;
    }

    public void saveInfo(View view){
        final String userNames = etUserName.getText().toString();
        final String Passwords = etPassword.getText().toString();
        final String passwordHash = RandomCodeGenerator.getPasswordHash(Passwords);
        final String signUpName = etName.getText().toString();
        final String Emails = etEmail.getText().toString();

        // Save to shared preferences if it's a new user - i.e log them in
        SharedPreferences sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(isNewUser) {
            editor.putString("username", userNames);
            editor.putString("password", passwordHash);
            editor.putString("sign_up_name", signUpName);
            editor.putString("email", Emails);
            editor.putString("role", chosenRole);
            editor.apply();
        }
        // Save to firebase too
        User user = new User(userNames, passwordHash, signUpName, Emails, chosenRole);
        if(isNewUser) {
            String userId = user.save(userDatabase);
            editor.putString("user_id", userId);
        } else {
            // if the string value of the password field coincides with whatever came from the db,
            // then we do not want to update the hash as we would upodate it with a hash of a hash
            String oldPasswordHash = userBeforeUpdate.getPassword();
            String currentPassword = Passwords;
            user.setUser_id(userId);
            if(currentPassword.equals(oldPasswordHash)){
                user.setPassword(oldPasswordHash);
            }
            user.update(userDatabase);
        }
        Toast.makeText(this, getString(R.string.sign_up_user_saved), Toast.LENGTH_LONG).show();
    }

    public void openLogIn(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void populateUser(DataSnapshot dataSnapshot){
        try {
            userBeforeUpdate = dataSnapshot.getValue(User.class);
            etUserName.setText(userBeforeUpdate.getUserName());
            etPassword.setText(userBeforeUpdate.getPassword());
            etName.setText(userBeforeUpdate.getUserNameSign());
            etEmail.setText(userBeforeUpdate.getUserEmail());
            String role = userBeforeUpdate.getRole();
            spinner_createUser.setSelection(roleSpinnerAdapter.getPosition(role));
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
