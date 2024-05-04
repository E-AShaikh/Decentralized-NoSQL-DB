package com.db.test;


import com.db.model.request.BootstrapperRequests;
import com.db.test.model.BankAccount;
import com.db.test.model.BankAccountType;
import com.db.test.service.BankAccountService;
import com.db.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class main {
    public static void main(String[] args) throws IOException {
//        BankAccount example = new BankAccount(12344, "Ezz", 366.1, false, "Salary");
        BankAccountService service = new BankAccountService();
        service.withdrawal("12344", 30);
//        service.addAccount(example);
        try {
            Thread.sleep(1000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service.getAllAccounts());

//        JSONObject test = new JSONObject("{\"balance\":3050.1,\"clientName\":\"ali\",\"accountType\":\"SALARY\",\"id\":1234,\"hasInsurance\":true,\"_version\":0}");
//        JSONUtil.mapJsonToObject(test, BankAccount.class);
//        Socket socket = new Socket("localhost", 8080);
//
//        JSONObject request = new JSONObject();
//        request.put("username", "ezz");
//        request.put("password", "ezz");
//        request.put("requestType", BootstrapperRequests.REGISTER.toString());
//
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(request.toString().getBytes());
//        outputStream.flush();

        while (true) {
            // Perform some operation or wait for user input
            // For example, you could add a sleep to avoid high CPU usage
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}