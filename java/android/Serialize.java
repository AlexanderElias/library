import android.app.Activity;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
      
      public static class ObjectsList implements Serializable {
            private static final long serialVersionUID = 8736847634070552888L;
            private ArrayList<Object> list;

            public void addItem (Object object) {
                  list.add(object);
            }

            public void removeItem (Object object) {
                  list.remove(object);
            }

            public void setList () {
                  list = new ArrayList<>();
            }

            public ArrayList<Object> getList () {
                  return list;
            }

      }
}

/*
            //
            //      Write example
            //
            Serialize serialize = new Serialize(activity);
            Serialize.ObjectsList tempObjectsList = new Serialize.ObjectsList();

            if (serialize.fileExists()) {
                  tempObjectsList = (Serialize.ObjectsList) serialize.read();
            } else {
                  tempObjectsList.setList();
            }

            Serialize.ObjectsList objectsList = tempObjectsList;

            //Add object to list of serializable objects array
            objectsList.addItem(OBJECT_TO_BE_ADDED);

            //Overwrite serialized file
            serialize.write(objectsList);
            
            
            
            
            //
            //      Read Example
            //
            Serialize serialize = new Serialize(activity);

            if (serialize.fileExists()) {
                  Serialize.ObjectsList objectsList = (Serialize.ObjectsList) serialize.read();

                  for (int i = 0; i < objectsList.getList().size(); i++) {
                        ORIGINAL_TYPE obj = (ORIGINAL_TYPE) objectsList.getList().get(i);
                  }
            }

*/
