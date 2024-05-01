package com.db.model.request;

import com.db.model.query.QueryType;
import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryRequest {
    private QueryType query;
    private String database;
    private String collection;
    private Map<String, Object> body;
}
