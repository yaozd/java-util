package com.kk.utils.json;


import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.Iterator;


/**
 * net.sf.json 改造成 FastJson
 * net.sf.json.toString() 效率太低，转成  fastjson.toString()
 */
public class FastjsonUtils2 {

    public static String json2String(JSONObject json) {
        com.alibaba.fastjson.JSONObject fastJson = json2fastJson(json);
        return fastJson.toString();
    }

    public static com.alibaba.fastjson.JSONObject json2fastJson(JSONObject json) {

        Iterator keys = json.keys();
        com.alibaba.fastjson.JSONObject fastJson = new com.alibaba.fastjson.JSONObject();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object o = json.opt(key);
            fastJson.put(key, object2fastJsonObject(o));
        }
        return fastJson;
    }

    private static Object object2fastJsonObject(Object obj) {

        if (obj == null || JSONNull.getInstance().equals(obj)) {
            return null;
        }
        if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            com.alibaba.fastjson.JSONArray fastArray = new com.alibaba.fastjson.JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                fastArray.add(object2fastJsonObject(jsonArray.opt(i)));
            }
            return fastArray;
        } else if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            Iterator keys = json.keys();
            com.alibaba.fastjson.JSONObject fastJson = new com.alibaba.fastjson.JSONObject();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object o = json.opt(key);
                fastJson.put(key, object2fastJsonObject(o));
            }
            return fastJson;
        }

        return obj;
    }
}
