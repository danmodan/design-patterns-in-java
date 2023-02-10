package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectCloner {

    private ObjectCloner(){}

    public static <T extends Serializable> T deepCopy(T t) {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            oos.flush();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}