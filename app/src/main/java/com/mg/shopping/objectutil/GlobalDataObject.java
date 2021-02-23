package com.mg.shopping.objectutil;

import java.util.ArrayList;
import java.util.List;

public class GlobalDataObject {

    static RequestObject requestObject = null;
    ArrayList<Object> objectArrayList = new ArrayList<>();

    public List<Object> getObjectArrayList() {
        return objectArrayList;
    }

    public GlobalDataObject setObjectArrayList(List<Object> objectArrayList) {
        this.objectArrayList.addAll(objectArrayList);
        return this;
    }

    public static RequestObject getRequestObject() {
        return requestObject;
    }

    public static void setRequestObject(RequestObject requestObject) {
        GlobalDataObject.requestObject = requestObject;
    }


}
