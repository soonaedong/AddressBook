package com.example.dhkim.address;

import java.io.Serializable;

public class AddressInfo implements Serializable {

    private String name;
    private String phone;
    private String address;

    public AddressInfo(String name, String phone, String address) {
        setName(name);
        setPhone(phone);
        setAddress(address);
    }

    public String getName() {return name;}
    public void setName(String input_name) {name = input_name;}

    public String getPhone() {return phone;}
    public void setPhone(String input_phone) {phone = input_phone;}

    public String getAddress() {return address;}
    public void setAddress(String input_address) {address = input_address;}

}
