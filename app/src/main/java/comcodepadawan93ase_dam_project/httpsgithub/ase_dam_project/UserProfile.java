package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.R;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        TextView tvNameUserProfile = (TextView)findViewById(R.id.tvNameUserProfile);
        TextView tvRoleUserProfile = (TextView)findViewById(R.id.tvRoleUserProfile);
        TextView tvIdUserProfile = (TextView) findViewById(R.id.tvIdUserProfile);
        Button button_historyUserProfile =(Button)findViewById(R.id.button_historyUserProfile);
        Button button_EditUserProfile =(Button)findViewById(R.id.button_DeleteUserProfile);
        Button button_DeleteUserProfile = (Button)findViewById(R.id.button_DeleteUserProfile);

        // in text view trebuie preluata informatia din bd
        

    }
}
