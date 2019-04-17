package com.at.arouter.common.util;

/**
 * Dialog工具类
 */
public class DialogUtil {

    public interface OnClickCustomerListener {
        //取消
        void onClickCancel();

        //确定
        void onClickConfirm();
    }


//    /**
//     * 取消确定的提示对话框
//     */
//    public static void showCommonDialog(Context context,
//                                        String title,
//                                        String message,
//                                        String confirm,
//                                        String cancel,
//                                        final OnClickCustomerListener onClickCustomerListener) {
//        View view = View.inflate(context, R.layout.view_dialog_common, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth() * 0.7);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        dialog.setCancelable(false);
//        dialog.show();
//
//
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
//        TextView tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
//        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
//
//
//        if (TextUtils.isEmpty(title)) {
//            tvTitle.setVisibility(View.GONE);
//        } else {
//            tvTitle.setText(title);
//            tvTitle.setVisibility(View.VISIBLE);
//        }
//
//        tvMessage.setText(message);//设置内容
//        tvConfirm.setText(confirm);//设置(确定)按钮内容
//        tvCancel.setText(cancel);//设置(取消)按钮内容
//
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if (onClickCustomerListener != null) {
//                    onClickCustomerListener.onClickConfirm();
//                }
//            }
//        });
//
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if (onClickCustomerListener != null) {
//                    onClickCustomerListener.onClickCancel();
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 备份助记词的提示对话框
//     */
//    public static Dialog showBackupDialog(Context context, final View.OnClickListener clickListener) {
//        View view = View.inflate(context, R.layout.dialog_backup_word, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth() * 0.8);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        dialog.setCancelable(false);
//        dialog.show();
//
//        TextView tv_sure = view.findViewById(R.id.tv_sure);
//        tv_sure.setOnClickListener(clickListener);
//        return dialog;
//    }
////
////    /**
////     * 二维码生成的提示对话框
////     */
////    public static Dialog showConfirmDialog(Context context, String coinName, final View.OnClickListener clickListener) {
////        View view = View.inflate(context, R.layout.dialog_confirm_address, null);
////        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(view);
////        Window window = dialog.getWindow();
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.8);
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.dimAmount = 0.6f;
////        window.setAttributes(lp);
////        dialog.setCancelable(false);
////        dialog.show();
////
////        final TextView iv_hint2 = view.findViewById(R.id.iv_hint2);
////        if (coinName.equals("ETH")) {
////            iv_hint2.setText(context.getString(R.string.confirm_content));
////        } else {
////            iv_hint2.setText(context.getString(R.string.confirm_content_cpc));
////        }
////        TextView tv_sure = view.findViewById(R.id.tv_sure);
////        tv_sure.setOnClickListener(clickListener);
////        return dialog;
////    }
////
////
////    /**
////     * 交易转出的二维码弹框的提示对话框
////     *
////     * @param address  我的地址
////     * @param coinName 币种名称
////     * @param flag     是否显示指定数量条目，为首页不显示，为资产详情显示
////     * @return
////     */
////    public static Dialog showExchangeQrAdressDialog(final Context context, final String address, String coinName, final boolean flag) {
////        View view = View.inflate(context, R.layout.dialog_exchange_qr_adress, null);
////        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(view);
////        Window window = dialog.getWindow();
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.8);
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        window.setAttributes(lp);
////
////        dialog.setCancelable(true);
////        dialog.show();
////
////        if (flag) {
////            view.findViewById(R.id.ll_set).setVisibility(View.VISIBLE);
////        } else {
////            view.findViewById(R.id.ll_set).setVisibility(View.GONE);
////        }
////        final ImageView iv_qrcode = view.findViewById(R.id.iv_adress);
////        final TextView tv_content = view.findViewById(R.id.tv_content);
////        final TextView tv_tem = view.findViewById(R.id.tv_tem);
////        tv_tem.setText(coinName);//保存在界面的币种名称
////        view.findViewById(R.id.tv_set_coin).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                List<String> ls = new ArrayList<>();
////                ls.add(Constants.ASSET_ETH);
////                ls.add(Constants.ASSET_CPC);
////                //显示选项
////                DialogUtil.showSwitchCoinDialog(context,
////                        context.getString(R.string.choose_coin_type),
////                        ls,
////                        tv_tem.getText().toString().trim(),
////                        new ClickCallBack() {
////                            @Override
////                            public void onSuccess(String newCoinName, Dialog dialog) {
////                                final float scale = context.getResources().getDisplayMetrics().density;
////                                int qrCodeDimention = (int) (256 * scale + 0.5f);
////                                QREncoder qrCodeEncoder;
////                                String tvContent = tv_content.getText().toString().trim();
////                                String kbAmout = tvContent.substring(context.getResources().getString(R.string.dialog_value_hint).length(), tvContent.length() - tv_tem.getText().toString().trim().length()).trim();
////                                if (newCoinName.equals("ETH")) {//ETH规则直接显示地址
////                                    qrCodeEncoder = new QREncoder(AddressEncoder.encodeERC(new AddressEncoder(address + "," + kbAmout)), null,
////                                            Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
////                                } else {//其余币种规则直接显示  地址+","+数量+","+币种名称
////                                    qrCodeEncoder = new QREncoder(AddressEncoder.encodeERC(new AddressEncoder(address + "," + kbAmout + "," + newCoinName)), null,
////                                            Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
////                                }
////                                tv_content.setText(context.getResources().getString(R.string.dialog_value_hint) + " " + kbAmout + " " + newCoinName);
////                                try {
////                                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
////                                    iv_qrcode.setImageBitmap(bitmap);
////                                } catch (WriterException e) {
////                                    e.printStackTrace();
////                                }
////                                //换图片
////                                if (newCoinName.equals("ETH")) {
////                                    ((ImageView) view.findViewById(R.id.iv_coin)).setImageResource(R.mipmap.transfer_iocn);
////                                } else {
////                                    ((ImageView) view.findViewById(R.id.iv_coin)).setImageResource(R.mipmap.transfer_cpc);
////                                }
////                                tv_tem.setText(newCoinName);
////                                dialog.dismiss();
////                            }
////                        }
////                );
////            }
////        });
////        //换图片
////        if (tv_tem.getText().toString().trim().equals("ETH")) {
////            ((ImageView) view.findViewById(R.id.iv_coin)).setImageResource(R.mipmap.transfer_iocn);
////        } else {
////            ((ImageView) view.findViewById(R.id.iv_coin)).setImageResource(R.mipmap.transfer_cpc);
////        }
////        final TextView tv_hint1 = view.findViewById(R.id.tv_hint1);
////        tv_hint1.setText(address + "");
////        tv_hint1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
////                // 将文本内容放到系统剪贴板里。
////                if (cm != null) {
////                    cm.setPrimaryClip(ClipData.newPlainText("text", tv_hint1.getText().toString().trim()));
////                    ToastUtil.show(context.getResources().getString(R.string.address_copy_well));
////                }
////            }
////        });
////
////        final float scale = context.getResources().getDisplayMetrics().density;
////        int qrCodeDimention = (int) (256 * scale + 0.5f);
////        QREncoder qrCodeEncoder;
////        if (tv_tem.getText().toString().trim().equals("ETH")) {//ETH规则直接显示地址
////            qrCodeEncoder = new QREncoder(AddressEncoder.encodeERC(new AddressEncoder(address + "")), null,
////                    Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
////        } else {//其余币种规则直接显示  地址+","+数量+","+币种名称
////            qrCodeEncoder = new QREncoder(AddressEncoder.encodeERC(new AddressEncoder(address + "," + "," + tv_tem.getText().toString().trim())), null,
////                    Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
////        }
////
////        try {
////            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
////            iv_qrcode.setImageBitmap(bitmap);
////        } catch (WriterException e) {
////            e.printStackTrace();
////        }
////
////
////        tv_content.setText(context.getString(R.string.dialog_value_hint) + " " + tv_tem.getText().toString().trim());
////        final TextView tv_set_value = view.findViewById(R.id.tv_set_value);
////        tv_set_value.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showExchangeNumDialog(context, tv_content, tv_tem.getText().toString().trim(), iv_qrcode, address);
////            }
////        });
////        return dialog;
////    }
////
////
//
//    /**
//     * 指定转出的数量对话框
//     * tvValue 输入框的填充地方
//     */
//    public static Dialog showExchangeNumDialog(final Context context, final TreasureCurrencyListModel symbol, final ClickCallBack callBack) {
//        View view = View.inflate(context, R.layout.dialog_exchange_num, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth() * 0.8);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        dialog.setCancelable(false);
//        dialog.show();
//        TextView tv_hint1 = view.findViewById(R.id.tv_hint1);
//        tv_hint1.setText(String.format(context.getResources().getString(R.string.treasure_num_page), symbol.symbol));
//        final EditText et_value = view.findViewById(R.id.et_value);
//        EcologyUtils.autoSoftInput(dialog, et_value);
//        if (symbol.type == 1) {
//            et_value.setHint(context.getResources().getString(R.string.treasure_num) + DecimalCalUtils.round(new BigDecimal(symbol.balance).doubleValue(), 4));
//            //eth位8位小数  其余为整数
//            et_value.addTextChangedListener(new CountTextWatcher(et_value, new TextWatchCallBack() {
//                @Override
//                public void onSuccess(EditText editText) {
//
//                }
//            }, Constants.INTEGER_COUNT, 2));
//        } else {
//            et_value.setHint(context.getResources().getString(R.string.treasure_num2) + DecimalCalUtils.round(new BigDecimal(symbol.userAmount).doubleValue(), 4));
//            //eth位8位小数  其余为整数
//            et_value.addTextChangedListener(new CountTextWatcher(et_value, new TextWatchCallBack() {
//                @Override
//                public void onSuccess(EditText editText) {
//
//                }
//            }, Constants.INTEGER_COUNT, 2));
//        }
//
//        TextView tv_no = view.findViewById(R.id.tv_no);
//
//        ImageView imageView = view.findViewById(R.id.ivQuestion);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogUtil.showSimpleDialog(context, null);
//            }
//        });
//        tv_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        TextView tv_yes = view.findViewById(R.id.tv_yes);
//        tv_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String value = et_value.getText().toString();
//                if (TextUtils.isEmpty(value)) {
//                    ToastUtil.show(context.getString(R.string.numberCoin));
//                    return;
//                }
//                //最大值限制
//                if (symbol.type == 1) {
//                    if (new BigDecimal(symbol.balance).subtract(new BigDecimal(value)).doubleValue() < 0) {
//                        ToastUtil.show(context.getString(R.string.numberCoin));
//                        return;
//                    }
//                } else {
//                    if (new BigDecimal(symbol.userAmount).subtract(new BigDecimal(value)).doubleValue() < 0) {
//                        ToastUtil.show(context.getString(R.string.numberCoin));
//                        return;
//                    }
//                }
//                callBack.onSuccess(et_value.getText().toString(), dialog);
//            }
//        });
//        return dialog;
//    }
////
////    /**
////     * 简单列表选择 公用对话框
////     */
////    public static Dialog showSimpleDialog(Context context, String title, List<String> list, String type) {
////        View view = View.inflate(context, R.layout.dialog_simple_list, null);
////        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(view);
////        Window window = dialog.getWindow();
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.8);
////        lp.height = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.7);
////        window.setAttributes(lp);
////        dialog.setCancelable(true);
////        dialog.show();
////        TextView textView = view.findViewById(R.id.tv_hint1);
////        textView.setText(title + "");
////        RecyclerView recycleView = view.findViewById(R.id.recycleView_language);
////
////        recycleView.setOverScrollMode(View.OVER_SCROLL_NEVER);
////        //设置显示样式
////        final GridLayoutManager mLayoutMgr = new GridLayoutManager(context, 1);
////        recycleView.setLayoutManager(mLayoutMgr);
////        List<String> data = (List<String>) list;
////
////
////        SimpleDataAdapter simpleDataAdapter = new SimpleDataAdapter(context, data, dialog, type);
////
////        simpleDataAdapter.isFirstOnly(false);
////        recycleView.setAdapter(simpleDataAdapter);
////        //可能需要移除之前添加的布局
////        simpleDataAdapter.removeAllFooterView();
////        simpleDataAdapter.notifyDataSetChanged();
////        return dialog;
////    }
////
////
//
//    /**
//     * 确认转账信息对话框
//     */
//    public static Dialog sureTransferDialog(final Context context,
//                                            final EthTransactionData ethTransactionData,
//                                            final FeeModel feeModel,
//                                            final DialogTextViewCallBack onClickListener) {
//        View view = View.inflate(context, R.layout.dialog_transfer_sure, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth());
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.BOTTOM;
//        window.setAttributes(lp);
//        dialog.setCancelable(true);
//        dialog.show();
//        try {
//            //数量
//            ((TextView) view.findViewById(R.id.iv_text1)).setText(DecimalCalUtils.round(new BigDecimal(ethTransactionData.amount).doubleValue(), Constants.DOT_COUNT_NUMBER) + ethTransactionData.symbol);
//            //矿工费
//            ((TextView) view.findViewById(R.id.iv_text2)).setText(DecimalCalUtils.round(new BigDecimal(feeModel.minerFee).doubleValue(), Constants.DOT_COUNT_MINER) + ethTransactionData.symbol + " ≈ " + DecimalCalUtils.round(new BigDecimal(feeModel.minerFee_scPrice).doubleValue(), Constants.DOT_COUNT_MONEY) + "USD");
//            //手续费
//            ((TextView) view.findViewById(R.id.iv_text6)).setText(DecimalCalUtils.round(new BigDecimal(feeModel.fee).doubleValue(), Constants.DOT_COUNT_FEE) + ethTransactionData.symbol);
//            //发款
//            ((TextView) view.findViewById(R.id.iv_text3)).setText(feeModel.address + "");
//            //收款
//            ((TextView) view.findViewById(R.id.iv_text4)).setText(ethTransactionData.toAddr + "");
//            //备注
//            ((TextView) view.findViewById(R.id.iv_text5)).setText(ethTransactionData.remark + "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        view.findViewById(R.id.iv_copy_get).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EcologyUtils.copy(context, feeModel.address);
//            }
//        });
//        view.findViewById(R.id.iv_copy_set).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EcologyUtils.copy(context, feeModel.toAddress);
//            }
//        });
//
//        TextView tv_sure = view.findViewById(R.id.tv_sure);
//        tv_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickListener.getText(v, "", dialog);
//
//
//            }
//        });
//        return dialog;
//    }
////
////
////    /**
////     * 输入密码
////     */
////    public static Dialog showPwdDialog(View v, final Context context, final DialogTextViewCallBack callBack) {
////        View view = View.inflate(context, R.layout.dialog_check_pwd, null);
////        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(view);
////        Window window = dialog.getWindow();
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.8);
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        window.setAttributes(lp);
////        dialog.setCancelable(false);
////        dialog.show();
////        final EditText et_value = view.findViewById(R.id.et_value);
////
////        TextView tv_no = view.findViewById(R.id.tv_no);
////        tv_no.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                dialog.dismiss();
////            }
////        });
////
////        final TextView tv_yes = view.findViewById(R.id.tv_yes);
////        tv_yes.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                callBack.getText(v, et_value.getText().toString().trim(), dialog);
////            }
////        });
////        return dialog;
////    }
////
//
//    /**
//     * 切换资产对话框
//     */
//    public static Dialog showSwitchCoinDialog(Context context, String title, List<SupportCoinModel> list, String type, ClickCallBack callBack) {
//        View view = View.inflate(context, R.layout.dialog_simple_list, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth() * 0.9);
//        lp.height = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getHeight() * 0.375);
//        lp.gravity = Gravity.CENTER;
//        window.setAttributes(lp);
//        dialog.setCancelable(true);
//        dialog.show();
//        TextView textView = view.findViewById(R.id.tv_hint1);
//        textView.setText(title + "");
//        RecyclerView recycleView = view.findViewById(R.id.recycleView_language);
//
//        recycleView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        //设置显示样式
//        final GridLayoutManager mLayoutMgr = new GridLayoutManager(context, 1);
//        recycleView.setLayoutManager(mLayoutMgr);
//
//        CoinAdapter coinAdapter = new CoinAdapter(context, list, dialog, type, callBack);
//
//        coinAdapter.isFirstOnly(false);
//        recycleView.setAdapter(coinAdapter);
//        //可能需要移除之前添加的布局
//        coinAdapter.removeAllFooterView();
//        coinAdapter.notifyDataSetChanged();
//        return dialog;
//    }
//
////
////
////    /**
////     * 复制拷贝完成的提示
////     */
////    public static Dialog showSimpleDialog(Context context, final View.OnClickListener clickListener) {
////        View view = View.inflate(context, R.layout.dialog_confirm_copy, null);
////        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(view);
////        Window window = dialog.getWindow();
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (window.getWindowManager()
////                .getDefaultDisplay()
////                .getWidth() * 0.8);
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.dimAmount = 0.6f;
////        window.setAttributes(lp);
////        window.setWindowAnimations(R.style.dialogShow);
////        dialog.setCancelable(false);
////        dialog.show();
////
////        final TextView iv_hint2 = view.findViewById(R.id.iv_hint2);
////
////        TextView tv_sure = view.findViewById(R.id.tv_sure);
////        tv_sure.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String url = "http://wallet.chainplus.us/wallet/node/walletdown/index.html";
////                ATWalletUtils.copy(context, url, "");
////                dialog.dismiss();
////            }
////        });
////        return dialog;
////    }
//
//
//    /**
//     * 余币宝的弹窗
//     */
//    public static Dialog showSimpleDialog(Context context, final View.OnClickListener clickListener) {
//        View view = View.inflate(context, R.layout.dialog_transfer_attention, null);
//        final Dialog dialog = new Dialog(context, R.style.Dialog_Theme_Transparent);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getWidth() * 0.8);
//        lp.height = (int) (window.getWindowManager()
//                .getDefaultDisplay()
//                .getHeight() * 0.6);
//        lp.dimAmount = 0.6f;
//        window.setAttributes(lp);
//        dialog.setCancelable(true);
//        dialog.show();
//
//        final TextView iv_hint2 = view.findViewById(R.id.iv_hint2);
//
//        TextView tv_sure = view.findViewById(R.id.tv_sure);
//        tv_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//        return dialog;
//    }

}
