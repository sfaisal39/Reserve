package io.approots.reserve.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Activities.History.Hostory_main;
import io.approots.reserve.Adapters.DayAdapter;
import io.approots.reserve.Adapters.TimeSlopAdapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.Car_Gerages;
import io.approots.reserve.Models.Day_List;
import io.approots.reserve.Models.TimeSlop_List;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class TimeSlot extends AppCompatActivity {

    String Type;
    String OtherID;
    RegularTextView type_text, type_text2, type_text3;

    SemiBoldTextView date_current;
    Dialog dialog, dialog_grages;
    private TimeSlopAdapter adapter;
    private List<TimeSlop_List> Card_data;
    private RecyclerView slot_Recycler;
    RelativeLayout button_review;

    private DayAdapter dayadapter;
    private List<Day_List> Day_data;
    private RecyclerView Day_Recycler;

    private List<Car_Gerages> car_gerages;
    private List<String> car_gerages_str;

    EditText DateTime_ed, name_ed, mobile_ed, plate_no_ed, choose_branch_ed, car_doc_ed;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    JSONArray Response;
    RegularTextView text_des;
    private ProgressBar progresss;

    private ShimmerFrameLayout mShimmerViewContainer;
    RelativeLayout icon_history, iocn_chat, icon_notification;

    ArrayAdapter adapterarray;

    String CarPROID, BRANCHTYPE, SLOTID, DELIVERY_TIME, DELIVERY_DATE;

    RegularTextView history, chat, notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_time_slot);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        history = (RegularTextView) findViewById(R.id.textmenu);
        chat = (RegularTextView) findViewById(R.id.textchat);
        notification = (RegularTextView) findViewById(R.id.textnotification);

        car_gerages = new ArrayList<>();
        car_gerages_str = new ArrayList<>();
        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);
        DateTime_ed = (EditText) findViewById(R.id.date_time);
        name_ed = (EditText) findViewById(R.id.name);
        mobile_ed = (EditText) findViewById(R.id.mobile);
        plate_no_ed = (EditText) findViewById(R.id.plate_no);
        choose_branch_ed = (EditText) findViewById(R.id.choose_branch);
        car_doc_ed = (EditText) findViewById(R.id.car_doc);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        if (sharedpreferences.contains("SLOTID")) {
            SharedPreferences.Editor edit = sharedpreferences.edit();
            edit.remove("SLOTID");
            edit.remove("TBID");
            edit.apply();
        }
        Checkstatus();
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkstatus();
    }

    private void Checkstatus() {

        if (sharedpreferences.contains("USER_ID")) {
            icon_history.setVisibility(View.VISIBLE);
            iocn_chat.setVisibility(View.VISIBLE);
            icon_notification.setVisibility(View.VISIBLE);

            if (sharedpreferences.contains("NOTIFICATION")) {
//                Log.d("USERID", String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
                notification.setText(String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
            } else {
                notification.setText(String.valueOf(0));
            }
            if (sharedpreferences.contains("ORDER")) {
                history.setText(String.valueOf(sharedpreferences.getInt("ORDER", 0)));
            } else {
                history.setText(String.valueOf(0));
            }

        } else {
            icon_history.setVisibility(View.GONE);
            iocn_chat.setVisibility(View.GONE);
            icon_notification.setVisibility(View.GONE);
        }
    }

    private void init() {

        dialog = new Dialog(this, R.style.NewDialog_Res);
        dialog.setContentView(R.layout.choose_caleder_time);
        dialog.setCancelable(true);

        dialog_grages = new Dialog(this, R.style.NewDialog);
        dialog_grages.setContentView(R.layout.choose_grages);
        dialog_grages.setCancelable(true);

        ListView listview = dialog_grages.findViewById(R.id.listview);
        adapterarray = new ArrayAdapter<String>(this, R.layout.listview, car_gerages_str);
        listview.setAdapter(adapterarray);

        date_current = (SemiBoldTextView) dialog.findViewById(R.id.date_current);
        type_text = (RegularTextView) findViewById(R.id.type_text);
        type_text2 = (RegularTextView) findViewById(R.id.type_text2);
        type_text3 = (RegularTextView) findViewById(R.id.type_text3);


        Card_data = new ArrayList<>();
        Day_data = new ArrayList<>();
        button_review = (RelativeLayout) findViewById(R.id.button_review);
        slot_Recycler = (RecyclerView) dialog.findViewById(R.id.slot_Recycler);
        Day_Recycler = (RecyclerView) dialog.findViewById(R.id.day_Recycler);
//        text_des = (RegularTextView) findViewById(R.id.text_des);
        progresss = (ProgressBar) findViewById(R.id.progresss);

        slot_Recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        slot_Recycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(10), false));
        slot_Recycler.setItemAnimator(new DefaultItemAnimator());
        Dialog view = dialog;
        adapter = new TimeSlopAdapter(this, Card_data, view);
        slot_Recycler.setAdapter(adapter);

        name_ed.setText(sharedpreferences.getString("USER_NAME", ""));
        mobile_ed.setText(sharedpreferences.getString("USER_MOBILE", ""));

        button_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SLOTID.equals("")) {

                    if (sharedpreferences.contains("USER_ID") && !OtherID.equals("")) {

                        GET_SLOT_STATUS();
                    } else {

//                        Log.d("USER", sharedpreferences.getString("USER_ID", ""));
                        startActivity(new Intent(TimeSlot.this, SignIn.class).putExtra("From", 1));
                    }


                } else {
                    Toast.makeText(TimeSlot.this, "Choose Date & Time", Toast.LENGTH_SHORT).show();
                }
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(TimeSlot.this, 5);
        Day_Recycler.setLayoutManager(gridLayoutManager);
