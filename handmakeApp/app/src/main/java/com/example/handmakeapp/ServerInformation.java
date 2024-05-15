package com.example.handmakeapp;

public class ServerInformation {
    public final static String SERVER_IP = "172.20.49.38";
    public final static String SERVER_PORT = "8080";

    public final static String getAbsoluteURL() {
        return "http://" + SERVER_IP + ":" + SERVER_PORT + "/HandMadeStore";
    }
}
