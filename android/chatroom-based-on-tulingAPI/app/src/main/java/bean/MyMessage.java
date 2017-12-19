package bean;

import java.util.Date;

/**
 * Created by dell on 2016/5/1.
 */
public class MyMessage {

    private String name;
    private String msg;
    private Date date;
    private Type type;

    public enum Type{
        INCOMING,OUTCOMING
    }

    public MyMessage(){}

    public MyMessage(String msg, Date date, Type type) {
        this.msg = msg;
        this.date = date;
        this.type = type;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
