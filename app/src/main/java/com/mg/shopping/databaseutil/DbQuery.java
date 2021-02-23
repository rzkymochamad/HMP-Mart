package com.mg.shopping.databaseutil;

import com.mg.shopping.utility.Utility;

public class DbQuery {
    private FavouritesQueries favouritesQueries;
    private BrandFollowQueries brandFollowQueries;

    protected DbQuery() {

        Utility.Logger(DbQuery.class.getName(), "Setting : Working");

        favouritesQueries = new FavouritesQueries();
        brandFollowQueries = new BrandFollowQueries();


    }

    protected FavouritesQueries getFavouritesQueries(){
        return favouritesQueries;
    }

    protected BrandFollowQueries getBrandFollowQueries(){
        return brandFollowQueries;
    }

}
