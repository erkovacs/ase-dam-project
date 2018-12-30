package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b;
    Toast toast;
    User user = new User();
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etEmail =  findViewById(R.id.etEmail);
        context = this;
        setContentView(R.layout.activity_signup);

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
                    b.setError("Please select a role!");
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
                saveInfo(v);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogIn();
            }
        });
        validUser = true;
        if (user.getUserName().isEmpty() || user.getUserName().length() > 30) {
            etUserName = findViewById(R.id.etUserName);

            etUserName.setError("Please enter a valid user name!");
            validUser = false;
        }
        if (user.getPassword().length() < 6 || user.getPassword().isEmpty()) {
            etPassword = findViewById(R.id.etPassword);


            etPassword.setError("Please enter a valid password!");
            validUser = false;

        }
        if (user.getUserName().isEmpty() || user.getUserName().length() > 30) {

            etName = findViewById(R.id.etName);

            etName.setError("Please enter a valid name!");
            validUser = false;
        }
        if (user.getUserEmail().isEmpty() || (user.getUserEmail().contains(logInCondition))== false) {

            etEmail =  findViewById(R.id.etEmail);

            etEmail.setError("Please enter the academic email address!");
            validUser = false;
        }
        // return validUser;

    }
    public void saveInfo(View view){
        final String userNames = etUserName.getText().toString();
        final String Passwords = etPassword.getText().toString();
        final String signUpName = etName.getText().toString();
        final String Emails = etEmail.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("userSignUpInfo", Context.MODE_PRIVATE);
        // Save to shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", userNames );
        editor.putString("password", Passwords);
        editor.putString("signUpName", signUpName);
        editor.putString("email", Emails);
        editor.putString("role", chosenRole);
        editor.apply();
        // Save to firebase too
        //String[] names = signUpName.split("\\s");
        //String fname = "";
        //String lname = "";
        //if(names.length >= 2){
        //  lname += names[0];
        //  for(int i= 1; i < names.length; i++){
        //      fname += names[i] + " ";
        //  }
        //} else if(names.length == 1) {
        //  fname += names[0];
        //}
        User user = new User(userNames, Passwords, signUpName, Emails, chosenRole);
        if(isNewUser) {
            user.save(userDatabase);
        } else {
            user.setUser_id(userId);
            user.update(userDatabase);
        }
        Toast.makeText(this, "User saved!", Toast.LENGTH_LONG).show();
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
            User user = dataSnapshot.getValue(User.class);
            etUserName.setText(user.getUserName());
            etPassword.setText(user.getPassword());
            etName.setText(user.getUserNameSign());
            etEmail.setText(user.getUserEmail());
            String role = user.getRole();
            spinner_createUser.setSelection(roleSpinnerAdapter.getPosition(role));
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
