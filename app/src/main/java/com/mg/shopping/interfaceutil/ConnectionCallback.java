package com.mg.shopping.interfaceutil;

import com.mg.shopping.objectutil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
