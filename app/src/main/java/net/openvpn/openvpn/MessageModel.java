package net.openvpn.openvpn;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class MessageModel {

    /**
     * code : 1
     * msg : [{"id":"1","name":"移动","description":"","sortby":"1","hidden":"0"},{"id":"2","name":"联通","description":"","sortby":"2","hidden":"0"},{"id":"3","name":"电信","description":"","sortby":"3","hidden":"0"}]
     * data : null
     */

    private int code;
    private Object data;
    private List<MsgBean> msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 1
         * name : 移动
         * description :
         * sortby : 1
         * hidden : 0
         */

        private String id;
        private String name;
        private String description;
        private String sortby;
        private String hidden;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSortby() {
            return sortby;
        }

        public void setSortby(String sortby) {
            this.sortby = sortby;
        }

        public String getHidden() {
            return hidden;
        }

        public void setHidden(String hidden) {
            this.hidden = hidden;
        }
    }
}
