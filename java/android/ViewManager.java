import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

public class ViewManager {
      private Activity activity;
      private FragmentManager fragmentManager;


      public ViewManager (Activity activity) {
            this.activity = activity;
            fragmentManager = activity.getFragmentManager();
      }

      public void replaceContainer(Integer containerViewId, Fragment fragment, String tag) {

            fragmentManager.beginTransaction()
                    .replace(containerViewId, fragment, tag)
                    .addToBackStack(null)
                    .commit();

      }
}

/*
                  Use this in your main activity

            private FragmentManager fragmentManager = getFragmentManager();
		FragmentManager fragmentManager = getFragmentManager();
		ViewManager viewManager.replaceContainer(R.id.fragment_container, FRAGMENT, FRAGMENT.TAG);

            @Override
            public void onBackPressed() {
                  if(fragmentManager.getBackStackEntryCount() == 0) {
                        super.onBackPressed();
                  }
                  else {
                        fragmentManager.popBackStack();
                  }
            }




                        Use this in the layout file to be the tag that is replaced

            <FrameLayout
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  android:id="@+id/fragment_container"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"/>




                  If you want to use the up navigation

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                  switch (item.getItemId()) {
                        case android.R.id.home: {
                              if (fragmentManager.getBackStackEntryCount() > 0) {
                                    fragmentManager.popBackStack();
                              }
                              return true;
                        }
                  }
                  return super.onOptionsItemSelected(item);
            }
*/
