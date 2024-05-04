package com.db.model.connection;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoSQLConfig {
    private String bootStrappingNodeUrl;
    private int bootStrappingPort;
    private String hostname;
    private int Port;
    private String database;
    private boolean isCreated;
    private String user;
    private String password;
}
