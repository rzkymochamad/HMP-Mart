package com.mg.shopping.databaseutil;

import android.database.DatabaseUtils;

import com.mg.shopping.interfaceutil.SqlQueries;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.utility.Utility;

public class QueryFactory extends DbQuery {


    public QueryFactory() {
        Utility.Logger(QueryFactory.class.getName(), "Setting : Working");
    }


    /**
     * <p>It is used to get Required Formmatted for getting required data</p>
     *
     * @param databaseObject
     * @return
     */
    public String getRequiredFormattedQuery(DatabaseObject databaseObject)
    {

        SqlQueries sqlQueries = null;
        String formattedQuery = null;
        DataObject dataObject = null;

        if (databaseObject.getDataObject() == null) {

            try {
                throw new QueryFactoryException("Something failed.", new Throwable("DataObject must not be null"));
            } catch (QueryFactoryException e) {
                e.printStackTrace();
            }

            return null;

        } else
            dataObject = databaseObject.getDataObject();

        switch (databaseObject.getTypeOperation()){
            case FAVOURITES:

                sqlQueries = getFavouritesQueries();
                if (databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT){

                    formattedQuery = String.format(sqlQueries.retrieve(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId()));

                }
                else if (databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE){

                    formattedQuery = String.format(sqlQueries.retrieve(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId()));

                }

                else if (databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_FAVOURITES_PRODUCT){

                    formattedQuery = String.format(sqlQueries.insert(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId()),sqlString(dataObject.getId()));

                }
                else if (databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_FAVOURITES_STORE){

                    formattedQuery = String.format(sqlQueries.insert(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId()),sqlString(dataObject.getId()));

                }

                else if (databaseObject.getDbOperation() == DbConstraint.DELETE_SPECIFIC_USER_FAVOURITES){

                    formattedQuery = String.format(sqlQueries.delete(databaseObject.getDbOperation())
                            , sqlString(dataObject.getId()));

                }

                return formattedQuery;

            case BRAND:

                sqlQueries = getBrandFollowQueries();
                if (databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_BRAND_FOLLOWING){

                    formattedQuery = String.format(sqlQueries.retrieve(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId())
                            ,  sqlString(dataObject.getId()));

                }
                else if (databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_BRAND_FOLLOWING){

                    formattedQuery = String.format(sqlQueries.insert(databaseObject.getDbOperation())
                            , sqlString(dataObject.getUserId()),sqlString(dataObject.getId()));

                }
                else if (databaseObject.getDbOperation() == DbConstraint.DELETE_SPECIFIC_USER_FOLLOWING){

                    formattedQuery = String.format(sqlQueries.delete(databaseObject.getDbOperation())
                            , sqlString(dataObject.getId()));

                }

                return formattedQuery;
            default:
                return formattedQuery;


        }





    }


    /**
     * <p>It is used to convert String into
     * Database friendly String </p>
     *
     * @param data
     * @return
     */
    private String sqlString(String data) {
        String sql = null;

        if (Utility.isEmptyString(data))
            sql = "null";
        else {
            sql = DatabaseUtils.sqlEscapeString(data);
        }

        return sql;

    }

    protected class QueryFactoryException extends Exception {

        public QueryFactoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
