package com.company.electro_store.server;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.server.functions.accountant.SalariesFunctions;
import com.company.electro_store.server.functions.persons.PersonFunctions;
import com.company.electro_store.util.connections.TCPConnection;
import com.company.electro_store.util.managers.CommandStorage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class ClientBoot{
    @Getter
    private final TCPConnection tcpConnection;
    private final int clientNumber;
    @Getter
    @Setter
    private boolean quit = false;

    ClientBoot(TCPConnection tcpConnection, int clientNumber){
        this.tcpConnection=tcpConnection;
        this.clientNumber=clientNumber;
    }
    public void doClient() {
        try {
            Response serverResponse;
            Request clientRequest;

                clientRequest=tcpConnection.getRequest();
                try {
                    System.out.println("client #" + clientNumber + " -> server: ");
                    System.out.println(clientRequest);
                    serverResponse = CommandStorage.executeCommandFromStorage(clientRequest.getRequestType(),
                            clientRequest.getRequestString(), tcpConnection);
                } catch (Exception exception) {
                    serverResponse = new Response(Response.ResponseType.ERROR, "Not defined error!");
                    exception.printStackTrace();
                }

                if (!quit) {
                    System.out.println("server -> client #" + clientNumber + ": ");
                    System.out.println(serverResponse);
                    tcpConnection.makeResponse(serverResponse);
                }
        } catch (SocketException socketException){
            tcpConnection.disconnect();
            return;
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setFinalMinute(User clientUser, Timestamp timestamp_beg) throws IOException, ClassNotFoundException {//TODO установить новый день
        Timestamp timestamp_end=new Timestamp(System.currentTimeMillis());
        double minutes= TimeUnit.MILLISECONDS.toMinutes(timestamp_beg.getTime());
        double minutes2=TimeUnit.MILLISECONDS.toMinutes(timestamp_end.getTime());
        double time= (minutes2-minutes)/60;
        clientUser.setMinutes((time+clientUser.getMinutes()));
        PersonFunctions.saveOrUpdateUser(clientUser);

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Salaries salaries=SalariesFunctions.getSalaryByDate(sqlDate);
        if(salaries!=null){
            salaries.setSalary(salaries.getSalary()+(clientUser.getPost().getCost()*time));
        }
        else salaries=new Salaries(clientUser.getPost().getCost()*clientUser.getMinutes(),sqlDate);
        SalariesFunctions.saveOrUpdateSalaries(salaries);
    }
}
