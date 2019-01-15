package com.netcracker.students.BatyrkinAndrew;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/calc")
public class CalculationRestService {

    @GET
    @Path("/help")
    @Produces(MediaType.TEXT_HTML)
    public String printHelp() {
        return "<html>" +
                "<title>" + "Help for calculation service" + "</title>" +
                "<body>" +
                "<h1>" + "Basic functions:" + "</h1>" +
                "<h2>" + "For different server response type " + "<br>" +
                "in URI (/rest/calc/YOUR_TYPE/..) you should write:" + "</h2>" +
                "<ul> " +
                "<li><pre>" + "TEXT_HTML:        th" + "</pre></li>" +
                "<li><pre>" + "TEXT_PLAIN:       tp" + "</pre></li>" +
                "<li><pre>" + "APPLICATION_JSON: aj" + "</pre></li>" +
                "</ul>" +
                "<h2>" + "ACTION: add, dividing, multiply, division." + "<br>" +
                "In URI (/rest/calc/) you should write:" + "</h2>" +
                "<ul> " +
                "<li><pre>" + "ADD:      add/a/b for \"a + b\"" + "</pre></li>" +
                "<li><pre>" + "SUBTRACT: did/a/b for \"a - b\"" + "</pre></li>" +
                "<li><pre>" + "MULTIPLY: mul/a/b for \"a * b\"" + "</pre></li>" +
                "<li><pre>" + "DIVISION: div/a/b for \"a / b\"" + "</pre></li>" +
                "</ul>" +
                "<h2>" + "Results types:" + "</h2>" +
                "<ul> " +
                "<li><pre>" + "1548     -  any number;" + "</pre></li>" +
                "<li><pre>" + "ERROR_a  -  invalid ACTION;" + "</pre></li>" +
                "<li><pre>" + "ERROR_d  -  one or both numbers consist of not only DIGITS;" + "</pre></li>" +
                "<li><pre>" + "ERROR_z  -  division by ZERO." + "</pre></li>" +
                "</ul>" +
                "<h1>" + "For example:" + "</h1>" +
                "<p><b>" + "localhost:8080/rest/calc/th/mul/15/8" + "</b>" + " ---> 15 * 8, " + "response type - TEXT_HTML" + "</p>" +
                "<p><b>" + "localhost:8080/rest/calc/tp/add/15/8" + "</b>" + " ---> 15 + 8, " + "response type - TEXT_PLAIN" + "</p>" +
                "<p><b>" + "localhost:8080/rest/calc/aj/div/15/8" + "</b>" + " ---> 15 / 8, " + "response type - APPLICATION_JSON" + "</p>" +
                "</body>" +
                "</html>";
    }

    @GET
    @Path("/th/{action}/{a}/{b}")
    @Produces(MediaType.TEXT_HTML)
    public String calcTH(@PathParam("a") String a, @PathParam("action") String action, @PathParam("b") String b) {
        return "<html><body>" + "<br>" +
                "a = " + a + "<br>" +
                "b = " + b + "<br>" +
                "action = " + action + "<br>" +
                "result = " +
                getResultAndError(a, b, action) +
                "</body></html>";
    }

    @GET
    @Path("/tp/{action}/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String calcTP(@PathParam("a") String a, @PathParam("action") String action, @PathParam("b") String b) {
        return getResultAndError(a,b,action);
    }

    @GET
    @Path("/aj/{action}/{a}/{b}")
    @Produces(MediaType.APPLICATION_JSON)
    public String calcAJ(@PathParam("a") String a, @PathParam("action") String action, @PathParam("b") String b) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", a);
        jsonObject.put("b", b);
        jsonObject.put("action", action);
        jsonObject.put("result", getResultAndError(a, b, action));
        return jsonObject.toString();
    }

    private String getResultAndError(String a, String b, String action) {
        BigDecimal result = null;
        String error = "";
        if (isIntegers(a, b)) {
            int first = Integer.parseInt(a);
            int last = Integer.parseInt(b);
            switch (action) {
                case "add": {
                    result = BigDecimal.valueOf(first + Integer.parseInt(b));
                    break;
                }
                case "sub": {
                    result = BigDecimal.valueOf(first - Integer.parseInt(b));
                    break;
                }
                case "mul": {
                    result = BigDecimal.valueOf(first * Integer.parseInt(b));
                    break;
                }
                case "div": {
                    if (last != 0) {
                        double fir = Double.parseDouble(a);
                        double las = Double.parseDouble(b);
                        result = BigDecimal.valueOf(fir / las);
                    } else {
                        error = "ERROR_z";
                    }
                    break;
                }
                default: {
                    error = "ERROR_a";
                }
            }
        } else {
            error = "ERROR_d";
        }
        return error.equals("") ? result.toString() : error;
    }


    private static boolean isIntegers(String a, String b) {
        String regex = "-?\\d+";
        return a.matches(regex) && b.matches(regex);
//        return isIntegers(a) && isIntegers(b);
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

}


    /* @GET
    @Path("/add/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@PathParam("a") String a, @PathParam("b") String b) {
        *//*int result;
        if (isIntegers(a, b)) {
            result = Integer.parseInt(a) + Integer.parseInt(b);
            return a + " + " + b + " = " + result;
        } else {
            return "Only digits!";
        }*//*
        return calc(a, "add" ,b);
    }

    @GET
    @Path("/did/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String did(@PathParam("a") String a, @PathParam("b") String b) {
        *//*int result;
        if (isIntegers(a, b)) {
            result = Integer.parseInt(a) - Integer.parseInt(b);
            return a + " - " + b + " = " + result;
        } else {
            return "Only digits!";
        }*//*
        return calc(a, "did" ,b);
    }

    @GET
    @Path("/mul/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String mul(@PathParam("a") String a, @PathParam("b") String b) {
        return calc(a, "mul" ,b);
    }

    @GET
    @Path("/div/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String div(@PathParam("a") String a, @PathParam("b") String b) {
        return calc(a,"div", b);
    }*/

/*For TEXT_PLAIN */
//        BigDecimal result;
//        String act;
//        if (isIntegers(a, b)) {
//            int first = Integer.parseInt(a);
//            int last = Integer.parseInt(b);
//            switch (action) {
//                case "add": {
//                    result = BigDecimal.valueOf(first + Integer.parseInt(b));
//                    act = "+";
//                    break;
//                }
//                case "did": {
//                    result = BigDecimal.valueOf(first - Integer.parseInt(b));
//                    act = "-";
//                    break;
//                }
//                case "mul": {
//                    result = BigDecimal.valueOf(first * Integer.parseInt(b));
//                    act = "*";
//                    break;
//                }
//                case "div": {
//                    if (last != 0) {
//                        double fir = Double.parseDouble(a);
//                        double las = Double.parseDouble(b);
//                        result = BigDecimal.valueOf(fir / las);
//                        act = "/";
//                    } else {
//                        return "ERROR_z";
//                    }
//                    break;
//                }
//                default: {
//                    return "ERROR_a";
//                }
//            }
//            return a + ' ' + act + ' ' + b + " = " + result;
////            return result.toString();
//        } else {
//            return a + ' ' + action + ' ' + b + " = ERROR" + '\n' + "Only digits!";
////            return "ERROR_d";
//        }