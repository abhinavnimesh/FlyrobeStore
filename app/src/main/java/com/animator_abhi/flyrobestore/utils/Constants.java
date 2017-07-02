package com.animator_abhi.flyrobestore.utils;

/**
 * Created by monisha on 31/03/16.
 */
public class Constants {

    public static final String HOME = "Home";
    public static final String PREF_SORT = "pref_sort";

    //--- Sorting Constants---
    public static final int SORT_PRICE_LH = 0;
    public static final int SORT_PRICE_HL = 1;
    public static final int SORT_POPULARITY = 2;
    public static final String PREF_COLOR_RESERVE = "pref_color_reserve";
    public static final String PREF_PRICE_RESERVE = "pref_price_reserve";
    public static final String PREF_BRAND_RESERVE = "pref_brand_reserve";
    public static final String PREF_CATEGORY_RESERVE = "pref_category_reserve";
    public static final int REQUEST_CODE_FILTERS_ACTIVITY = 1800;
    public static final String INTENT_KEY_FILTERS_DATA = "intent_key_filters_data";
    public static final String INTENT_KEY_USE_FILTERS = "intent_key_use_filters";

    //--- Login Constants---

    public final static String IS_LOGGED_IN="is_logged_in";

    public static final String LOGOUT = "Logout";

    public final static String OTP="otp";

    public final static String FIRTSNAME="firstname";

    public final static String LASTNAME="lastname";

    public final static String PHONE_NUMBER="phone_number";

    public final static String FE_ID="fe_id";


    //Proforma ID
    public final static  String PROFORMA_ID="proforma_id";

    public final static  String NUMBER_OF_ITEMS="numOfItems";

    public final static  String ADDRESS="address";

    public final static  String CONTACT_NUMBER="contact_number";

    public final static  String RECEPIENT_NUMBER="recepient_number";

    public static final String CUSTOMER_NAME = "customer_name" ;

    public static final String GENDER = "gender" ;

    public static final String USER_MEASUREMENT_ID = "user_measurement_id" ;

    public static final String PARENT_USER_ID = "parent_user_id" ;

    public static final String SELECT_GENDER = "-- Select Gender --" ;

    public static final String MALE = "Male" ;

    public static final String FEMALE = "Female" ;

    public static boolean UPDATE_MSR_EDIT_FLAG = false ;

    public static final String MAX_BUST = "max_bust" ;

    public static final String MIN_BUST = "min_bust" ;

    public static final String MAX_WAIST = "max_waist" ;

    public static final String MIN_WAIST = "min_waist" ;

    public static final String MAX_HIP = "max_hip" ;

    public static final String MIN_HIP = "min_hip" ;

    public static final String HEIGHT = "height" ;

    public static final String COMMENT = "comment" ;

    public static final String ORDER_STATUS = "-- Select --";

    public static final String CANCELLED_ORDER_BY_US = "Cancel Order";

    public static final String MARK_MEASURED = "Measured";

    public static final String CONFIRM_WITH_MEASUREMENT_FROM_PLACED = "Confirm With Measurement";

    public static final String MEASUREMENT_FAILED = "Measurement Failed";

    public static final String SIZE_GUIDE_IMAGE = "size_guide_image";

    public static final String ORDER_STATUS_LIST = "order_status_list";

    public static final String REASON_ID = "reasonId" ;

    public static final String OFM_STATUS = "OFM" ;

    public static final String FEA_STATUS = "FEA" ;


    //--- Dress Details Constants---
    public static final String WILL_FIT = "Will Fit";
    public static final String NOT_FIT = "Not Fit";
    public static final String MODERATE_FIT = "Moderate Fit";

    public static final String DRESS_IMAGE = "dressImage";
    public static final String ORDER_ID = "order_id";
    public static final String BRAND = "brand";
    public static final String ITEM_SHORT_DESCRIPTION = "shortDescription";
    public static final String WAREHOUSE_ID = "warehouse_id";
    public static final String SIZE = "size";
    public static final String RENTAL_PRICE = "rental";
    public static final String SECURITY_DEPOSIT = "security";
    public static final String DELIVERY_DATE = "deliveryDate";
    public static final String FITTING_TEXT = "fittingText";

    public static final String FILTERED_CATEGORY_LIST = "filteredCategoryList";
    public static final String FILTERED_COLOR_LIST = "filteredColorList";
    public static final String FILTERED_PRICE_LIST = "filteredPriceList";
    public static final String FILTERED_BRAND_LIST = "filteredBrandList";
    public static final String FILTER_APPLIED = "fiter_applied";

    public static final int NEXT_STATUS_RESERVE = 9;
    public static final String SIGNATURE_SAVED = "signature_saved";

    //---- Dress categories ---
    public static final String ANARKALI = "Anarkali";
    public static final String JACKET = "Jacket";
    public static final String CROP_TOP = "Crop Top";
    public static final String GOWN = "Gown";
    public static final String BLOUSE = "Blouse";
    public static final String KURTA = "Kurta";
    public static final String MAXI_DRESS = "Maxi dress";
    public static final String TOP = "Top";
    public static final String CHUDIDAAR = "Chudidaar";
    public static final String LEGGINGS = "Leggings";
    public static final String PANT = "Pant";
    public static final String SKIRT = "Skirt";
    public static final String LAHENGA = "Lahenga";
    public static final String SAREES = "Sarees";
    public static final String SAREE_GOWN = "Saree gown";
    public static final String DHOTI = "Dhoti";
    public static final String PLAZO = "Plazo";
    public static final String BOTTOM = "Bottom";

