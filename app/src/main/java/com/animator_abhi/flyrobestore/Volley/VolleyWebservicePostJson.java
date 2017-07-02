package com.animator_abhi.flyrobestore.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.animator_abhi.flyrobestore.utils.Constants;
import com.animator_abhi.flyrobestore.utils.PrefUtils;

import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by ANIMATOR ABHI on 24/06/2017.
 */

public class VolleyWebservicePostJson   {

    AppController volleySingleton;
    private static final String TAG = "HttpClient";
    Context mContext;
    String accessToken="dashboard";

    /*
     * Listener to catch and parse response
     */
    VolleyWebserviceResponseListener mListener;
  //  WebserviceResponseListener mListener;
    Response.Listener vListener;
    Response.ErrorListener vErrorListener;
    /*
     * URl of Web service
     */
    ProgressDialog mProgressDialog;
    String url;
    JSONObject mJsonObject;
    // String response;
    boolean isProgressDialog;
    String via;
    protected ProgressBar mProgress;
    protected ViewGroup mContentContainer;
    protected ViewGroup mFrameHeader;
    protected int url_id;


    protected String headerKey;
    protected String headerValue;



    protected ViewGroup mContentCoordinatorLayout;
  //  HostNameVerifierHelper mHostNameVerifier;


    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager()
            {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
    };


    public VolleyWebservicePostJson(Context mContext,
                                    VolleyWebserviceResponseListener mListenerRoot, String url,
                                    JSONObject mJsonObject, ProgressBar mProgress, String via, ViewGroup mContentContainer, ViewGroup mFrameHeader ) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
        this.mListener = mListenerRoot;
        this.url = url;
        //this.isProgressDialog = isProgressDialog;
        this.via = via;
        this.mProgress = mProgress;

        this.mContentContainer = mContentContainer;
        this.mFrameHeader = mFrameHeader;




