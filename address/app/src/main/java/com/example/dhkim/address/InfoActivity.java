package com.example.dhkim.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;
import java.util.TooManyListenersException;


public class InfoActivity extends Activity {

    private int nMode;
    EditText edName;
    EditText edPhone;
    EditText edAddress;

    Button btInfo;

    public class ButtonListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btInfo) {
                if (nMode == 1) {
                    //Check Name
                    if (edName.getText().length() == 0) {

                        Toast.makeText(InfoActivity.this, "이름을 입력하십시요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Intent intent = getIntent();
                intent.putExtra("Mode", nMode);
                intent.putExtra("info", new AddressInfo(edName.getText().toString()
                        ,edPhone.getText().toString()
                        ,edAddress.getText().toString()));
                setResult(1, intent);
                finish();
            }
        }
    }

    public void SetMode(int mode) {
        nMode = mode;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Check Mode

        Intent intent = getIntent();
        nMode = intent.getIntExtra("Mode", 1);

        edName = (EditText)findViewById(R.id.editName);
        edPhone = (EditText)findViewById(R.id.editPhone);
        edAddress = (EditText)findViewById(R.id.editAddress);
        btInfo = (Button)findViewById(R.id.bt_info);


        ButtonListener btListener = new ButtonListener();

        switch(nMode)
        {
            case 1:
                btInfo.setText("저장");
                break;
            case 2:
                edName.setEnabled(false);
                edPhone.setEnabled(false);
                edAddress.setEnabled(false);
                btInfo.setText("삭제");
                break;
            default:
                btInfo.setText("저장");
                break;
        }
        btInfo.setOnClickListener(btListener);
    }
}