    //--- Tolerance Range ---
    /* Medium Length - Gown
       Minor Length - Chuddidar, Leggings, Pant, Plazo, Saree Gown, Skirt
       Major Length - Kurta, Dhoti, Maxi Dress, Anarkali
     */

    public static final int MEDIUM_LENGTH_MIN_TOLERANCE = 8;
    public static final int MEDIUM_LENGTH_MAX_TOLERANCE = 2;
    public static final int MAJOR_LENGTH_MIN_TOLERANCE = 15;
    public static final int MAJOR_LENGTH_MAX_TOLERANCE = 2;
    public static final int MINOR_LENGTH_MIN_TOLERANCE = 5;
    public static final int MINOR_LENGTH_MAX_TOLERANCE = 4;
    public static final double BUST_MIN_TOLERANCE = 2.5;
    public static final double BUST_MAX_TOLERANCE = 1.5;
    public static final double UNDER_BUST_MIN_TOLERANCE = 1.5;
    public static final double UNDER_BUST_MAX_TOLERANCE = 1.5;
    public static final double UPPER_WAIST_MIN_TOLERANCE = 3.5;
    public static final double UPPER_WAIST_MAX_TOLERANCE = 2.5;
    public static final double LOWER_WAIST_MIN_TOLERANCE = 2.5;
    public static final double LOWER_WAIST_MAX_TOLERANCE = 2.5;
    public static final double ARMHOLE_TOLERANCE = 3.5;
    public static final double ARMS_TOLERANCE = 3.5;
    public static final double CUFF_TOLERANCE = 2;
    public static final int HIP_TOLERANCE = 1;

    // --- Options Activity ---
    public static final String NUMBER_OF_DAYS = "Days";
    public static final String FOUR_DAYS= "4 days";
    public static final String SIX_DAYS = "6 days";
    public static final String EIGHT_DAYS = "8 days";

    public static final String ITEM_BRAND = "itemBrand";
    public static final String ITEM_RETAIL = "itemRetail";
    public static final String ITEM_SHORT_NAME = "itemShortName";
    public static final String ITEM_RENTAL = "itemRental";
    public static final String ITEM_FEATURES = "itemFeatures";
    public static final String WHATS_INCLUDED = "whats_included";
    public static final String FRONT_IMAGE = "frontImage";
    public static final String BACK_IMAGE = "backImage";
    public static final String SWIPE_IMAGE = "swipeImage";
    public static final String REDIRECT_URL = "redirectUrl";

    public static final String PARENT_DRESS_ID = "parent_dress_id";

    public static final String ARG_KEY_IMAGE_URLS = "arg_key_image_urls";
    public static final String DRESS_IMAGE_LIST = "imageList";

    public static final String LINK_SENT_SUCCESS = "Link Sent Successfully";
    public static final String LINK_SENT_FAILURE= "SMS faild, please try again";
    public static final String SMS= "Hi, The outfit you selected is not alterable to your size. Please try ";
    public static final String BOOK_NOW = ". We think it would fit you well. Book now!";

    public static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // --- Filter on the Basis of---
    public static final String FILTER_CATEGORY = "Category";
    public static final String FILTER_COLOR = "Color";
    public static final String FILTER_BRAND = "Brand";


    // Men Sizes

    public static final String DRESS_SIZE_FS = "FS";

    public static final String TWENTYSIX = "26";
    public static final String TWENTYSEVEN = "27";
    public static final String TWENTYEIGHT = "28";
    public static final String TWENTYNINE = "29";
    public static final String THIRTY = "30";
    public static final String THIRTYONE = "31";
    public static final String THIRTYTWO = "32";
    public static final String THIRTYTHREE = "33";
    public static final String THIRTYFOUR = "34";
    public static final String THIRTYFIVE = "35";
    public static final String THIRTYSIX = "36";
    public static final String THIRTYSEVEN = "37";
    public static final String THIRTYEIGHT = "38";
    public static final String THIRTYNINE = "39";
    public static final String FOURTY = "40";
    public static final String FOURTYONE = "41";
    public static final String FOURTYTWO = "42";
    public static final String FOURTYTHREE = "43";
    public static final String FOURTYFOUR = "44";
    public static final String FOURTYFIVE = "45";
    public static final String FOURTYSIX = "46";
    public static final String FOURTYSEVEN = "47";
    public static final String FOURTYEIGHT = "48";
    public static final String FOURTYNINE = "49";
    public static final String FIFTY = "50";


    //price id for Women

    public static final int PRICE_0_2000 = 1;
    public static final int PRICE_2000_3500 = 2;
    public static final int PRICE_3500_5000 = 3;
    public static final int PRICE_5000 = 4;


    // Price id for Men

    public static final int PRICE_0_1000 = 1;
    public static final int PRICE_1000_2500 = 2;
    public static final int PRICE_2500_4000 = 3;
    public static final int PRICE_4000 = 4;

    //Latitude and Longitude
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
}
