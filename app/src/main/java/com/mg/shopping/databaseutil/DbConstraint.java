package com.mg.shopping.databaseutil;

public enum DbConstraint {

    RETRIEVE,
    RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT,
    RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE,
    RETRIEVE_SPECIFIC_BRAND_FOLLOWING,

    INSERT,
    INSERT_NEW_FAVOURITES_PRODUCT,
    INSERT_NEW_FAVOURITES_STORE,
    INSERT_NEW_BRAND_FOLLOWING,

    INSERT_NEW_RECORD,
    UPDATE,

    DELETE ,
    DELETE_SPECIFIC_USER_FAVOURITES,
    DELETE_SPECIFIC_USER_FOLLOWING

}
