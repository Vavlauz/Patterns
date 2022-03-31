package ru.netology.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestInfo {
    private final String city;
    private final String name;
    private final String phone;

//    public RequestInfo(String city, String name, String phone) {
//        this.name = name;
//        this.phone = phone;
//        this.city = city;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public String getCity() {
//        return city;
//    }
}