        // Log.d("WebServices", "url is" + url);



    }

    public VolleyWebservicePostJson(Context mContext,
                                    VolleyWebserviceResponseListener mListenerRoot, String url,
                                    JSONObject mJsonObject, ProgressBar mProgress, String via,
                                    ViewGroup mContentContainer, ViewGroup mFrameHeader, int url_id ) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
        this.mListener = mListenerRoot;
        this.url = url;
        //this.isProgressDialog = isProgressDialog;
        this.via = via;
        this.mProgress = mProgress;

        this.mContentContainer = mContentContainer;
        this.mFrameHeader = mFrameHeader;
        this.url_id = url_id;
        //Log.d("WebServices", "url is" + url);

    }





    public VolleyWebservicePostJson(Context mContext,
                                    VolleyWebserviceResponseListener mListenerRoot,
                                    String url,
                                    JSONObject mJsonObject,
                                    ProgressBar mProgress,
                                    String via,
                                    ViewGroup mContentContainer, ViewGroup mFrameHeader, String headerKey, String headerValue, int url_id ) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
        this.mListener = mListenerRoot;
        this.url = url;
        //this.isProgressDialog = isProgressDialog;
        this.via = via;
        this.mProgress = mProgress;

        this.mContentContainer = mContentContainer;
        this.mFrameHeader = mFrameHeader;
        this.headerKey= headerKey;
        this.headerValue = headerValue;
        this.url_id = url_id;


        // Log.d("WebServices", "url is" + url);


    }


    public VolleyWebservicePostJson(Context mContext,
                                    VolleyWebserviceResponseListener mListenerRoot, String url,
                                    JSONObject mJsonObject, ProgressBar mProgress, String via,
                                    ViewGroup mContentContainer, ViewGroup mFrameHeader, int url_id , ViewGroup snackBarLayout) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
        this.mListener = mListenerRoot;
        this.url = url;
        //this.isProgressDialog = isProgressDialog;
        this.via = via;
        this.mProgress = mProgress;

        this.mContentContainer = mContentContainer;
        this.mFrameHeader = mFrameHeader;
        this.url_id = url_id;
        //Log.d("WebServices", "url is" + url);
        this.mContentCoordinatorLayout = snackBarLayout;


    }

    public VolleyWebservicePostJson(Context mContext,
                                    VolleyWebserviceResponseListener mListenerRoot,String url,
                                    JSONObject mJsonObject, String via,  int url_id) {

        this.mContext = mContext;
        this.mJsonObject = mJsonObject;
        this.mListener = mListenerRoot;
        this.url = url;
        //this.isProgressDialog = isProgressDialog;
        this.via = via;

        this.url_id = url_id;
        //Log.d("WebServices", "url is" + url);

    }

    public   void execute()
    {
       volleySingleton= AppController.getInstance();

        //pre execute

        Log.d("#CALLED","1");
    /*    if(mProgress!=null)
        {
            mFrameHeader.setVisibility(View.GONE);
            mContentContainer.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
        }*/

      //  mHostNameVerifier = new HostNameVerifierHelper();

        //do in background
        long startTime = System.currentTimeMillis();
        Log.d("###URL###",url);
        String response;

        if (via.equalsIgnoreCase("post")) {

           /* final StringRequest strReq = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(mProgress!=null)
                            {
                                mProgress.setVisibility(View.GONE);
                                mFrameHeader.setVisibility(View.VISIBLE);
                                mContentContainer.setVisibility(View.VISIBLE);

                            }
                            // Display the first 500 characters of the response string.
                            mListener.responseWithId(response,via,url_id);
                            mListener.response(response, via);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mListener.onError();
                }
            })*//*{
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put();

                    return params;
                }

            }*/
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, mJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                   /* if(mProgress!=null)
                    {
                        mProgress.setVisibility(View.GONE);
                        mFrameHeader.setVisibility(View.VISIBLE);
                        mContentContainer.setVisibility(View.VISIBLE);

                    }*/
                    //TODO: handle success
                    mListener.responseWithId(response.toString(),via,url_id);
                    mListener.response(response.toString(), via);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer "+accessToken);
                    return headers;
                }
            };
            volleySingleton.addToRequestQueue(jsonRequest);

            //response = SendHttpPost(url, mJsonObject);

          // response = HttpPostSecureConnection(url, mJsonObject==null?"":mJsonObject.toString());

        } else if (via.equalsIgnoreCase("")||via.equalsIgnoreCase("get")){


            StringRequest strReq = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                          /*  if(mProgress!=null)
                            {

                                mProgress.setVisibility(View.GONE);
                                mFrameHeader.setVisibility(View.VISIBLE);
                                mContentContainer.setVisibility(View.VISIBLE);

                            }*/
                            // Display the first 500 characters of the response string.
                                mListener.responseWithId(response,via,url_id);
                            mListener.response(response, via);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mListener.onError();


                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer "+ accessToken);
                    return headers;
                }
            };
           // response = HttpGetRequestSecure(url);
          /*  if(url.contains("https"))
            {
                response = HttpGetRequestSecure(url);
            }
            else
            {
                response = SendHttpGettstring(url);
            }
*/
            volleySingleton.addToRequestQueue(strReq);
        }
        else if (via.equalsIgnoreCase("patch")){
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PATCH, url, mJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                /*    if(mProgress!=null)
                    {
                        mProgress.setVisibility(View.GONE);
                        mFrameHeader.setVisibility(View.VISIBLE);
                        mContentContainer.setVisibility(View.VISIBLE);

                    }*/
                    //TODO: handle success
                    mListener.responseWithId(response.toString(),via,url_id);
                    mListener.response(response.toString(), via);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer "+ accessToken);
                    return headers;
                }
            };
            volleySingleton.addToRequestQueue(jsonRequest);
           // response = HttpPatchRequest(url,mJsonObject==null?"":mJsonObject.toString());
        }
        else if(via.equalsIgnoreCase("delete"))
        {
            //response = DeleteHttpRequest(url,mJsonObject==null?"":mJsonObject.toString());
        }
        else
        {

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, mJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                   /* if(mProgress!=null)
                    {
                        mProgress.setVisibility(View.GONE);
                        mFrameHeader.setVisibility(View.VISIBLE);
                        mContentContainer.setVisibility(View.VISIBLE);

                    }*/
                    //TODO: handle success
                    mListener.responseWithId(response.toString(),via,url_id);
                    mListener.response(response.toString(), via);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer "+ accessToken);
                    return headers;
                }
            };
            volleySingleton.addToRequestQueue(jsonRequest);

          //  response = UpdateHttpRequest(url,mJsonObject==null?"":mJsonObject.toString());
        }

        long endtime = System.currentTimeMillis();

        long timediff = endtime-startTime;


        // Log.d("TIMEDIFF_DOINBG",timediff+"#URL#"+url);
       // return response;



    }


    void requestString()
    {
        StringRequest strReq = new StringRequest(url, vListener , vErrorListener);
    }

}
