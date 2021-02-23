package com.mg.shopping.interfaceutil;

import com.mg.shopping.databaseutil.DbConstraint;

/**
 * Created by hp on 2/26/2018.
 */

public interface SqlQueries {

    /**
     * <p>Give query of SQL which can retrieve all data from Database</p>
     *
     * @return give SQl Query for retreiving all data
     */
    String retrieve(DbConstraint dbConstraint);

    /**
     * <p>Give query of SQL which can update data in Database</p>
     *
     * @return give SQl Query for Updating data
     */
    String update(DbConstraint dbConstraint);

    /**
     * <p>Give query of SQL which can insert data in Database</p>
     *
     * @return give SQl Query for inserting  data
     */
    String insert(DbConstraint dbConstraint);

    /**
     * <p>Give query of SQL which can delete  data from Database</p>
     *
     * @return give SQl Query for delete data
     */
    String delete(DbConstraint dbConstraint);




}
