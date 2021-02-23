package com.mg.shopping.databaseutil;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SqlQueries;
import com.mg.shopping.utility.Utility;

public class FavouritesQueries implements SqlQueries {
    private String tag = FavouritesQueries.class.getName();
    private static final String WHERE = " WHERE ";

    public FavouritesQueries() {
        Utility.Logger(tag, "Setting : Working");
    }

    @Override
    public String retrieve(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.RETRIEVE)
            query = "SELECT * FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME;

        else  if (dbConstraint == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT)
            query = "SELECT *  FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                    +WHERE+Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID+"=%s AND "+Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE+"='favourite_product'";

        else  if (dbConstraint == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE)
            query = "SELECT *  FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                    +WHERE+Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID+"=%s AND "+Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE+"='favourite_store'";

        return query;
    }

    @Override
    public String update(DbConstraint dbConstraint) {
        return null;
    }

    @Override
    public String insert(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.INSERT_NEW_FAVOURITES_PRODUCT)
            query = "INSERT INTO " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                    + "("
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + ","
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_ID + ","
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE
                    + ") VALUES (%s,%s,'favourite_product')";

        else if (dbConstraint == DbConstraint.INSERT_NEW_FAVOURITES_STORE)
            query = "INSERT INTO " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                    + "("
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + ","
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_ID + ","
                    + Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE
                    + ") VALUES (%s,%s,'favourite_store')";

        return query;
    }

    @Override
    public String delete(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.DELETE_SPECIFIC_USER_FAVOURITES)
            query = "DELETE FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                    +WHERE+Constant.DatabaseColumn.FAVOURITES_COLUMN_ID+"=%s";



        return query;
    }


}
