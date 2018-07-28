package main;

import java.net.MalformedURLException;
import java.net.URL;

public class Proxy {

    private URL rootUrl;
    private int timeout;

    public Proxy(String host, int port, int timeout, boolean sslEnabled) throws MalformedURLException {
        String protocol;
        if (sslEnabled == true) {
            protocol = "https://";
        } else {
            protocol = "http://";
        }
        this.rootUrl = new URL(protocol, host, port, null);
        this.timeout = timeout;

    }

    public URL getUrl() {
        return this.rootUrl;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
