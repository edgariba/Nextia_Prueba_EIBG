package com.nextia.PruebaEb.Utils.Header;

public class HeaderResponse {
    public String severity;
    public int code;
    public String message;

    /**
     * Constructor para el return del response entity
     * @param severity
     * @param code
     * @param message
     */
    public HeaderResponse(String severity, int code, String message) {
        this.severity = severity;
        this.code = code;
        this.message = message;
    }
}
