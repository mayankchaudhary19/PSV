package com.example.myapplication.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DBqueries;
import com.example.myapplication.MainActivity;
import com.example.myapplication.OrderConfirmationActivity;
import com.example.myapplication.OrderSummaryActivity;
import com.example.myapplication.PaymentActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentModeFragment  extends BottomSheetDialogFragment {

    private RadioGroup radioGroup;
    private RadioButton paymentModeRadioButton;
    private TextView continueButton;
    private TextView paymentTotalAmount;
    private Dialog loadingDialog;
    public static boolean fromCart;


    public PaymentModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_payment_mode, container, false);
        radioGroup=(RadioGroup)view.findViewById(R.id.paymentRadioGroup);
        continueButton=view.findViewById(R.id.payment_continue_btn);
        paymentTotalAmount=view.findViewById(R.id.payment_total_amount);
        Bundle bundle = getArguments();
        String totalAmt = bundle.getString("amount");
        paymentTotalAmount.setText(totalAmt);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                paymentModeRadioButton = (RadioButton) view.findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(getContext(),"Select Payment Method", Toast.LENGTH_SHORT).show();
                }else {
                    loadingDialog.show();

                    String text = paymentModeRadioButton.getText().toString().trim();
                    if (text.equals("Cash on Delivery")){
                        Intent otpIntent = new Intent(getContext(), PaymentActivity.class);
                        //todo: pass mobile number through bundle
                        // otpIntent.putExtra("mobileNo",.substring(0,10));
                        startActivity(otpIntent);
                        dismiss();
                    }else {
                        //todo:
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                        }

                        final String mId = "ACjuaHj2022831154117";
                        final String customerId = FirebaseAuth.getInstance().getUid();
                        final String orderId = UUID.randomUUID().toString().substring(0,18);
                        final String url = "https://psvplasticworks.000webhostapp.com/Paytm/generateChecksum.php";
                        final String callBackUrl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonObject=new JSONObject(response);

                                    if (jsonObject.has("CHECKSUMHASH")){
                                        String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");

                                        PaytmPGService paytmPGService = PaytmPGService.getStagingService("https://securegw-stage.paytm.in/order/process");//todo: getProductionService()

                                        HashMap<String, String> paramMap = new HashMap<String, String>();
                                        paramMap.put("MID", mId);
// Key in your staging and production MID available in your dashboard
                                        paramMap.put("ORDER_ID", orderId);
                                        paramMap.put("CUST_ID", customerId);
                                        paramMap.put("CHANNEL_ID", "WAP");
                                        paramMap.put("TXN_AMOUNT", paymentTotalAmount.getText().toString().substring(1, paymentTotalAmount.getText().length()));
                                        paramMap.put("WEBSITE", "WEBSTAGING");
// This is the staging value. Production value is available in your dashboard
                                        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
// This is the staging value. Production value is available in your dashboard
                                        paramMap.put("CALLBACK_URL", callBackUrl);
                                        paramMap.put("CHECKSUMHASH",CHECKSUMHASH);

                                        PaytmOrder order=new PaytmOrder(paramMap);
                                        paytmPGService.initialize(order,null);
                                        paytmPGService.startPaymentTransaction(getContext(), true, true, new PaytmPaymentTransactionCallback() {
                                            @Override
                                            public void onTransactionResponse(Bundle inResponse) {
                                              //  Toast.makeText(getContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                                                if (inResponse.getString("STATUS").equals("TXN_SUCCESS")){
                                                    if (fromCart){
                                                        loadingDialog.show();
                                                        Map<String,Object> updateCartList = new HashMap<>();
                                                        long cartListSize=0;
                                                        final List<Integer> indexList =new ArrayList<>();
                                                        for(int x = 0; x< DBqueries.cartList.size(); x++){
                                                            if (!DBqueries.cartItemModelList.get(x).isInStock()){
                                                                updateCartList.put("productId"+cartListSize,DBqueries.cartItemModelList.get(x).getProductId());
                                                                cartListSize++;
//                                                                Toast.makeText(getContext(), "yessss", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                indexList.add(x);
                                                            }
                                                        }
                                                        updateCartList.put("cartListSize", cartListSize);

                                                        FirebaseFirestore.getInstance().collection("Users")
                                                                .document(FirebaseAuth.getInstance().getUid())
                                                                .collection("UserData")
                                                                .document("Cart")
                                                                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
////                                                                    for (int x=0;x<indexList.size();x++){
//                                                                        DBqueries.cartList.remove(1);
//                                                                        DBqueries.cartItemModelList.remove(1);
////                                                                        OrderSummaryActivity.priceDetailsLL.setVisibility(View.GONE);
////                                                                    }
                                                                    for (int x=indexList.size()-1;x>=0;x--){
                                                                        DBqueries.cartList.remove(indexList.get(x).intValue());
                                                                        DBqueries.cartItemModelList.remove(indexList.get(x).intValue());
//                                                                        OrderSummaryActivity.priceDetailsLL.setVisibility(View.GONE);
                                                                    }

                                                                }else {
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                                                }
                                                                loadingDialog.dismiss();
                                                            }
                                                        });
                                                    }

                                                    Intent intent =new Intent(getContext(), OrderConfirmationActivity.class);
                                                    intent.putExtra("OrderId",inResponse.getString("ORDERID"));
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void networkNotAvailable() {
                                                Toast.makeText(getContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void clientAuthenticationFailed(String inErrorMessage) {
                                                Toast.makeText(getContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void someUIErrorOccurred(String inErrorMessage) {
                                                Toast.makeText(getContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                                Toast.makeText(getContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onBackPressedCancelTransaction() {
                                                Toast.makeText(getContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                                Toast.makeText(getContext(), "Transaction Cancelled " + inResponse.toString(), Toast.LENGTH_LONG).show();

                                            }
                                        });

                                        }

                                }catch (JSONException e){
                                    e.printStackTrace();
//                                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                                }

                            }
                        },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
//                                dismiss();

                            }
                        }) {
                                @Override
                                protected Map<String, String> getParams () throws AuthFailureError {
                                    Map<String, String> paramMap = new HashMap<String, String>();
                                    paramMap.put("MID", mId);
// Key in your staging and production MID available in your dashboard
                                    paramMap.put("ORDER_ID", orderId);
                                    paramMap.put("CUST_ID", customerId);
                                    paramMap.put("CHANNEL_ID", "WAP");
                                    paramMap.put("TXN_AMOUNT", paymentTotalAmount.getText().toString().substring(1, paymentTotalAmount.getText().length())); //matlab apan sirf paise kitne h wo le rhe h Rs. and /- ye sab nhi
                                    paramMap.put("WEBSITE", "WEBSTAGING");
// This is the staging value. Production value is available in your dashboard
                                    paramMap.put("INDUSTRY_TYPE_ID", "Retail");
// This is the staging value. Production value is available in your dashboard
                                    paramMap.put("CALLBACK_URL", callBackUrl);
//                                    paramMap.put("CHECKSUMHASH",CHECKSUMHASH);
                                    return paramMap;
//                                    return super.getParams();
                                }
                            };

                        requestQueue.add(stringRequest);

//                        dismiss();

                    }
                }
            }
        });

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        loadingDialog.dismiss();
    }
}
