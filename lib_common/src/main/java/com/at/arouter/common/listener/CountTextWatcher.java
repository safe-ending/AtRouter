package com.at.arouter.common.listener;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.at.arouter.common.callback.TextWatchCallBack;


/**
 * desc:  输入框数字输入监听，几位小数几位整数
 * author:  yangtao
 * <p>
 * creat: 2018/8/19 19:05
 */

public class CountTextWatcher implements TextWatcher {

    EditText editText;
    TextWatchCallBack callBack;
    //整数位
    public static int INTEGER_COUNT = 5;
    //小数位
    public static int DECIMAL_COUNT = 8;

    private double MIN_MARK = 0.01;

    public static double MAX_MARK = 0.01;
    public static boolean isSetMax = false;

    public CountTextWatcher(EditText editText, TextWatchCallBack callBack, int max1, int max2) {
        this.editText = editText;
        this.callBack = callBack;
        this.INTEGER_COUNT = max1;
        this.DECIMAL_COUNT = max2;
        isSetMax = false;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        //首位连续2个0剔除为0.
        if (s.toString().equals("00") || s.toString().equals("01") || s.toString().equals("02") || s.toString().equals("03")
                || s.toString().equals("04") || s.toString().equals("05") || s.toString().equals("06")
                || s.toString().equals("06") || s.toString().equals("07") || s.toString().equals("08") || s.toString().equals("09")) {
            editText.setText("0");
            editText.setSelection(1);
        }
        if (s.toString().length() > 1 && !s.toString().contains(".") && s.toString().startsWith("0")) {
            editText.setText(s.subSequence(1, s.length()));
        }
    }


    @Override
    public void afterTextChanged(Editable s) {
        judgeNumber(s, editText);
        editText.setSelection(editText.getText().toString().length());
        callBack.onSuccess(editText);
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后8位）
     *
     * @param edt
     */
    public static void judgeNumber(Editable edt, EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int secondDot = temp.indexOf(".", posDot + 1);
        int index = editText.getSelectionStart();//获取光标位置
        if (posDot == 0) {//必须先输入数字后才能输入小数点
            edt.delete(0, temp.length());//删除所有字符
            return;
        }


        if (posDot < 0) {//不包含小数点
            if (temp.length() > INTEGER_COUNT) {
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            }
        }
        if (posDot > INTEGER_COUNT) {//小数点前大于5位数就删除光标前一位
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }
        //为整数  //此时不能再次在外层设置setSelection属性
        if (DECIMAL_COUNT == 0 && posDot > 0) {
            editText.setText(temp.substring(0, temp.length() - 1));//删除光标前的字符

            return;
        }
        //重复小数点
        if (secondDot != -1 && secondDot > posDot) {//小数点删除光标前一位小数点
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }

        if (posDot != -1 && temp.length() - posDot - 1 > DECIMAL_COUNT)//如果包含小数点
        {
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }

        //设置最大值MAX_MARK
        if (isSetMax) {
            double num;
            //重复小数点
            if (secondDot != -1 && secondDot > posDot) {
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            } else if (TextUtils.isEmpty(temp)) {
                edt.delete(0, temp.length());//删除所有字符
                return;
            } else {
                num = Double.parseDouble(temp);
            }
            //设置最大值
            if (num > MAX_MARK) {
                temp = String.valueOf(MAX_MARK);
                editText.setText(temp);
            }
            return;
        }
    }
}



