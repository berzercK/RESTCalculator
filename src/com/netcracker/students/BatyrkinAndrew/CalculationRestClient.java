package com.netcracker.students.BatyrkinAndrew;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CalculationRestClient {
    private static Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));

    public static void main(String[] args) {
//        String typeResults = "aj";
//        String action = "add";
//        String a = "10";
//        String b = "15";

//        String typeResults = "aj";
//        String action = "add";
//        String a = "10";
//        String b = "0";

//        String typeResults = "tp";
//        String action = "mul";
//        String a = "100";
//        String b = "15";

//        String typeResults = "tp";
//        String action = "div";
//        String a = "100a";
//        String b = "15";

        String typeResults = "tp";
        String action = "did";
        String a = "124";
        String b = "15";

        makeCalc(a, b, action, typeResults);
    }

    private static void makeCalc(String a, String b, String action, String typeResults) {
        if (checkTypeResult(typeResults)) {
            String way = "http://localhost:8080/rest/calc";
            WebTarget webTarget = client.target(way).path(typeResults).path(action).path(a).path(b);
            Invocation.Builder builder;
            if (typeResults.equals("aj")) {
                builder = webTarget.request(MediaType.APPLICATION_JSON);
            } else {
                builder = webTarget.request(MediaType.TEXT_PLAIN);
            }
            System.out.println(builder.get().readEntity(String.class));
        } else {
            System.out.println("Incorrect type of results!");
        }
    }

    private static boolean checkTypeResult(String typeResults) {
        return typeResults.equals("tp") || typeResults.equals("aj");
    }

}
