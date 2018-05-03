package fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.BlankActivity;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import model.Category;
import model.City;
import model.Province;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SherlookHohlmes on 4/2/2018.
 */

public class ProfileFragment extends Fragment {

    private View view;
    private AppCompatButton btnCat, btnLocation, btnCity;
    private TextView txtCategory, txtProvince, txtCity;
    private Category category;
    private Province province;
    private City city;
    private ImageView imgUpload;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private RadioButton rbPresentation, rbSale;
    private RadioGroup radioGroup;
    private Button btnSubmit;
    private Bitmap bmpUpload = null;
    private String type = null;
    private String filePath = "";
    private RequestQueue queue;
    private LinearLayout linPrice, linCity;
    private String price;
    private String selectedItem, title, description, email, phone;
    private EditText txtPrice, edtTitle, edtDescription, edtEmail, edtPhone;
    private Spinner priceType;
    private MaterialDialog pdialog;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //checkPermissions();

        txtPrice = (EditText) view.findViewById(R.id.edt_price);
        txtCategory = (TextView) view.findViewById(R.id.txt_cat);
        txtProvince = (TextView) view.findViewById(R.id.txt_province);
        txtCity = (TextView) view.findViewById(R.id.txt_city);
        imgUpload = (ImageView) view.findViewById(R.id.img_upload);
        rbPresentation = (RadioButton) view.findViewById(R.id.radio_presentation);
        rbSale = (RadioButton) view.findViewById(R.id.radio_for_sale);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        radioGroup = (RadioGroup) view.findViewById(R.id.myRadioGroup);
        linPrice = (LinearLayout) view.findViewById(R.id.lin_price);
        linCity = (LinearLayout) view.findViewById(R.id.lin_city);
        edtTitle = (EditText) view.findViewById(R.id.edt_title);
        edtDescription = (EditText) view.findViewById(R.id.edt_description);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);

        priceType = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> priceTypeAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.price_array, R.layout.spiner_item);
        priceTypeAdapter.setDropDownViewResource(R.layout.spiner_item);
        priceType.setAdapter(priceTypeAdapter);
        priceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("قیمت مورد نظر")) {
                    linPrice.setVisibility(View.VISIBLE);
                    // do your stuff
                } else {

                    linPrice.setVisibility(View.GONE);
                    txtPrice.setText("");

                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCat = (AppCompatButton) view.findViewById(R.id.btn_cat);
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseCategoryFrgment getCat = new ChooseCategoryFrgment();
                getCat.show(getActivity().getSupportFragmentManager(), "getCategoryToFragment");

            }
        });

        btnLocation = (AppCompatButton) view.findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseProvinceFrgment getProvince = new ChooseProvinceFrgment();
                getProvince.show(getActivity().getSupportFragmentManager(), "getProvinceToActivity");

            }
        });
        btnCity = (AppCompatButton) view.findViewById(R.id.btn_city);
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CityDialog getCity = new CityDialog(province);
                getCity.show(getActivity().getSupportFragmentManager(), "getCityToFragment");

            }
        });

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

                StrictMode.VmPolicy.Builder mbuilder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(mbuilder.build());

                final String[] items = new String[]{"دوربین", "انتخاب از گالری"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            pickImageFromCamera();

                        } else {

                            Intent pictureActionIntent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(
                                    pictureActionIntent,
                                    PICK_FROM_FILE);

                        }

                        dialog.cancel();

                    }
                });

                final AlertDialog dialog = builder.create();

                dialog.show();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find which radioButton is checked by id
                if (selectedId == rbPresentation.getId()) {

                    type = "ارائه";

                } else if (selectedId == rbSale.getId()) {

                    type = "درخواستی";

                }
