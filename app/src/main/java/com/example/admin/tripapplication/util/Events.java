package com.example.admin.tripapplication.util;

/**
 * Created by Admin on 9/21/2017.
 */

public class Events {

    public static class MessageEvent{
        String action;
        Object object;

        public MessageEvent(String action, Object object) {
            this.action = action;
            this.object = object;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
