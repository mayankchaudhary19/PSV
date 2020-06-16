package com.example.myapplication.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter {
    private List<MyCartItemModel> myCartItemModelList;
    Context context;
//    private

    public MyCartAdapter(List<MyCartItemModel> myCartItemModelList,Context context){
        this.myCartItemModelList = myCartItemModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (myCartItemModelList.get(position).getType()){
            case 0:
                return MyCartItemModel.CART_ITEM;
            case 1:
                return MyCartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case MyCartItemModel.CART_ITEM:
                View cartItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                return new CartItemViewHolder(cartItemView);
            case MyCartItemModel.TOTAL_AMOUNT:
                View cartTotalView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new CartTotalPriceViewHolder(cartTotalView);
            default:
                return null;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (myCartItemModelList.get(position).getType()){
            case MyCartItemModel.CART_ITEM:
                String productId= myCartItemModelList.get(position).getProductId();
                boolean inStock = myCartItemModelList.get(position).isInStock();
                String resource= myCartItemModelList.get(position).getProductImage();
                String title= myCartItemModelList.get(position).getProductTitle();
                String subTitle= myCartItemModelList.get(position).getProductSubtitle();
                String price= myCartItemModelList.get(position).getProductPrice();
                String initialPrice= myCartItemModelList.get(position).getProductInitialPrice();
                String discountAmount= myCartItemModelList.get(position).getProductDiscount();
                Long offerApplied=myCartItemModelList.get(position).getOffersApplied();
                Long couponsApplied=myCartItemModelList.get(position).getCouponsApplied();

                ((CartItemViewHolder)holder).setItemDetails(productId,inStock,resource,title,subTitle,price,initialPrice,discountAmount,offerApplied,couponsApplied);
                break;
            case MyCartItemModel.TOTAL_AMOUNT:
                String totalItems= myCartItemModelList.get(position).getTotalItems();
                String totalItemsPrice= myCartItemModelList.get(position).getTotalItemsPrice();
                String discountItemsPrice= myCartItemModelList.get(position).getDiscountItemsPrice();
                String shippingCharges= myCartItemModelList.get(position).getShippingCharges();
                String subTotal= myCartItemModelList.get(position).getSubTotal();
                ((CartTotalPriceViewHolder)holder).setPriceDetails(totalItems,totalItemsPrice,discountItemsPrice,shippingCharges,subTotal);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return myCartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productSubtitle;
        private TextView productPrice;
        private TextView productInitialPrice;
        private TextView productDiscountAmount;
        private TextView offerApplied;
        private TextView couponsApplied;
        private LinearLayout couponRedemptionLayout;
////////////
        private int mSelectedIndex = 0;
        private int mSelectedIndex2 = 0;
        private int actualPos = 0;
        private EditText quantityNo;
        private String specifiedQuantity;
        private Spinner spinnerQty,spinnerQtyType;
        private Dialog quantityDilog;
        private boolean is123=true;
////////////


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            productSubtitle=itemView.findViewById(R.id.product_subtitle);
            productPrice=itemView.findViewById(R.id.product_price);
            productInitialPrice=itemView.findViewById(R.id.product_initial_price);
            productDiscountAmount=itemView.findViewById(R.id.product_discount);
            offerApplied=itemView.findViewById(R.id.t_v_offer_applied);
            couponsApplied=itemView.findViewById(R.id.t_v_coupon_applied);

            couponRedemptionLayout = itemView.findViewById(R.id.coupon_redemption_layout);

//////////////////
            spinnerQty = itemView.findViewById(R.id.spinner1);
            spinnerQtyType = itemView.findViewById(R.id.spinner2);
        }
        private void setItemDetails(String productId,boolean inStock, String resource, String title, String subtitle, final String price, final String initialPrice, String discountAmount,Long offerAppliedNo ,Long couponsAppliedNo , final int position){
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.square_placeholder)).into(productImage);
            productTitle.setText(title);
            productSubtitle.setText(subtitle);
            if (inStock) {
                productPrice.setText(price);
                productInitialPrice.setText(initialPrice);
                productDiscountAmount.setText(discountAmount);

                if (couponsAppliedNo > 0) {
                    couponsApplied.setVisibility(View.VISIBLE);
                    if (couponsAppliedNo == 1) {
                        couponsApplied.setText("free " + couponsAppliedNo + " coupen");
                    } else {
                        couponsApplied.setText("free " + couponsAppliedNo + " coupens");
                    }
                } else {
                    couponsApplied.setVisibility(View.INVISIBLE);
                }

            }else{

            }

            productPrice.setText(price);
            productInitialPrice.setText(initialPrice);
            productDiscountAmount.setText(discountAmount);
            final List<String> list = new ArrayList<String>();
            list.add("1");      //Initial dummy entry
            list.add("2");
            list.add("3");
            list.add("More");
            list.add("");       //4th index


            final ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, list) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextSize(14);
                    ((TextView) v).setGravity(Gravity.START);
                    if (position == 3)
                        ((TextView) v).setText("Qty: ");


//
                    return v;
                }

                public View getDropDownView(final int position, View convertView, ViewGroup parent) {

                    View v = super.getDropDownView(position, convertView, parent);
                        ((TextView) v).setGravity(Gravity.CENTER);
                    ((TextView) v).setPadding(10,0,10,0);

                    if (position == mSelectedIndex && position < 3) {
                        // Set spinner selected popup item's text color
                        ((TextView) v).setTextColor(Color.parseColor("#FF8A65"));
                    } else if (position == 4 || position == 5) {
                        ((TextView) v).setHeight(0);
                    }
                    if (position==3)
                        ((TextView) v).setPadding(0,0,0,8);
                    if (position==0)
                        ((TextView) v).setPadding(0,4,0,0);
                    return v;
                }


            };

            spinnerQty.setAdapter(langAdapter);

            spinnerQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSelectedIndex = position;
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                    if (position == 0) {
                        is123=true;
                        if (!list.get(4).equals("1")) {
                            list.set(4,"Qty: 1");
                            if (list.size()>5) {
                                list.remove(5);
                            }
                        }
                    } else if (position == 1) {
                        is123=true;
                        if (!list.get(4).equals("2")) {
                            list.set(4,"Qty: 2");
                            if (list.size()>5) {
                                list.remove(5);
                            }

                        }
                    } else if (position == 2) {
                        is123=true;
                        if (!list.get(4).equals("3")) {
                            list.set(4,"Qty: 3");

                            if (list.size()>5) {
                                list.remove(5);
                            }
//                            list.remove(4);
//                            list.add(4, "Qty: 3");
                        }
                    }
                    else if (position == 4) {

                        if (((TextView) spinnerQty.getSelectedView()).getText().toString().equals("Qty: 1")) {
                            spinnerQty.setSelection(0);
                        } else if (((TextView) spinnerQty.getSelectedView()).getText().toString().equals("Qty: 2")) {
                            spinnerQty.setSelection(1);
                        } else if (((TextView) spinnerQty.getSelectedView()).getText().toString().equals("Qty: 3")) {
                            spinnerQty.setSelection(2);
                        }
                    }



                    if (mSelectedIndex == 3) {

                        quantityDilog = new Dialog(itemView.getContext());
                        quantityDilog.setContentView(R.layout.quantity_dialog);
                        quantityDilog.setCancelable(true);
                        quantityDilog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        quantityNo = quantityDilog.findViewById(R.id.editTextQuantity);
                        //todo: quantityNo Code
                        TextView cancelBtn = quantityDilog.findViewById(R.id.cancelQuantityDialogButton);
                        TextView applyBtn = quantityDilog.findViewById(R.id.applyQuantityDialogButton);

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (is123)
                                    spinnerQty.setSelection(4);
                                else
                                    spinnerQty.setSelection(5);
//
                                quantityDilog.dismiss();
                            }
                        });

                        quantityDilog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                if (is123)
                                    spinnerQty.setSelection(4);
                                else
                                    spinnerQty.setSelection(5);
                            }
                        });

                        applyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quantityNo.setError(null);
                                specifiedQuantity = quantityNo.getText().toString();
                                if (quantityNo.getText().toString().isEmpty()) {
                                    quantityNo.setError("Please Specify Quantity");
                                    return;
                                } else if (quantityNo.getText().toString().equals("0")) {
                                    quantityNo.setError("Please Remove Item from Cart");
                                    return;
                                } else {
                                    is123=false;
                                    if (list.size() < 6) {
                                        list.add("");
                                    } else {
                                        int index = list.size() - 1;
                                        list.remove(index);
                                        switch (specifiedQuantity) {
                                            case "1":
                                                spinnerQty.setSelection(0);
                                                break;
                                            case "2":
                                                spinnerQty.setSelection(1);
                                                break;
                                            case "3":
                                                spinnerQty.setSelection(2);
                                                break;
                                            default:
                                                String num =quantityNo.getText().toString();
                                                list.add("Qty: "+num);
                                                spinnerQty.setSelection(5);
//                                                ((TextView) spinnerQty.getSelectedView()).setText("Qty: " + specifiedQuantity);
                                                mSelectedIndex = 3;
                                                break;
                                        }
                                        quantityDilog.dismiss();
                                    }
                                }
                            }
                        });

                        quantityDilog.show();

                    } else if (position < 4) {
                        actualPos = position + 1;
                        ((TextView) spinnerQty.getSelectedView()).setText("Qty: " + actualPos);

                    }
                    if (position == 5) {
                        ((TextView) spinnerQty.getSelectedView()).setText("Qty: " + specifiedQuantity);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            final List<String> list2 = new ArrayList<String>();
            list2.add("Piece [1]");
            list2.add("Pieces [3]");
            list2.add("Pieces [6]");
            list2.add("Dozen [12]");
            list2.add("Gross [144]");

            final ArrayAdapter<String> quantityTypeAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text2, list2) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextSize(14);
                    ((TextView) v).setGravity(Gravity.CENTER);
                    v.setPadding(30,2,0,5);
                    return v;
                }

                public View getDropDownView(final int position, View convertView, ViewGroup parent) {

                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setGravity(Gravity.START);
                    v.setPadding(30,0,0,5);
                    ((TextView) v).setWidth(250);
//                    ((TextView) v).setHeight(62);

                    if (position == mSelectedIndex2)
                        ((TextView) v).setTextColor(Color.parseColor("#FF8A65"));
                    if (position==list2.size()-1)
                        ((TextView) v).setPadding(25,0,20,10);
                    if (position<list2.size()-1)
                        ((TextView) v).setPadding(30,2,0,2);
                    return v;
                }
            };

            spinnerQtyType.setAdapter(quantityTypeAdapter);

            spinnerQtyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSelectedIndex2 = position;
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });



        }

    }



    class CartTotalPriceViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView totalItemsDiscount;
        private TextView shippingCharges;
        private TextView subTotal;

        public CartTotalPriceViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems=itemView.findViewById(R.id.total_items);
            totalItemsPrice=itemView.findViewById(R.id.total_items_price);
            totalItemsDiscount=itemView.findViewById(R.id.saved_or_discount_price);
            shippingCharges=itemView.findViewById(R.id.shipping_price);
            subTotal=itemView.findViewById(R.id.total_or_subTotal_price);
        }

        private void setPriceDetails(String totalItemText,String totalItemsPriceText,String totalItemsDiscountText,String shippingChargesText,String subTotalText){
            totalItems.setText(totalItemText);
            totalItemsPrice.setText(totalItemsPriceText);
            totalItemsDiscount.setText(totalItemsDiscountText);
            shippingCharges.setText(shippingChargesText);
            subTotal.setText(subTotalText);

        }
    }
}







