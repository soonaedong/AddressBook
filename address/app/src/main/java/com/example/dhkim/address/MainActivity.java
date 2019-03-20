package com.example.dhkim.address;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import android.widget.Toast;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{

    EditText    input_name;
    Button      add_button;
    ListView    address_list;

    EditListener input_listener;
    ButtonListener bt_listener;

    List<String> list;

    SQLiteDatabase address_db;


    public int updateList(String input) {
        // Query 로 받아오는거 추가 필요
        list.clear();
        Cursor cursor;
        int cnt = 0;
        if (input.length() > 0) {
            cursor = address_db.rawQuery("SELECT name, phone_num, address FROM TB_ADDRESS WHERE name LIKE \"%" + input + "\" ORDER BY name", null);
        }
        else {
            cursor = address_db.rawQuery("SELECT name, phone_num, address FROM TB_ADDRESS ORDER BY name", null);
        }

        while(cursor.moveToNext())
        {
            list.add(cursor.getString(0));
            cnt++;
        }
        return cnt;
    }

    public int insertData(AddressInfo info)
    {
        Log.i("DB RESULT", info.getName() + info.getPhone() + info.getAddress());
        address_db.execSQL("INSERT INTO TB_ADDRESS(name, phone_num, address) VALUES(?,?,?)",
                new String[]{info.getName(), info.getPhone(), info.getAddress()});
        return 1;
    }
    public class EditListener implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v == input_name) {

                Editable str = input_name.getText();
                updateList(str.toString());

            }
            return false;
        }
    }

    public class ButtonListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == add_button) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class );
                intent.putExtra("Mode", (int)1);
                startActivityForResult(intent, 0);
                //startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case 1: //저장
                AddressInfo info = (AddressInfo)data.getSerializableExtra("info");
                //Insert 하는 부분
                if (insertData(info) == 0) {
                    Toast.makeText(this, "INSERT FAILED", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2: //삭제
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_listener = new EditListener();
        bt_listener = new ButtonListener();

        input_name = (EditText)findViewById( R.id.text_input);
        add_button = (Button)findViewById( R.id.btn_add);
        address_list = (ListView)findViewById(R.id.list_address);
        input_name.setOnEditorActionListener(input_listener);
        add_button.setOnClickListener(bt_listener);

        // DB Initialize
        DBHelper helper = new DBHelper(this);
        address_db = helper.getWritableDatabase();

        //데이터를 저장하게 되는 리스트
        list = new ArrayList<>();

        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        address_list.setAdapter(adapter);

        //Get data from DB
        updateList("");
    }
}
