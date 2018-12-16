package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity context = this;
        Button btnPlayGame =findViewById(R.id.btnPlay);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an alert to allow the user to input the code
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Join game room");
                alert.setMessage("Enter your unique code :");

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        // Validate code
                        if(!"".equals(value) && value != null){
                            startQuiz();
                        } else {
                            Toast.makeText(context, "You need to provide a code to enter the game.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

                alert.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                alert.show();
            }
        });
    }

    private void startQuiz(){
        Intent intent = new Intent(MainActivity.this, SingleAnswerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Upon tapping on a menu item, display a list of entities if applicable
        boolean retval = false;
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.users_menu_item:
                intent = new Intent(this, UserListActivity.class);
                retval = true;
                break;
            case R.id.questionnaires_menu_item:
                intent = new Intent(this, QuestionnaireListActivity.class);
                retval = true;
                break;
            case R.id.questions_menu_item:
                intent = new Intent(this, QuestionListActivity.class);
                retval = true;
                break;
            case R.id.history_menu_item:
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.settings_menu_item:
                intent = new Intent(this, SettingsActivity.class);
                retval = true;
                break;
            case R.id.contact_menu_item:
                intent = new Intent(this, ContactActivity.class);
                retval = true;
                break;
            case R.id.about_menu_item:
                intent = new Intent(this, AboutActivity.class);
                retval = true;
                break;
            case R.id.help_menu_item:
                intent = new Intent(this, HelpActivity.class);
                retval = true;
                break;
            default:
                retval = super.onOptionsItemSelected(item);
        }
        if(retval)
            this.startActivity(intent);
        return retval;
    }
}
