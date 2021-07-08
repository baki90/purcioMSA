package com.user.dto;

import lombok.Data;
import com.user.domain.Address;

@Data
public class UserResDTO {

    private String name;
    private String password;
    private String picture;

    private String nickName; // 닉네임
    private String phoneNumber; // 휴대폰 번호
    private Integer age; // 나이
    private String sex; // 성별

    private Address address; // 거주지
}
