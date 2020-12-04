package ru.itis.rasimusv;

import com.beust.jcommander.*;

@Parameters(separators = "=")
public class Args {

    @Parameter (names = {"--server-ip", "--i"})
    public String host = "localhost";

    @Parameter (names = {"--server-port", "--p"})
    public int port = 7777;
}