//
//            spinnerLayout.setVisibility(View.GONE);
//            final String productQty=productQuantity.getText().toString();
//
//
//            if (productQty.equals("Qty:  1")){
//                qty_spinner_1.setTextColor(Color.parseColor("#FF8A65"));
//                qty_spinner_2.setTextColor(Color.parseColor("#80000000"));
//                qty_spinner_3.setTextColor(Color.parseColor("#80000000"));
//            }else if (productQty.equals("Qty:  2")){
//                qty_spinner_2.setTextColor(Color.parseColor("#FF8A65"));
//                qty_spinner_1.setTextColor(Color.parseColor("#80000000"));
//                qty_spinner_3.setTextColor(Color.parseColor("#80000000"));
//
//            }else if  (productQty.equals("Qty:  3")){
//                qty_spinner_3.setTextColor(Color.parseColor("#FF8A65"));
//                qty_spinner_2.setTextColor(Color.parseColor("#80000000"));
//                qty_spinner_1.setTextColor(Color.parseColor("#80000000"));
//            }
//            else {
//                qty_spinner_3.setTextColor(Color.parseColor("#80000000"));
//                qty_spinner_2.setTextColor(Color.parseColor("#80000000"));
//                qty_spinner_1.setTextColor(Color.parseColor("#80000000"));
//
//            }
//            productQuantity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    spinnerLayout.setVisibility(View.VISIBLE);
//                    spinnerIsOpen=true;
//                    spinnerLayout.postDelayed(new Runnable() {
//                        public void run() {
//                            spinnerIsOpen=false;
//                            spinnerLayout.setVisibility(View.GONE);
//                        }
//                    }, 4000);
////                    mCallback.onClick(spinnerIsOpen);
//
//                    qty_spinner_1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            qty_spinner_1.setTextColor(Color.parseColor("#FF8A65"));
//                            qty_spinner_2.setTextColor(Color.parseColor("#80000000"));
//                            qty_spinner_3.setTextColor(Color.parseColor("#80000000"));
//                            spinnerLayout.setVisibility(View.GONE);
//                            productQuantity.setText("Qty:  "+ "1");
//                            spinnerIsOpen=false;
//                        }
//                    });
//                    qty_spinner_2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            qty_spinner_2.setTextColor(Color.parseColor("#FF8A65"));
//                            qty_spinner_1.setTextColor(Color.parseColor("#80000000"));
//                            qty_spinner_3.setTextColor(Color.parseColor("#80000000"));
//                            spinnerLayout.setVisibility(View.GONE);
//                            productQuantity.setText("Qty:  "+ "2");
//                            spinnerIsOpen=false;
//                        }
//                    });
//                    qty_spinner_3.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            qty_spinner_3.setTextColor(Color.parseColor("#FF8A65"));
//                            qty_spinner_2.setTextColor(Color.parseColor("#80000000"));
//                            qty_spinner_1.setTextColor(Color.parseColor("#80000000"));
//                            spinnerLayout.setVisibility(View.GONE);
//                            productQuantity.setText("Qty:  "+ "3");
//                            spinnerIsOpen=false;
//                        }
//                    });
//                    qty_spinner_more.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            spinnerLayout.setVisibility(View.GONE);
//                            spinnerIsOpen=false;
//
//                           final Dialog quantityDilog=new Dialog(itemView.getContext());
//                           quantityDilog.setContentView(R.layout.quantity_dialog);
//                           quantityDilog.setCancelable(true);
//                           quantityDilog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                           quantityDilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                           final EditText quantityNo =quantityDilog.findViewById(R.id.editTextQuantity);
//                           //todo: quantityNo Code
//                           TextView cancelBtn=quantityDilog.findViewById(R.id.cancelQuantityDialogButton);
//                           TextView applyBtn=quantityDilog.findViewById(R.id.applyQuantityDialogButton);
//
//                           cancelBtn.setOnClickListener(new View.OnClickListener() {
//                               @Override
//                               public void onClick(View v) {
//                                    quantityDilog.dismiss();
//                               }
//                           });
//                           applyBtn.setOnClickListener(new View.OnClickListener() {
//                               @Override
//                               public void onClick(View v) {
//                                   String specifiedQuantity=quantityNo.getText().toString();
//                                   productQuantity.setText("Qty:  "+ specifiedQuantity);
//                                   if (specifiedQuantity.equals("1")){
//                                       qty_spinner_1.setTextColor(Color.parseColor("#FF8A65"));
//                                       qty_spinner_2.setTextColor(Color.parseColor("#66000000"));
//                                       qty_spinner_3.setTextColor(Color.parseColor("#66000000"));
//                                   }
//                                   else if (specifiedQuantity.equals("2")){
//                                       qty_spinner_2.setTextColor(Color.parseColor("#FF8A65"));
//                                       qty_spinner_1.setTextColor(Color.parseColor("#66000000"));
//                                       qty_spinner_3.setTextColor(Color.parseColor("#66000000"));
//                                   }
//                                   else if (specifiedQuantity.equals("3")){
//                                       qty_spinner_3.setTextColor(Color.parseColor("#FF8A65"));
//                                       qty_spinner_2.setTextColor(Color.parseColor("#66000000"));
//                                       qty_spinner_1.setTextColor(Color.parseColor("#66000000"));
//
//                                   }else {
//                                       qty_spinner_3.setTextColor(Color.parseColor("#66000000"));
//                                       qty_spinner_2.setTextColor(Color.parseColor("#66000000"));
//                                       qty_spinner_1.setTextColor(Color.parseColor("#66000000"));
//
//                                   }
//                                   quantityDilog.dismiss();
//
//                               }
//                           });
//                           quantityDilog.show();
//                            }
//                        });
//                    }
//                });
//            cartItemLL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (spinnerIsOpen){
//                        spinnerLayout.setVisibility(View.GONE);
//                    }
//
//                }
//            });



