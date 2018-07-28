package main;

public class Error extends Exception {

    String message;
    String traceback;

    public Error(String message, String odoo_traceback) {
        this.message = message;
        this.traceback = odoo_traceback;
    }

}