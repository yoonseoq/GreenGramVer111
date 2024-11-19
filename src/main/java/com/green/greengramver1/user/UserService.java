package com.green.greengramver1.user;

import com.green.greengramver1.user.model.UserInsReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    public int insUser(UserInsReq p) {
        return mapper.insUser(p);
    }
}
