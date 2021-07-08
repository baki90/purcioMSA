package com.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.user.domain.User;
import com.user.dto.UserReqDTO;
import com.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> retrieveUser(@PathVariable Long id){
        return userService.retrieveUser(id);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> retrieveUserList(){
        return userService.retrieveUserList();
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserReqDTO userCreateReqDTO){
        return userService.createUser(userCreateReqDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserReqDTO userUpdateReqDTO){
        return userService.updateUser(id, userUpdateReqDTO);
    }

    /**
     * 닉네임의 존재 여부를 알려줍니다.
     * @param nickName
     * @return boolean
     */
    @GetMapping("/isExist/nickname={nickName}")
    public ResponseEntity<Boolean> isExistNickName(@PathVariable("nickName") String nickName){
        return ResponseEntity.ok().body(userService.nickNameIsExist(nickName));
    }

    /**
     * 이메일의 존재 여부를 알려줍니다.
     * @param email
     * @return void
     */
    @GetMapping("/isExist/email={email}")
    public ResponseEntity<Boolean> isExistEmail(@PathVariable("email") String email){
        return ResponseEntity.ok().body(userService.emailIsExist(email));
    }


}
