
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


public class ApiAsyncTask extends AsyncTask<String, Integer, JSONObject> {

      ProgressBar progressBar;
      View progressLayout;

      AlertDialog.Builder alert;

      Activity activity;
      public HttpAsyncTask(Activity activity) {
            this.activity = activity;

            alert = new AlertDialog.Builder(activity);
            alert.setNeutralButton("Close",null);
            
            //setup variables
            
            //Progress circle
            progressLayout = activity.findViewById(R.id.progressLayout);
            progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
      }


      @Override
      protected void onPreExecute() {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
      }


      @Override
      protected JSONObject doInBackground(String... params) {

            try {
                  String webAddress = "";
                  URL url = new URL(webAddress);

                  URLConnection urlConnection = url.openConnection();
                  urlConnection.connect();

                  String jsonString = IOUtils.toString(urlConnection.getInputStream());

                  return new JSONObject(jsonString);
            }
            catch(Exception e) {
                  Log.e("TAG", "Could not establish URLConnection: " + e.toString());
                  return null;
            }
      }

      @Override
      protected void onPostExecute(JSONObject result) {

            progressLayout.setVisibility(View.INVISIBLE);

            try {
              
                //Assign variables to views
            }
            catch (Exception e) {
                  e.printStackTrace();
            }
      }


      @Override
      protected void onProgressUpdate(Integer... value){
            progressBar.setProgress(value[0]);
      }
}


/*
    <!-- progress.xml -->
        <RelativeLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/progressLayout"
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        <ProgressBar
            android:id="@+id/progressBar"
            android:minHeight="20dp"
            android:maxHeight="20dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            style="@android:style/Widget.ProgressBar.Small"/>
    </RelativeLayout>
    
    <!-- main.xml -->
        <include
            layout="@layout/progress"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
*/
