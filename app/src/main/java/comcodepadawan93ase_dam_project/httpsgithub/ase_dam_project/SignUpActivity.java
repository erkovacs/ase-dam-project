package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
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

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.User;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b;
    Toast toast;
    User user = new User();
    boolean validUser;
    private String chosenRole;
    Button login;
    String logInCondition = "@stud.ase.ro";
    public static final String TYPE_TAG = "SignUpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        final String[] UserRole = new String[] { "Professor", "Student"};
        final Spinner spinner_createUser =  (Spinner) findViewById(R.id.spinner_createUser);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UserRole);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_createUser.setAdapter(adapter);
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


       EditText etUserName = (EditText)findViewById(R.id.etUserName);
        EditText etPassword =(EditText) findViewById(R.id.etPassword);
        EditText etName =(EditText) findViewById(R.id.etName);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
       final String userNames = etUserName.getText().toString();
        final String Passwords = etPassword.getText().toString();
        final String FirstNames = etName.getText().toString();
        final String LastNames = etEmail.getText().toString();
        login =(Button) findViewById(R.id.button_logIn);
        Button b = (Button) findViewById(R.id.button_CreateUser);
        b.setOnClickListener(new View.OnClickListener(){
                  @Override
                 public void onClick(View v) {
               int user_id = userNames.hashCode();
                if(validUser) {
                    user = new User(user_id, userNames, Passwords, FirstNames, LastNames, chosenRole);
                }


               toast.makeText(getApplicationContext(), "Account created!", toast.LENGTH_SHORT).show();
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
            etUserName.setError("Please enter a valid user name!");
            validUser = false;
        }
        if (user.getPassword().length() < 6 || user.getPassword().isEmpty()) {
            etPassword.setError("Please enter a valid password!");
            validUser = false;

        }
        if (user.getUserName().isEmpty() || user.getUserName().length() > 30) {
            etName.setError("Please enter a valid name!");
            validUser = false;
        }
        if (user.getUserEmail().isEmpty() || (user.getUserEmail().contains(logInCondition))== false) {

            etEmail.setError("Please enter the academic email address!");
            validUser = false;
        }
        // return validUser;

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
}