//        Day_Recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Day_Recycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(5), false));
        Day_Recycler.setItemAnimator(new DefaultItemAnimator());
        dayadapter = new DayAdapter(this, Day_data);
        Day_Recycler.setAdapter(dayadapter);


        dayadapter.setOnDayClickListener(new DayAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(int position) {
                slot_Recycler.setAdapter(null);
                slot_Recycler.removeAllViewsInLayout();
                slot_Recycler.swapAdapter(adapter, false);
                Card_data.clear();

                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();
                Log.i("POJ", String.valueOf(position));
//                Toast.makeText(TimeSlot.this,position,Toast.LENGTH_SHORT).show();
                getSlot(position);


            }
        });

        adapter.setOnAreaClickListener(new TimeSlopAdapter.OnSlotClickListener() {
            @Override
            public void onSlotClick(int position) {


                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();


                if (!Card_data.get(position).getSLOT_AVAIABLE().equals("No")) {

                    SLOTID = Card_data.get(position).getSLOT_ID();
                    Log.d("SLOTID", Card_data.get(position).getSLOT_ID());

                    DELIVERY_TIME = Card_data.get(position).getTB_TIME();
                    DELIVERY_DATE = Card_data.get(position).getFULL_DATE();
                    DateTime_ed.setText(Card_data.get(position).getFULL_DATE() + " " + Card_data.get(position).getTB_TIME());
//                DateTime_ed.setText(sharedpreferences.getString("DELIVERY_DATE", "") + " " + sharedpreferences.getString("DELIVERY_TIME", ""));

                    dialog.dismiss();
                }


//                dayadapter.notifyDataSetChanged();
//                dayadapter.notifyDataSetChanged();


            }
        });


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("NAME1")) {
                OtherID = args.getString("TB_ID");
                CarPROID = OtherID;
                Type = args.getString("Tb_Type");

                if (!Type.equals("D")) {
                    plate_no_ed.setVisibility(View.VISIBLE);
                    choose_branch_ed.setVisibility(View.VISIBLE);
                    car_doc_ed.setVisibility(View.VISIBLE);
//                    CarPROID = OtherID;
//                    OtherID = "";
                } else {
                    getResponse(args.getString("TB_ID"));
                    DateTime_ed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DateTimeCall();
                        }
                    });
                }


                if (args.containsKey("CAR_BRANCHES")) {
                    BRANCHTYPE = args.getString("CAR_BRANCHES");
                    getGrages(args.getString("TB_ID"));
                }