//                if (txtPrice.getText().toString().length() > 0)
//                    price = txtPrice.getText().toString() + " تومان";
//                else if (selectedItem.equals("قیمت مورد نظر") || selectedItem.equals("لطفا انتخاب کنید"))
//                    price = null;
//                else
//                    price = selectedItem;

                title = edtTitle.getText().toString();
                description = edtDescription.getText().toString();
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();

                if (checkConnection()) {

                    if (category != null &&
                            province != null &&
                            city != null &&
                            filePath != null &&
                            (title != null && title.length() > 0) &&
                            (description != null && description.length() > 0) &&
                            (email != null && email.length() > 0) &&
                            (phone != null && phone.length() > 0)) {

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
                        builder.content(R.string.please_wait)
                                .progress(true, 0)
                                .cancelable(false);
                        pdialog = builder.build();
                        pdialog.show();

                        queue = Volley.newRequestQueue(Appconfig.context);
                        String url = Appconfig.base_url + "/api/imageupload";
                        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.e("uploadresponse-->", response);
                                        JSONObject jObj = null;
                                        try {
                                            jObj = new JSONObject(response);
                                            String result = jObj.getString("status");
                                            if (result.equals("success")) {

                                                Log.e("condition-->", "OK");

                                                final String targetPath = jObj.getString("target_path");
                                                queue = Volley.newRequestQueue(Appconfig.context);
                                                StringRequest postRequest = new StringRequest(Request.Method.POST, Appconfig.base_url + "/api/insertadv",
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                // response
                                                                Log.e("response-->", response);
                                                                updateFragments();
                                                            }
                                                        },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                // error
                                                                Log.d("response-->", "error: " + error);
                                                                handleError(error);

                                                            }
                                                        }
                                                ) {
                                                    @Override
                                                    protected Map<String, String> getParams() {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("cat_id", category.getId());
                                                        params.put("description", description);
                                                        params.put("email", email);
                                                        params.put("image", Appconfig.base_url + targetPath);
                                                        params.put("location", province.getName());
                                                        params.put("city", city.getName());
                                                        params.put("phone", phone);
//                                                        params.put("price", price);
                                                        params.put("title", title);
//                                                        params.put("type", type);

                                                        return params;
                                                    }
                                                };
                                                queue.add(postRequest);


                                            } else {

                                                Toast.makeText(getActivity(), "خطا در بارگزاری عکس! لطفا مجدد تلاش کنید", Toast.LENGTH_SHORT).show();
                                                if (pdialog != null) {
                                                    pdialog.cancel();
                                                    pdialog.dismiss();
                                                    Log.e("dialog-->", "OK");
                                                }

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                handleError(error);
                            }
                        });

                        Log.e("filePath-->", filePath);
                        smr.addFile("image", filePath);
                        queue.add(smr);

                    }

                }
                else
                {

                    showErrorDialog("لطفا اتصال به اینترنت را بررسی نمایید!");

                }

            }
        });

        return view;
    }

    private void handleError(VolleyError error) {

        if (error instanceof TimeoutError) {
            showErrorDialog("اتمام زمان پاسخ! لطفا مجدد تلاش کنید");
        } else if (error instanceof ServerError) {
            showErrorDialog("خطای سرور! لطفا مجدد تلاش کنید");
        } else if (error instanceof AuthFailureError) {
            showErrorDialog("خطای احراز هویت! لطفا مجدد تلاش کنید");
        } else if (error instanceof NetworkError) {
            showErrorDialog("خطای شبکه! لطفا مجدد تلاش کنید");
        } else if (error instanceof NoConnectionError) {
            showErrorDialog("لطفا اتصال به اینترنت را بررسی نمایید!");
        } else if (error instanceof ParseError) {
            showErrorDialog("خطای پارسینگ! لطفا مجدد تلاش کنید");
        }
        else
            showErrorDialog("خطای شبکه! لطفا مجدد تلاش کنید");

    }

    private void showErrorDialog(String errorMessage) {

        if (pdialog != null) {
            pdialog.cancel();
            pdialog.dismiss();
            Log.e("dialog-->", "OK");
        }
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

    }

    private void updateFragments() {
        getadvs();
        backToFirst();
    }

    private void backToFirst() {


    }

    private void getadvs() {
        queue = Volley.newRequestQueue(getActivity());
        queue.getCache().clear();
        String url = Uri.parse(Appconfig.base_url + "api/getadvs").toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response>>>>>>", response);
                callbackSuccesResponse(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("response>>>>>>", "Error" + error);
            }
        });
        MyStringRequest.setShouldCache(false);
        queue.add(MyStringRequest);

    }

    private void callbackSuccesResponse(String mResponse)
    {

        try {
            Appconfig.allAdvertisings = new JSONArray(mResponse);
            Log.e("advertisings>>>>>>", Appconfig.allAdvertisings.length() + "");
            if (Appconfig.homeFragment != null) {
                Appconfig.fm.beginTransaction().remove(Appconfig.homeFragment).commit();
                Appconfig.homeFragment = new HomeFragment();
                Appconfig.fm.beginTransaction().add( R.id.frm_main_container, Appconfig.homeFragment, "homeFrag").commit();

            }
            if (Appconfig.searchFragment != null) {
                Appconfig.fm.beginTransaction().remove(Appconfig.searchFragment).commit();
                Appconfig.searchFragment = new SearchFragment();
                Appconfig.fm.beginTransaction().add( R.id.frm_main_container, Appconfig.searchFragment, "homeFrag").hide(Appconfig.searchFragment).commit();
            }

            category = null; description = null; email = null; province = null; city = null; phone = null; price = null; title = null; type = null;
            txtCategory.setText("انتخاب");
            txtProvince.setText("استان");
            txtCity.setText("شهر");
            linCity.setVisibility(View.GONE);
            imgUpload.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            radioGroup.clearCheck();
            priceType.setSelection(0);
            txtPrice.setText(""); txtPrice.setHint("10000 تومان");
            edtTitle.setText("");
            edtDescription.setText("");
            edtPhone.setText("");
            edtEmail.setText("");

            if (pdialog != null) {
                pdialog.cancel();
                pdialog.dismiss();

                IntroActivity.hideShowFragment(Appconfig.homeFragment);
                Toast.makeText(getActivity(), "آگهی شما با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                IntroActivity.bottomNavigationView.selectTabWithId(R.id.navigation_home);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);

        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

        }

    }

    private void pickImageFromCamera() {

        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_adv_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);

        }

    }

    public void onUserSelectCategory(Category category) {

        txtCategory.setText(category.getName());
        this.category = category;

    }

    public void onUserSelectProvince(Province province) {

        txtProvince.setText(province.getName());
        this.province = province;
        linCity.setVisibility(View.VISIBLE);
        txtCity.setText("شهر");
        city = null;

    }

    public void onUserSelectCity(City city) {

        txtCity.setText(city.getName());
        this.city = city;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_FROM_CAMERA) {
                filePath = mImageCaptureUri.getPath();
                Log.e("filepath--->", filePath);
                Glide.with(getActivity()).load(filePath).into(imgUpload);
            } else if (requestCode == PICK_FROM_FILE) {

                mImageCaptureUri = data.getData();
                filePath = getPath(mImageCaptureUri);
                Log.e("filepath--->", filePath);
                Glide.with(getActivity()).load(filePath).into(imgUpload);
            }

        }

    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public boolean checkConnection() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 4);
            }

        }
        if (isOnline())
            return true;
        else
            return false;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }

}
