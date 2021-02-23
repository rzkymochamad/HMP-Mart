package com.mg.shopping.managementutil;

import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.objectutil.RequestObject;

import java.util.List;

public abstract class ManagementBase {

    public abstract void sendRequestToServer(RequestObject requestObject);

    public abstract List<Object> getDataFromDatabase(DatabaseObject databaseObject);

    public abstract PrefObject getPreferences(PrefObject prefObject);

    public abstract void savePreferences(PrefObject prefObject);

}
