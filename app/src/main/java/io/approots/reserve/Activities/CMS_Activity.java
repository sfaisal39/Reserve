package io.approots.reserve.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class CMS_Activity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    SemiBoldTextView semiBoldTextView;
    WebView myWebView;
    String myUrl;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_cms);
        progress = findViewById(R.id.progress);
        semiBoldTextView = (SemiBoldTextView) findViewById(R.id.text_title);
        myWebView = (WebView) findViewById(R.id.webView_link);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().getLoadsImagesAutomatically();
        myWebView.getProgress();
        myWebView.getSettings().getUseWideViewPort();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetResponse();
    }

    //Response Calling
    private void GetResponse() {
        progress.setVisibility(View.VISIBLE);
        String lang = sharedpreferences.getString("LOCALES", "en");
        final String URL = Constants.BaseURL + "GET_CMS?sop=" + Constants.SOP + "&lang=" + lang + "&TB_TYPE=" + "help";

//        Toast.makeText(getActivity(), sharedpreferences.getString("ParentID", ""), Toast.LENGTH_LONG).show();
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Called", String.valueOf(response));
//                        Toast.makeText(getActivity(),String.valueOf(response),Toast.LENGTH_LONG).show();
                        try {
                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {
                                progress.setVisibility(View.GONE);


                                JSONArray arst = ar.getJSONArray("data");
                                JSONObject obj = arst.getJSONObject(0);
                                semiBoldTextView.setText(obj.getString("TB_TITLE"));
                                myWebView.loadData(obj.getString("TB_NAME"), "text/html", null);


//                                obj.getString("TB_DETAIL")


                            } else {
//                                progress.setVisibility(View.GONE);
//
                                String mskg = ar.getString("msg");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());

                NetworkResponse networkResponse = error.networkResponse;
//                getoffline(sharedpreferencesPrefs.getString(type, ""));

                if (error instanceof NetworkError) {

                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
//                    getoffline(sharedpreferencesPrefs.getString(type, ""));
//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
//                    progress.setVisibility(View.GONE);
                }
            }
        });
        MySingleton.getInstance(CMS_Activity.this).addToRequestQueue(request_json);
    }

    public void back_bnt(View view) {
        onBackPressed();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            return super.onRenderProcessGone(view, detail);


        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            progress.setVisibility(View.GONE);
        }
    }
}
