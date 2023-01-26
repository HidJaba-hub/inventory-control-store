package com.company.electro_store.util.managers;

import com.company.electro_store.dto.Response;
import com.company.electro_store.util.connections.TCPConnection;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandStorage {
    private static final Map<String, ServerCommand> commands = new HashMap<>();
    
    public static void addCommandToStorage(String command, ServerCommand function) {
        commands.put(command, function);
    }
    
    public static Response executeCommandFromStorage(String command, String requestString, TCPConnection client)
        throws RuntimeException {
        ServerCommand serverCommand = commands.get(command);
        if (serverCommand == null) {
            return new Response(Response.ResponseType.ERROR, "Command not exist");
        }
        try {
            return new Response(Response.ResponseType.OK,
                    serverCommand.command.apply(new ClientRequestString(requestString, client)));
        } catch (Exception exception) {
            return new Response(Response.ResponseType.ERROR, exception.getMessage());
        }
    }
    
    @AllArgsConstructor
    public static class ClientRequestString {
        public String requestString;
        public TCPConnection client;
    }
    
    @AllArgsConstructor
    public static class ServerCommand {
        public Function<ClientRequestString, Object> command;
        //интерфейс принимает аргумент типа ClientRequestString и выдает результат типа Object
    }
}
