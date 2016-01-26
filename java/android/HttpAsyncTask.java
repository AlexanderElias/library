
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.URL;
import java.net.URLConnection;
//Must Import org.apache.commons.io
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


public class ApiAsyncTask extends AsyncTask<String, Integer, JSONObject> {
      Activity activity;
      OnTaskCompleted listener;

      ProgressBar progressBar;
      View progressLayout;
      String url;

      public ApiAsyncTask (Activity activity, String url, OnTaskCompleted listener) {
            this.activity = activity;
            this.url = url;
            this.listener = listener;
            
            progressLayout = activity.findViewById(R.id.progressLayout);
            progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
      }

      @Override
      protected void onPreExecute() {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
      }

      @Override
      protected void onProgressUpdate(Integer... value){
            progressBar.setProgress(value[0]);
      }

      @Override
      protected JSONObject doInBackground(String... params) {

            try {
                  URL Url = new URL(url);

                  URLConnection urlConnection = Url.openConnection();
                  urlConnection.connect();

                  String jsonString = IOUtils.toString(urlConnection.getInputStream());

                  return new JSONObject(jsonString);

            } catch (MalformedURLException e) {
                  e.printStackTrace();
                  return null;
            } catch (IOException e) {
                  e.printStackTrace();
                  return null;
            } catch (JSONException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      @Override
      protected void onPostExecute(JSONObject result) {
            progressLayout.setVisibility(View.INVISIBLE);
            listener.onTaskCompleted(result);
      }
}


/*

    //Interface for ApiAsyncTask class
    public interface OnTaskCompleted {
        void onTaskCompleted(JSONObject result);
    }

    //Callback for ApiAsyncTask class
    public class OnTaskCompletedCallback implements OnTaskCompleted{
        @Override
        public void onTaskCompleted(JSONObject result) {
            //Use result
        }
    }


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
