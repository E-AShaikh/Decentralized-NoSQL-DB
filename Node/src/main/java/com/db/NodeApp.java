package com.db;


import com.db.services.AffinityService;
import com.db.services.DatabaseServices;
import org.json.simple.JSONArray;

import java.io.IOException;

public class NodeApp {
    public static void main( String[] args ) throws IOException {
        Server server = new Server();
        new Thread(server).start();

    }
}
