package com.company.electro_store.server.functions;

import com.company.electro_store.entity.persons.User;
import com.company.electro_store.server.ClientBoot;
import com.company.electro_store.util.connections.TCPConnection;

import java.io.IOException;
import java.util.Arrays;

public class GeneralFunctions extends Function {
    public static User login(User user, TCPConnection client) {
        String str=user.getLogin();
        User currentUser = userService.read(user.getLogin());
        if (currentUser!=null&&Arrays.equals(currentUser.getPassword(), user.getPassword())) {
            client.setClientUser(currentUser);
            client.setMinute();
            return currentUser;
        }
        return null;
    }
    
    public static User logout(TCPConnection client) throws IOException, ClassNotFoundException {
        client.setFinalMinute(client.getClientUser());
      //  client.setClientUser(null);
        return null;
    }
    
    public static void quit(TCPConnection client) {//TODO
      //  client.setQuit(true);
    }
}
