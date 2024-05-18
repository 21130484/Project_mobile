package com.example.handmakeapp;

public class ServerInformation {
    public final static String SERVER_IP = "172.20.47.234";//ipconfig
    public final static String SERVER_PORT = "8080";

    public static String getAbsoluteURL() {
        return "http://" + SERVER_IP + ":" + SERVER_PORT + "/HandMadeStore";
    }
}
