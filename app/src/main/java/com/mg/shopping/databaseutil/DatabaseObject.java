package com.mg.shopping.databaseutil;

import com.mg.shopping.objectutil.DataObject;


public class DatabaseObject {
    private DbConstraint dbOperation;
    private TypeConstraint typeOperation;
    private DataObject dataObject;

    public DbConstraint getDbOperation() {
        return dbOperation;
    }

    public DatabaseObject setDbOperation(DbConstraint dbOperation) {
        this.dbOperation = dbOperation;
        return this;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public DatabaseObject setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    public TypeConstraint getTypeOperation() {
        return typeOperation;
    }

    public DatabaseObject setTypeOperation(TypeConstraint typeOperation) {
        this.typeOperation = typeOperation;
        return this;
    }

    @Override
    public String toString() {
        return "DatabaseObject{" +
                "dbOperation=" + dbOperation +
                ", typeOperation=" + typeOperation +
                ", appInfoObject=" + dataObject +
                '}';
    }
}
