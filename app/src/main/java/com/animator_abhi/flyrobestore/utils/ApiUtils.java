package com.animator_abhi.flyrobestore.utils;

/**
 * Created by monisha on 20/04/16.
 */
public class ApiUtils {


     private static final String BASE_URL = "http://staging.flyrobeapp.com/api/web/v1/";
    // private static final String BASE_URL = "http://ops.flyrobeapp.com/api/web/v1/";  //production

   //  private static final String BASE_URL_HOME = "https://api-master.flyrobeapp.com/api/android/v1/";
    private static final String BASE_URL_HOME = "https://staging.flyrobeapp.com/api/android/v1/";

     private static final String BASE_URL_NODE = "https://node.flyrobeapp.com:8000/";
   // private static final String BASE_URL_NODE = "https://node.flyrobeapp.com:8002/"; //production

    //API FOR FE USERNAME VERIFICATION
    public static final int USERNAME_VERIFY_ID = 101;
    public static final String USERNAME_VERIFY_URL = BASE_URL + "biker-details/?user_name=";

    //API FOR ORDER LIST
    public static final int ORDER_LIST_ID = 102;
    public static final String ORDER_LIST_URL = BASE_URL + "fit-expert-proforma-list/?biker_id=";

    //API FOR CUSTOMER LIST MALE
    public static final int CUSTOMER_LIST_MALE_ID = 103;
    public static final String CUSTOMER_LIST_MALE_URL = BASE_URL + "male-measurement?contact_number=";

    //API FOR CUSTOMER LIST FEMALE
    public static final int CUSTOMER_LIST_FEMALE_ID = 104;
    public static final String CUSTOMER_LIST_FEMALE_URL = BASE_URL + "female-measurement?contact_number=";


    //API FOR UPDATE FEMALE MEASUREMENTS
    public static final int UPDT_MSR_FEMALE_ID = 105;
    public static final String UPDT_MSR_FEMALE_URL = BASE_URL + "female-measurement-update/";

    //API FOR UPDATE MALE MEASUREMENTS
    public static final int UPDT_MSR_MALE_ID = 106;
    public static final String UPDT_MSR_MALE_URL = BASE_URL + "male-measurement-update/";

    //API FOR CREATE FEMALE MEASUREMENTS
    public static final int CREATE_MSR_FEMALE_ID = 107;
    public static final String CREATE_MSR_FEMALE_URL = BASE_URL + "female-measurement/";

    //API FOR CREATE MALE MEASUREMENTS
    public static final int CREATE_MSR_MALE_ID = 108;
    public static final String CREATE_MSR_MALE_URL = BASE_URL + "male-measurement/";

    //API TO GET ORDER DETAILS FOR FEMALE CUSTOMER
    public static final int ORDER_DETAILS_FML_ID = 109;
    public static final String ORDER_DETAILS_FML_URL = BASE_URL_NODE + "fml-customer-order-items-measurement-info";

    //API TO GET ORDER DETAILS FOR MALE CUSTOMER
    public static final int ORDER_DETAILS_ML_ID = 110;
    public static final String ORDER_DETAILS_ML_URL = BASE_URL_NODE + "ml-customer-order-items-measurement-info";

    //API TO GET OTP SMS & SEND SMS TO CUSTOMER
    public static final int SMS_SEND_ID = 111;
    public static final String SMS_SEND_URL = "http://node.flyrobeapp.com/send_sms";

    //API TO OPTION LIST
    public static final int OPTION_LIST_ID = 112;
    public static final String OPTION_LIST_URL = BASE_URL_NODE + "items-list-according-customer-measurement";

    //API TO GET OTP SMS & SEND SMS TO CUSTOMER
    public static final int UPDATE_COMMENT_ID = 113;
    public static final String UPDATE_COMMENT_URL = BASE_URL + "orders/";

    //API FOR ORDER STATUS CHANGE
    public static final int STATUS_CHANGE_ID = 114;
    public static final String STATUS_CHANGE_URL = BASE_URL + "order-status-change/";

    //API FOR DRESS DETAIL
    public static final int DRESS_DETAIL_ID = 115;
    public static final String DRESS_DETAIL_URL = BASE_URL_HOME + "parent-item/";

    //API FOR FILTERS
    public static final int FILTERS_ID = 116;
    public static final String FILTERS_URL = BASE_URL_HOME + "inventory-item/filter-population/";

    //API FOR GET RETURN REASON
    public static final int MSR_FAILED_REASON_ID = 117;
    public static final String MSR_FAILED_REASON_URL = BASE_URL + "order-escalation-reason?escalation_type=";

    public static final int SEND_DB_LAT_LONG_ID = 118;
    public static final String SEND_DB_LAT_LONG_URL = "http://node.flyrobeapp.com:8003";


    public static final int APP_UPDATE_ID = 119;
    public static final String APP_UPDATE_URL = "http://assets.flyrobeapp.com/logistics_app/";


}
