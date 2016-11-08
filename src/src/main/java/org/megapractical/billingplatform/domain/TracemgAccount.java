package org.megapractical.billingplatform.domain;


import java.time.ZonedDateTime;

/**
 * Created by Admin on 07/11/2016.
 */

public class TracemgAccount {

    public TracemgAccount(){}

    private long id;
    private String timestamp;
    private String modulo;
    private String rfc;
    private String operation;
    private String result;

    public long getID(){return id;}

    public void setID(long id){this.id = id; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
