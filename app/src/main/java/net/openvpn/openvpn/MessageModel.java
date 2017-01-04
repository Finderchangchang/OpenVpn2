package net.openvpn.openvpn;

import java.io.Serializable;
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


    public static class MsgBean implements Serializable {
        /**
         * id : 10
         * category_id : 1
         * title : 移动2
         * content : 测试
         * visit_count : 0
         * timeline : 2017-01-03 11:49:55
         */

        private String id;
        private String category_id;
        private String title;
        private String content;
        private String visit_count;
        private String timeline;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVisit_count() {
            return visit_count;
        }

        public void setVisit_count(String visit_count) {
            this.visit_count = visit_count;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }
    }
}
