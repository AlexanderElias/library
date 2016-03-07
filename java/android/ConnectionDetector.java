

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetector {

      private Activity activity;
      private AlertDialog.Builder alert;

      public ConnectionDetector(Activity activity) {
            this.activity = activity;

            alert = new AlertDialog.Builder(activity);
            alert.setNeutralButton("Close", null);
            alert.setTitle("Internet Connection Required");
            alert.setMessage("This application requires a network connection");
      }

      public boolean isConnected() {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            boolean isConnectedOrConnecting = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if( !isConnectedOrConnecting ) {
                  alert.show();
            }

            return isConnectedOrConnecting;
      }

}


/*
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
*/
