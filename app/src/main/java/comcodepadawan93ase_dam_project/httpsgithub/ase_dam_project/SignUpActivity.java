package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

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

import java.text.BreakIterator;

import static android.widget.Toast.*;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b;
    Toast toast;
    User user = new User();
    boolean validUser;
    private String chosenRole;

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
        EditText etFirstName =(EditText) findViewById(R.id.etFirstName);
        EditText etLastName = (EditText) findViewById(R.id.etLastName);
       final String userNames = etUserName.getText().toString();
        final String Passwords = etPassword.getText().toString();
        final String FirstNames = etFirstName.getText().toString();
        final String LastNames = etLastName.getText().toString();

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

         validUser = true;
        if (user.getUserName().isEmpty() || user.getUserName().length() > 30) {
            etUserName.setError("Please eter a valid user name!");
            validUser = false;
        }
        if (user.getPassword().length() < 6 || user.getPassword().isEmpty()) {
            etPassword.setError("Please enter a valid password!");
            validUser = false;

        }
        if (user.getFirstName().isEmpty() || user.getFirstName().length() > 30) {
            etFirstName.setError("Please enter a valid name!");
            validUser = false;
        }
        if (user.getLastName().isEmpty() || user.getLastName().length() > 30) {
            etLastName.setError("Please enter a valid name!");
            validUser = false;
        }
        // return validUser;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
