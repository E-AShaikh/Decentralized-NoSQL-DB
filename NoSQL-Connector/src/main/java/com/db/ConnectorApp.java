package com.db;

import com.db.connection.NoSQLDatabaseConnection;
import com.db.repository.CRUDNoSQLRepository;
import com.db.test.model.BankAccount;
import com.db.util.DataType;
import com.db.util.JSONUtil;
import com.db.model.request.User;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Hello world!
 *
 */
public class ConnectorApp {
//    static NoSQLDatabaseConnection connection;
    public static void main(String[] args) {
//        connection = NoSQLDatabaseConnection.getInstance();

//        while (true) {
//            // Perform some operation or wait for user input
//            // For example, you could add a sleep to avoid high CPU usage
//            try {
//                Thread.sleep(1000); // Sleep for 1 second
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Class<?> clazz = BankAccount.class;
        Field[] fields = clazz.getDeclaredFields();

        JSONObject schema = new JSONObject();
        for (Field field : fields) {
            try {
                schema.put(field.getName(), DataType.valueOf(field.getType().getSimpleName().toUpperCase()));
            } catch (IllegalArgumentException e) {
                schema.put(field.getName(), "STRING");
            }
        }
        System.out.println(schema);

    }
}
