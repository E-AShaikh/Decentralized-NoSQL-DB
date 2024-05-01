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
    private String nodeUrl;
    private int nodePort;
    private String database;
    private String user;
    private String password;
}
