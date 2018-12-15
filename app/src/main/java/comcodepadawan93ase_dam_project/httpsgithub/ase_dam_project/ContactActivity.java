package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {
    Button button_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
       button_json = findViewById(R.id.button_json);
       button_json.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               clickButton();
           }
       });
    }
    public void clickButton(){
        Intent intent =  new Intent(this, JSONContactHistoryActivity.class);
        startActivity(intent);
    }
}
