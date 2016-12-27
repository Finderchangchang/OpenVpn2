package net.openvpn.openvpn;

/**
 * Created by Finder丶畅畅 on 2016/12/27 22:52
 * QQ群481606175
 */

public class LoginModel {

    /**
     * status : success
     * time : 30
     * remain : 1024
     */

    private String status;
    private String time;
    private String remain;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }
}
