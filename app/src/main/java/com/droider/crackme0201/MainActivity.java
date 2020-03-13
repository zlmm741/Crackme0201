package com.droider.crackme0201;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText edit_userName;
    private EditText edit_sn;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.unregister);
        edit_userName = (EditText)findViewById(R.id.edit_username);
        edit_sn = (EditText)findViewById(R.id.edit_sn);
        btn_register = (Button)findViewById(R.id.button_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // trim()：去掉原始字符串头部和尾部的空格
                if (!checkSN(edit_userName.getText().toString().trim(),
                        edit_sn.getText().toString().trim())) {
                    // 验证失败后弹出注册失败的 Toast
                    Toast.makeText(MainActivity.this,
                            R.string.unsuccessed,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // 验证成功后弹出注册成功的 Toast
                    Toast.makeText(MainActivity.this,
                            R.string.successed,
                            Toast.LENGTH_SHORT).show();
                    // 设置按钮不可点击
                    btn_register.setEnabled(false);
                    // 设置标题
                    setTitle(R.string.registered);
                }
            }
        });
    }

    // 验证注册码函数
    private boolean checkSN(String userName, String sn) {
        try {
            if ((userName == null) || (userName.length() == 0)) {
                return false;
            }
            if ((sn == null) || (sn.length() != 16)) {
                return false;
            }
            // MessageDigest 类为应用程序提供信息摘要算法的功能，
            // 如 MD5 或 SHA 算法。信息摘要是安全的单向哈希函数，
            // 它接收任意大小的数据，输出固定长度的哈希值。
            // MessageDigest 对象开始被初始化
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 调用 reset 方法重置摘要
            digest.reset();
            // 使用 update 方法处理数据
            digest.update(userName.getBytes());
            // 所有要更新的数据都被更新后，
            // 应调用 digest 方法之一完成哈希计算
            byte[] bytes = digest.digest();
            String hexstr = toHexString(bytes, "");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hexstr.length(); i += 2) {
                sb.append(hexstr.charAt(i));
            }
            String userSN = sb.toString();
            // 不区分大小写
            if (!userSN.equalsIgnoreCase(sn)) {
                return false;
            }
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // 加密函数
    private static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b: bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex).append(separator);
        }
        return hexString.toString();
    }
}