//            productQuantitySpinner.setOnItemSelectedListener(this);
//            final List<String> quantity = new ArrayList<>();
//            quantity.add("1");
//            quantity.add("2");
//            quantity.add("3");
//            quantity.add("More");
//
//            // Creating adapter for spinner
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, quantity);
//
//            // Drop down layout style - list view with radio button
//            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            // attaching data adapter to spinner
//            productQuantitySpinner.setAdapter(dataAdapter);
////            productQuantitySpinner.onI
//           productQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//               @Override
//               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                   final String selectedItem = parent.getItemAtPosition(position).toString();
//                   if(selectedItem.equals("More"))
//                   {
//                       final Dialog quantityDilog=new Dialog(itemView.getContext());
//                       quantityDilog.setContentView(R.layout.quantity_dialog);
//                       quantityDilog.setCancelable(false);
//                       final EditText quantityNo =quantityDilog.findViewById(R.id.editTextQuantity);
//                       //todo: quantityNo Code
//                       TextView cancelBtn=quantityDilog.findViewById(R.id.cancelQuantityDialogButton);
//                       TextView applyBtn=quantityDilog.findViewById(R.id.applyQuantityDialogButton);
//                       cancelBtn.setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//                                quantityDilog.dismiss();
//                           }
//                       });
//                       applyBtn.setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//                               String specifiedQty = quantityNo.getText().toString();
//                               quantity.add(specifiedQty);
//
//
//                               quantityDilog.dismiss();
//
//                           }
//                       });
//                       quantityDilog.show();
//
//                   }
//               }
//
//               @Override
//               public void onNothingSelected(AdapterView<?> parent) {
//
//               }
//           });
//// int coupons applied
//        }
//
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {