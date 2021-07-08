package com.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.user.common.exception.EmailIsAlreadyExist;
import com.user.common.exception.NickNameIsAlreadyExist;
import com.user.common.exception.NotFoundUserException;
import com.user.domain.User;
import com.user.dto.UserReqDTO;
import com.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> retrieveUserList(){
        List<User> userList = userRepository.findAll();

        return ResponseEntity.ok().body(userList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<User> retrieveUser(Long id){
        User user = userRepository.findById(id).orElse(null);

        return ResponseEntity.ok().body(user);
    }

    /**
     * 유저를 생성한다.
     * @param userReqDTO 등록 정보
     * @return 생성된 유저의 아이디
     */
    @Transactional
    public ResponseEntity<String> createUser(UserReqDTO userReqDTO){

        if(emailIsExist(userReqDTO.getEmail())) {
            throw new EmailIsAlreadyExist("이미 존재하는 이메일입니다.");
        }

        if(nickNameIsExist(userReqDTO.getNickName())){
            throw new NickNameIsAlreadyExist("이미 존재하는 닉네임입니다.");
        }

        // TODO: password 암호화 작업 필요
        User user = User.createUser(userReqDTO.getName(), userReqDTO.getPassword(), userReqDTO.getEmail(),
                userReqDTO.getPicture(), userReqDTO.getNickName(),
                userReqDTO.getPhoneNumber(), userReqDTO.getAge(),
                userReqDTO.getSex(), userReqDTO.getAddress());

        userRepository.save(user);

        return ResponseEntity.ok().body(user.getId());
    }

    /** 유저를 변경한다.
     * @param id 유저 아이디
     * @param userUpdateReqDTO 변경 정보
     * @return 변경된 유저의 아이디
     */
    @Transactional
    public ResponseEntity<String> updateUser(Long id, UserReqDTO userUpdateReqDTO){
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);

        if(nickNameIsExist(userUpdateReqDTO.getNickName())){
            throw new NickNameIsAlreadyExist("이미 존재하는 닉네임입니다.");
        }

        user.updateUser(userUpdateReqDTO.getName(), userUpdateReqDTO.getPassword(),
                userUpdateReqDTO.getPicture(), userUpdateReqDTO.getNickName(),
                userUpdateReqDTO.getPhoneNumber(), userUpdateReqDTO.getAge(),
                userUpdateReqDTO.getSex(), userUpdateReqDTO.getAddress());

        userRepository.save(user);

        return ResponseEntity.ok().body(user.getId());
    }

    /**
     * 유저를 삭제한다.
     * @param id
     * @return 삭제된 유저의 아이디
     */
    @Transactional
    public ResponseEntity<String> deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);

        userRepository.delete(user);

        return ResponseEntity.ok().body(user.getId());
    }

    /**
     * 현재 해당 이메일이 존재하는지 판별한다.
     * @param email
     * @return 존재한다면 TRUE & 없다면 False
     */
    @Transactional(readOnly = true)
    public Boolean emailIsExist(String email){
        User user = userRepository.findUserByEmail(email).orElse(null);

        return user != null ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 해당 닉네임이 존재하는지 판별한다.
     * @param nickName
     * @return 존재한다면 TRUE & 없다면 FALSE
     */
    @Transactional(readOnly = true)
    public Boolean nickNameIsExist(String nickName){
        User user = userRepository.findUserByNickName(nickName).orElse(null);

        return user != null ? Boolean.TRUE : Boolean.FALSE;
    }


}
