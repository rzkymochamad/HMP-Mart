package com.mg.shopping.fontutil;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by hp on 5/20/2018.
 */

public class Font {
    
    private Font(){
        
    }

    static final String UBUNTU_REGULAR = "Fonts/Ubuntu-Regular.ttf";
    static final String UBUNTU_LIGHT = "Fonts/Ubuntu-Light.ttf";
    static final String UBUNTU_MEDIUM = "Fonts/Ubuntu-Medium.ttf";
    static final String UBUNTU_BOLD = "Fonts/Ubuntu-Bold.ttf";
    static final String LEMON_MILK = "Fonts/LemonMilk.otf";
    static final String NEURO_POLITICAL = "Fonts/neuro_political.ttf";
    static final String PRIME_LIGHT = "Fonts/Prime Light.otf";
    static final String PRIME_REGULAR = "Fonts/Prime Regular.otf";
    static final String AQUATICO_REGULAR = "Fonts/Aquatico-Regular.otf";
    static final String RALEWAY_REGULAR = "Fonts/Raleway-Regular.ttf";
    static final String RALEWAY_SEMI_BOLD = "Fonts/Raleway-SemiBold.ttf";
    static final String RALEWAY_LIGHT = "Fonts/Raleway-Light.ttf";

    static final String IBM_SANS_LIGHT = "Fonts/IBMPlexSans-Light.ttf";
    static final String IBM_SANS_REGULAR = "Fonts/IBMPlexSans-Regular.ttf";
    static final String IBM_SANS_MEDIUM = "Fonts/IBMPlexSans-Medium.ttf";
    static final String IBM_SANS_BOLD = "Fonts/IBMPlexSans-Bold.ttf";

    static final String TYPO_REGULAR = "Fonts/Typo_Round_Regular_Demo.otf";
    static final String TYPO_LIGHT = "Fonts/Typo_Round_Light_Demo.otf";
    static final String TYPO_BOLD = "Fonts/Typo_Round_Bold_Demo.otf";


    public static Typeface ubuntuRegularFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), UBUNTU_REGULAR);
    }

    public static Typeface ubuntuMediumFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), UBUNTU_MEDIUM);
    }

    public static Typeface ubuntuBoldFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), UBUNTU_BOLD);
    }

    public static Typeface ubuntuLightFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), UBUNTU_LIGHT);
    }

    public static Typeface lemonMilkFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), LEMON_MILK);
    }

    public static Typeface neuroPoliticalFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), NEURO_POLITICAL);
    }

    public static Typeface primeLightFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), PRIME_LIGHT);
    }

    public static Typeface primeRegularFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), PRIME_REGULAR);
    }

    public static Typeface aquaticoRegularFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), AQUATICO_REGULAR);
    }

    public static Typeface ralewayRegularFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), RALEWAY_REGULAR);
    }

    public static Typeface ralewayLightFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), RALEWAY_LIGHT);
    }

    public static Typeface ralewaySemiBoldFont(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), RALEWAY_SEMI_BOLD);
    }





}
