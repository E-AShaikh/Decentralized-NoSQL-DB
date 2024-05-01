package com.db;

import com.db.repository.CRUDNoSQLRepository;
import com.db.util.JSONUtil;
import com.db.model.request.User;

/**
 * Hello world!
 *
 */
public class ConnectorApp
{
    public static void main( String[] args )
    {
        CRUDNoSQLRepository<User, String> test = new CRUDNoSQLRepository<User, String>();
        

    }

//    private void test() {
//
//        System.out.println(JSONUtil.generateJsonSchema(User));
//    }
}
