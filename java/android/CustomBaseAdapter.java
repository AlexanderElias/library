
import android.view.View;
import java.util.ArrayList;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;

public class CustomBaseAdapter extends BaseAdapter {
      private static final long ID_CONSTANT = 0x010101010L;
      private ArrayList<Object> objects;
      private Activity activity;

	public CustomBaseAdapter(Activity _activity) {
            activity = _activity;
      }

      public void addItems (ArrayList<Object> _objects) {
            objects = _objects;
      }

      @Override
      public int getCount () {
            return objects.size();
      }

      @Override
      public Object getItem (int position) {
            return objects.get(position);
      }

      // constant to position to create unique ID value
      @Override
      public long getItemId (int position) {
            return ID_CONSTANT + position;
      }

      @Override
      public View getView (int position, View row, ViewGroup parent) {

            // if we don't have a recycled view create a new view
            if (row == null) {
                  // creating the new view
                  row = LayoutInflater.from(activity).inflate(R.layout.row, parent, false);
            }

            // get the object from the collection
            Object object = (Object) getItem(position);

            // use the object from the collection to fill out the view
            TextView textViewFirst = (TextView) row.findViewById(R.id.rowTextFirst);
            textViewFirst.setText( object.toString() );

		// use the object from the collection to fill out the view
            TextView textViewSecond = (TextView) row.findViewById(R.id.rowTextSecond);
            textViewSecond.setText( object.toString() );

            return row;
      }
}


/*

	// MainActivity.java
	ListView listview = (ListView) view.findViewById(R.id.listView);
	CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(activity, ARRAY);
	listview.setAdapter(customBaseAdapter);


	<!-- listView.xml -->
	<ListView
	  android:id="@+id/listView"
	  android:layout_width="match_parent"
	  android:layout_height="match_parent"/>


	<!-- row.xml -->
	<LinearLayout
		xmlns:android="http://schemas.android.com/apk/res/android"
		android:orientation="horizontal"
		android:layout_width="match_parent"
		android:layout_height="match_parent">

	<TextView
		  android:id="@+id/rowTextFirst"
		  android:textColor="#000"
		  android:padding="4dp"
		  android:layout_width="wrap_content"
		  android:layout_height="wrap_content"/>

	<TextView
		  android:id="@+id/rowTextSecond"
		  android:textColor="#000"
		  android:padding="4dp"
		  android:layout_width="wrap_content"
		  android:layout_height="wrap_content"/>

	</LinearLayout>
*/
