import android.app.Activity;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Alexander Elias
 */

public class Serialize {
      private final static String FILENAME = "serialized.txt";
      private Activity activity;
      private Context context;

      public Serialize(Activity activity) {
            this.activity = activity;
      }

      public Serialize(Context context) {
            this.context = context;
      }

      public boolean fileExists() {
            return activity.getFileStreamPath(FILENAME).exists();
      }

      public void write (Object object) {
            try {
                  FileOutputStream fos;
                  if (activity != null) {
                        fos = activity.openFileOutput(FILENAME, activity.MODE_PRIVATE);
                  } else {
                        fos = context.openFileOutput(FILENAME, activity.MODE_PRIVATE);
                  }

                  ObjectOutputStream oos = new ObjectOutputStream(fos);

                  //Write the serializable object to a file
                  oos.writeObject(object);
                  oos.close();

            } catch (Exception e){
                  e.printStackTrace();
            }
      }

      public Object read () {
            Object object;

            try {
                  FileInputStream fis;
                  if (activity != null) {
                        fis = activity.openFileInput(FILENAME);
                  } else {
                        fis = context.openFileInput(FILENAME);
                  }

                  ObjectInputStream ois = new ObjectInputStream(fis);

                  //Read the serialized object from the file to an object
                  object = ois.readObject();
                  ois.close();

                  return object;

            } catch(Exception e) {
                  e.printStackTrace();
                  return null;
            }
      }
}
