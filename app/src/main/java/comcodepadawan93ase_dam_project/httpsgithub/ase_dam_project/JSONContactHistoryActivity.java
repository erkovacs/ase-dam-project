package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONContactHistoryActivity extends ListActivity {
private ProgressDialog dialog;
private static String url = "https://api.myjson.com/bins/k8838";
private static  final String TAG_CONTACT = "contact";
private static final String TAG_NAME = "name";
private static final String TAG_EMAIL = "email";
private static final String TAG_SUBJECT = "subject";
private static final String TAG_QUESTION ="question";
JSONArray contacts = null ;
ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      ListView listview = (ListView) findViewById(android.R.id.list);
        setContentView(R.layout.activity_jsonhistory);
        contactList = new ArrayList<HashMap<String, String>>();
        new GetContacts().execute();

    }
    public class GetContacts extends AsyncTask <String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(JSONContactHistoryActivity.this);
            dialog.setMessage("Not over yet..");
            dialog.setCancelable(false);
            dialog.show();

        }
        @Override
        protected String doInBackground(String...strings) {
            String jsonString = makeJSONCall(url);
            Log.d("Response:", "The content:" + jsonString);
            loadJSONObject(jsonString);
            return null;
        }
        public String makeJSONCall(String url) {
            String response = null;
            DefaultHttpClient client = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(url);
            try {
                httpResponse = client.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter newAdapter = new SimpleAdapter(JSONContactHistoryActivity.this, contactList, R.layout.activity_jsonelement,
                    new String[]{TAG_NAME, TAG_EMAIL, TAG_SUBJECT, TAG_QUESTION}, new int[]{R.id.tvNameJson, R.id.tvEmailJson, R.id.tvSubjectJson, R.id.tvQuestionJson});
            setListAdapter(newAdapter);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }




        public void loadJSONObject(String jsonString){
            if(jsonString != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonString);
                    contacts = jsonObject.getJSONArray(TAG_CONTACT);

                    for(int i = 0; i<contacts.length();i++){
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString(TAG_NAME);
                        String email = c.getString(TAG_EMAIL);
                        String subject = c.getString(TAG_SUBJECT);
                        String question = c.getString(TAG_QUESTION);

                        HashMap<String, String> contact = new HashMap<String, String>();
                        contact.put(TAG_NAME, name);
                        contact.put(TAG_EMAIL, email);
                        contact.put(TAG_SUBJECT, subject);
                        contact.put(TAG_QUESTION, question);
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else{
                Log.e("Error", "No data found!");
            }
        }
    }
}
