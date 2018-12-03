package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.questionnaires_menu_item:
                intent = new Intent(this, QuestionnaireListActivity.class);
                retval = true;
                break;
            case R.id.questions_menu_item:
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.history_menu_item:
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.settings_menu_item:
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.contact_menu_item:
                intent = new Intent(this, null);
                retval = true;
                break;
            case R.id.about_menu_item:
                intent = new Intent(this, null);
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
