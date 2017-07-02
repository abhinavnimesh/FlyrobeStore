package com.animator_abhi.flyrobestore.Volley;

import com.google.gson.JsonSyntaxException;

/**
 * Created by ANIMATOR ABHI on 24/06/2017.
 */

public interface VolleyWebserviceResponseListener {

    /**
     * method to catch response
     *
     * @param strresponse
     *            response get from any web-service
     */
    public void responseWithId(String strresponse, String via, int urlId) throws JsonSyntaxException,NullPointerException;
    /***
     * If Error or Exception occured
     */
    public void onError() throws NullPointerException;

    public void response(String response, String via) throws JsonSyntaxException,NullPointerException;

    public void slowInternetConnction() throws NullPointerException;
}
