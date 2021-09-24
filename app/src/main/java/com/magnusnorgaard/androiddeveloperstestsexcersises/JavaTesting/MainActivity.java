package com.magnusnorgaard.androiddeveloperstestsexcersises.JavaTesting;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.magnusnorgaard.androiddeveloperstestsexcersises.R;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

@SuppressLint({"SetTextI18n", "StaticFieldLeak"})
public class MainActivity extends AppCompatActivity {

    private Activity context;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = (Activity) this;
        button = findViewById(R.id.button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestAsyncTask();
            }
        });
    }

    public void TestAsyncTask(){
        String[] urls = { "https://www.boredapi.com/api/activity?type=recreational" };
        for (int i=0; i<urls.length; i++){
            new TestAsyncTask().execute(urls[i]);
        }
    }

    public class TestAsyncTask extends AsyncTask<String, Void, String> { // doInBackground, Methods, Response
        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            try {
                url = new URL(urls[0]);
                HttpsURLConnection http = (HttpsURLConnection) url.openConnection(); // open connection with url
                BufferedReader i = new BufferedReader(new InputStreamReader(http.getInputStream())); // get response from url
                String response = "";
                do{
                    response += String.valueOf(i.readLine());
                } while(i.readLine() != null); // do and while - read response if next line != null
                return response; // return response to onPostExecute
            } catch (Exception e) {
                return e.toString(); // return error to onPostExecute
            }
        }

        @Override
        protected void onPostExecute(String s) { // onResponseGet
            super.onPostExecute(s);
            TextView textView = context.findViewById(R.id.textView);
            try {
                textView.setText(new JSONObject(s).getString("activity"));
            } catch (Exception e) {
                textView.setText(String.valueOf(e));
            }
        }
    }
}