//                Log.d("OTHER_ID:", OtherID);
                type_text.setText(args.getString("NAME1"));
                type_text2.setText(args.getString("NAME2"));
                type_text3.setText(args.getString("NAME3") + " X");

            }
        }

        choose_branch_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallBranches();
            }
        });

        DateTime_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();
                DateTimeCall();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                choose_branch_ed.setFocusable(false);
                DateTime_ed.setFocusable(false);
                mobile_ed.setFocusable(false);
                name_ed.setFocusable(false);
                OtherID = car_gerages.get(position).getTB_ID();
                choose_branch_ed.setText(car_gerages.get(position).getTB_NAME());
                Log.v("LIST", car_gerages.get(position).getTB_ID());
                dialog_grages.dismiss();


                getResponse(car_gerages.get(position).getTB_ID());
                if (!car_gerages.get(position).getTB_ID().equals("")) {
                    DateTime_ed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DateTimeCall();
                        }
                    });
                }

            }
        });
    }

    private void CallBranches() {
        dialog_grages.show();
        adapterarray.notifyDataSetChanged();
    }

    private void getGrages(final String ProID) {

        String lang = sharedpreferences.getString("LOCALES", "en");

        final String URL = Constants.BaseURL + "GET_CAR_BRANCHES?sop=" + Constants.SOP + "&lang=" + lang + "&TB_PRODID=" + ProID + "&TB_TYPE=" + BRANCHTYPE;
        Log.i("CHDS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDS", String.valueOf(response));
//
                        try {
                            progresss.setVisibility(View.GONE);
                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {

                                JSONArray arst = ar.getJSONArray("data");


                                for (int i = 0; i < arst.length(); i++) {
                                    JSONObject obj = arst.getJSONObject(i);
                                    car_gerages_str.add(obj.getString("TB_NAME"));
                                    Car_Gerages datas = new Car_Gerages(obj.getString("TB_ID"), obj.getString("TB_NAME"));
                                    car_gerages.add(datas);
                                    adapterarray.notifyDataSetChanged();
                                }

                            } else {

//
                                String mskg = ar.getString("msg");
                                Toast.makeText(TimeSlot.this, String.valueOf(mskg), Toast.LENGTH_LONG).show();

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


                if (error instanceof NetworkError) {

                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                    getGrages(ProID);
                }
            }
        });
        MySingleton.getInstance(TimeSlot.this).addToRequestQueue(request_json);

    }

    private void DateTimeCall() {

//        Toast.makeText(TimeSlot.this, "CSl", Toast.LENGTH_SHORT);
        DateTime_ed.setEnabled(true);
        adapter.notifyDataSetChanged();
        dayadapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        dayadapter.notifyDataSetChanged();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialog.show();
    }

    public void backss(View view) {
        onBackPressed();
    }

    private void getSlot(final int pos) {

//        adapter.notifyDataSetChanged();
//
//        titletxt.setText(name);
//        Response
        slot_Recycler.setAdapter(null);
        slot_Recycler.removeAllViewsInLayout();
        slot_Recycler.swapAdapter(adapter, false);
        Card_data.clear();
        adapter.notifyDataSetChanged();

        adapter.notifyDataSetChanged();
        dayadapter.notifyDataSetChanged();


        try {

            JSONObject obj = Response.getJSONObject(pos);
            Log.i("POJ", String.valueOf(obj));

            JSONArray data_child = obj.getJSONArray("data_child");


            for (int i = 0; i < data_child.length(); i++) {
                JSONObject child = data_child.getJSONObject(i);
                TimeSlop_List datas = new TimeSlop_List(child.getString("TB_TIME"), child.getString("ORDER_LIMIT"), child.getString("ORDER_DONE"), child.getString("ORDER_REMAIN"), child.getString("SLOT_AVAIABLE"), child.getString("SAVE_GREEN"), child.getString("FULL_DATE"), child.getString("TB_ID"), child.getString("SLOT_ID"), child.getString("SLOT_AVAIABLE_LABEL"));
                Card_data.add(datas);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getResponse(final String OtherIDs) {


//        mShimmerViewContainer.setVisibility(View.VISIBLE);
//        mShimmerViewContainer.startShimmer();
        progresss.setVisibility(View.VISIBLE);
        DateTime_ed.setEnabled(true);
//        DateTime_ed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DateTimeCall();
//            }
//        });
        String lang = sharedpreferences.getString("LOCALES", "en");
//        final String URL = Constants.BaseURL + "GET_DELIVERY_SLOT?sop=" + Constants.SOP + "&lang=" + lang + "&TB_CITYID=" + cityid + "&TB_ORDERID=" + order_id;
        final String URL = Constants.BaseURL + "GET_CAR_CALENDAR?sop=" + Constants.SOP + "&lang=" + lang + "&TB_OTHERID=" + OtherIDs + "&TB_TYPE=" + Type;
//        Toast.makeText(getActivity(), sharedpreferences.getString("ParentID", ""), Toast.LENGTH_LONG).show();
        Log.i("CHDS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDS", String.valueOf(response));
//                        Toast.makeText(getActivity(),String.valueOf(response),Toast.LENGTH_LONG).show();
                        try {
                            progresss.setVisibility(View.GONE);
                            DateTime_ed.setEnabled(true);
                            JSONObject ar = response.getJSONObject("result");
//                            text_des.setText(ar.getString("shortNote") + ar.getString("slotExpiry_mnt"));
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {

                                date_current.setText(ar.getString("month_header"));
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);

                                JSONArray arst = ar.getJSONArray("data");
//                                JSONObject obj = arst.getJSONObject(pos);
//                                JSONArray data_child = arst.getJSONArray("data_child");

                                Response = arst;
                                Log.i("POJ", String.valueOf(arst));

                                if (sharedpreferences.contains("TBID")) {
//                                    Log.i("ONJ", String.valueOf(sharedpreferences.getString("TBID", "")));
//                                    Toast.makeText(TimeSlot.this, String.valueOf(sharedpreferences.getString("TBID", "")), Toast.LENGTH_SHORT).show();
                                    getSlot(Integer.parseInt(sharedpreferences.getString("TBID", "")) - 1);
                                } else {
//                                    Toast.makeText(TimeSlot.this, String.valueOf(0), Toast.LENGTH_SHORT).show();
                                    getSlot(0);

                                }

                                for (int i = 0; i < arst.length(); i++) {
                                    JSONObject obj = arst.getJSONObject(i);
//                                    JSONArray data_child = obj.getJSONArray("data_child");
//                                  /  Log.i("ONJ", String.valueOf(String.valueOf(obj.getString("DAY"))));
//                                    Toast.makeText(TimeSlot.this, String.valueOf(obj), Toast.LENGTH_SHORT).show();
                                    Day_List datas = new Day_List(obj.getString("DATE"), obj.getString("DAY"), obj.getString("FULL_DATE"), obj.getString("SLOT_AVAIABLE"), obj.getString("TB_ID"));
                                    Day_data.add(datas);
                                    dayadapter.notifyDataSetChanged();
                                }

                            } else {

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


                if (error instanceof NetworkError) {

                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                    getResponse(OtherIDs);
                }
            }
        });
        MySingleton.getInstance(TimeSlot.this).addToRequestQueue(request_json);

    }

    public void notification(View view) {
        Intent in = new Intent(this, Notification.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    public void history(View view) {
        Intent in = new Intent(this, Hostory_main.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


    }


    private int dpToPx(int i) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics()));
    }

    private void GET_SLOT_STATUS() {

        progresss.setVisibility(View.VISIBLE);
//        String DELIVERY_DATE = sharedpreferences.getString("DELIVERY_DATE", "");
//        String DELIVERY_TIME = sharedpreferences.getString("DELIVERY_TIME", "");
//        String SLOTID = sharedpreferences.getString("DELIVERY_SLOTID", "");


//        String DELIVERY_DATE = sharedpreferences.getString("DELIVERY_DATE", "");
        String DELIVERY_TIME = sharedpreferences.getString("DELIVERY_TIME", "");
//        String SLOTID = SLOTID;
        String USER_ID = sharedpreferences.getString("USER_ID", "");

        String lang = sharedpreferences.getString("LOCALES", "en");

        String URL = Constants.BaseURL + "INSERT_ORDER?sop=" + Constants.SOP + "&lang=" + lang + "&TB_ID=" + "" + "&TB_TYPE=" + Type + "&DELIVERY_DATE=" + DELIVERY_DATE + "&USERID=" + USER_ID + "&TB_OTHERID=" + OtherID + "&TB_DELIVERY_SLOTID=" + SLOTID + "&TB_NAME=" + name_ed.getText().toString().trim() + "&TB_PHONE=" + mobile_ed.getText().toString().trim() + "&TB_PLATE_NO=" + plate_no_ed.getText().toString().trim() + "&TB_DOC_ID=" + car_doc_ed.getText().toString().trim();
        URL = URL.replace(" ", "%20");
        Log.i("CHDSSS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDSSS", String.valueOf(response));
                        try {
                            progresss.setVisibility(View.GONE);
                            JSONObject ar = response.getJSONObject("result");
//                            text_des.setText(ar.getString("shortNote") + ar.getString("slotExpiry_mnt"));
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {

//                                Alertdialog("", msg);
                                if (sharedpreferences.contains("SLOTID")) {
                                    SharedPreferences.Editor edit = sharedpreferences.edit();
                                    edit.remove("SLOTID");
                                    edit.remove("TBID");
                                    edit.apply();
                                }

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("ORDER", Integer.parseInt(ar.getString("tot_order")));
                                editor.apply();


                                startActivity(new Intent(TimeSlot.this, Reservation_Done.class).putExtra("MSG", msg));
//                                JSONArray arst = ar.getJSONArray("data");

                            } else {

//
                                String mskg = ar.getString("msg");

                                Alertdialog("", mskg);
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


                if (error instanceof NetworkError) {

                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                    GET_SLOT_STATUS();
                }
            }
        });
        MySingleton.getInstance(TimeSlot.this).addToRequestQueue(request_json);

    }

    void Alertdialog(final String code, String message) {
        // custom dialog
        final Dialog dialog = new Dialog(this, R.style.NewDialog);
        dialog.setContentView(R.layout.connection);
        dialog.setCancelable(false);
        TextView mesage = dialog.findViewById(R.id.messagesa);
        TextView eddor = dialog.findViewById(R.id.errorcode);
        TextView okbutton = dialog.findViewById(R.id.okbutton);
        mesage.setText(message);
        eddor.setVisibility(View.GONE);
//        eddor.setText("Error code: " + code);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                SetCart_data();


            }
        });

        dialog.show();
    }


}
