package ru.itis.rasimusv;

import com.beust.jcommander.*;

@Parameters(separators = "=")
public class Args {

    @Parameter (names = {"--port", "--p"})
    public int port = 7777;
}
