package com.mg.shopping.databaseutil;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SqlQueries;
import com.mg.shopping.utility.Utility;

public class BrandFollowQueries implements SqlQueries {
    private String tag = BrandFollowQueries.class.getName();

    public BrandFollowQueries() {
        Utility.Logger(tag, "Setting : Working");
    }

    @Override
    public String retrieve(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.RETRIEVE)
            query = "SELECT * FROM " + Constant.DatabaseColumn.BRAND_TABLE_NAME;

        else  if (dbConstraint == DbConstraint.RETRIEVE_SPECIFIC_BRAND_FOLLOWING)

            query = "SELECT *  FROM " + Constant.DatabaseColumn.BRAND_TABLE_NAME
                    +" WHERE "+Constant.DatabaseColumn.BRAND_COLUMN_USER_ID+"=%s ";



        /* AND record.type = %s */



        return query;
    }

    @Override
    public String update(DbConstraint dbConstraint) {
        return null;
    }

    @Override
    public String insert(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.INSERT_NEW_BRAND_FOLLOWING)
            query = "INSERT INTO " + Constant.DatabaseColumn.BRAND_TABLE_NAME
                    + "("
                    + Constant.DatabaseColumn.BRAND_COLUMN_USER_ID + ","
                    + Constant.DatabaseColumn.BRAND_COLUMN_OBJECT_ID
                    + ") VALUES (%s,%s)";



        return query;
    }

    @Override
    public String delete(DbConstraint dbConstraint) {
        String query = null;

        if (dbConstraint == DbConstraint.DELETE_SPECIFIC_USER_FOLLOWING)
            query = "DELETE FROM " + Constant.DatabaseColumn.BRAND_TABLE_NAME
                    +" WHERE "+Constant.DatabaseColumn.BRAND_COLUMN_ID+"=%s";



        return query;
    }


}
