package net.openvpn.openvpn;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class Model {

    /**
     * code : 1
     * msg : [{"id":"7","category_id":"1","title":"测试移动","content":"","visit_count":"0","timeline":"2016-12-16 19:35:48"}]
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
         * id : 7
         * category_id : 1
         * title : 测试移动
         * content :
         * visit_count : 0
         * timeline : 2016-12-16 19:35:48
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
