package com.db.commands;

import com.db.commands.actions.*;
import com.db.commands.queries.PingQuery;
import com.db.commands.queries.create.*;
import com.db.commands.queries.delete.*;
import com.db.commands.queries.find.*;
import com.db.commands.queries.update.*;
//import org.example.commands.TCP.*;
//import org.example.commands.UPD.InitializeCommand;
//import org.example.commands.UPD.RedirectCommand;
//import org.example.commands.UPD.SyncCommand;
//
//import org.example.commands.UPD.AddUserCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<CommandTypes,Command> commandsMap;
    public CommandFactory(){
        commandsMap=new HashMap<>();
        //TCP COMMANDS
        commandsMap.put(CommandTypes.CREATE_COLLECTION,new CreateCollectionQuery());
        commandsMap.put(CommandTypes.CREATE_DATABASE,new CreateDatabaseQuery());
        commandsMap.put(CommandTypes.CREATE_DOCUMENT,new CreateDocumentQuery());
        commandsMap.put(CommandTypes.CREATE_INDEX,new CreateIndexQuery());
        commandsMap.put(CommandTypes.DELETE_COLLECTION,new DeleteCollectionQuery());
        commandsMap.put(CommandTypes.DELETE_DATABASE,new DeleteDatabaseQuery());
        commandsMap.put(CommandTypes.DELETE_DOCUMENT,new DeleteDocumentQuery());
        commandsMap.put(CommandTypes.FIND_ALL,new FindAllQuery());
        commandsMap.put(CommandTypes.FIND,new FindQuery());
        commandsMap.put(CommandTypes.SYNC_UPDATE_DOCUMENT,new SyncUpdateDocumentQuery());
        commandsMap.put(CommandTypes.UPDATE_DOCUMENT,new UpdateDocumentQuery());
        commandsMap.put(CommandTypes.PING,new PingQuery());
        //UPD COMMANDS
        commandsMap.put(CommandTypes.ADD_USER, new AddUserAction());
        commandsMap.put(CommandTypes.INITIALIZE, new InitializeAction());
        commandsMap.put(CommandTypes.REDIRECT, new RedirectAction());
        commandsMap.put(CommandTypes.SYNC, new SyncAction());
    }
    public Command createCommand(CommandTypes commandType){
        return commandsMap.get(commandType);
    }
}

