package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {
    EditText etSIUserName;
    EditText etSIPassword;
    Button  button_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signin);
        etSIUserName = (EditText) findViewById(R.id.etSIUserName);
        etSIPassword = (EditText) findViewById(R.id.etSIPassword);
        final String SIusername = etSIUserName.getText().toString();
        final String SIpassword = etSIPassword.getText().toString();
        button_login = (Button)findViewById(R.id.button_signIn);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(SIusername, SIpassword);
                openMainActivity();
            }
        });


    }
    public void openMainActivity(){
        Intent intent =  new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validation(String SIusername, String SIpassword){
        boolean isValid = true;
        if(SIusername.isEmpty() || SIusername == null){
            etSIUserName.setError("Please enter the username!");
            isValid = false;
        }
        if(SIpassword.isEmpty() || SIpassword == null){
            etSIPassword.setError("Please enter the correct password!");
            isValid=false;
        }

        return isValid;
    }
}
