package com.example.vinsergey.depoaddroutes;

import java.util.List;

public class test {

    public String name_route;
    public String type;
    public List<ForwardBean> forward;
    public List<BackwardBean> backward;

    public static class ForwardBean {
        public String name_station;
        public List<String> holiday;
        public List<String> workday;
    }

    public static class BackwardBean {
        public String name_station;
        public List<String> holiday;
        public List<String> workday;
    }
}
