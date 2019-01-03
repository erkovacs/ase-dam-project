package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Model.User;

public class SignIn extends AppCompatActivity {
    EditText etSIUserName;
    EditText etSIPassword;
    Button  button_login;
    SignIn context;
    DatabaseReference db;

    public static final String TYPE_TAG = "SIgnInActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        context = this;
        db = FirebaseDatabase.getInstance().getReference(User.TYPE_TAG);

        etSIUserName = (EditText) findViewById(R.id.etSIUserName);
        etSIPassword = (EditText) findViewById(R.id.etSIPassword);

        button_login = (Button)findViewById(R.id.button_signIn);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });


    }
    public void openMainActivity(){
        Intent intent =  new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isLoginValid(String SIusername, String SIpassword){
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

    private void signInUser(DataSnapshot dataSnapshot){
        User currentUser = dataSnapshot.getValue(User.class);
        if(currentUser != null){
            // Save to shared preferences the current user
            SharedPreferences sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", currentUser.getUserName());
            editor.putString("password", currentUser.getPassword());
            editor.putString("sign_up_name", currentUser.getUserNameSign());
            editor.putString("email", currentUser.getUserEmail());
            editor.putString("role", currentUser.getRole());
            editor.apply();
            //On success open main activity
            openMainActivity();
        } else {
            Toast.makeText(context, "This user does not exist! Sign up to play the game!", Toast.LENGTH_LONG).show();
        }
    }

    private void handleLogin(){
        final String SIusername = etSIUserName.getText().toString();
        final String SIpassword = etSIPassword.getText().toString();
        if(isLoginValid(SIusername, SIpassword)) {
            Query queryRef = db.orderByChild("userName").equalTo(SIusername);
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    signInUser(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
