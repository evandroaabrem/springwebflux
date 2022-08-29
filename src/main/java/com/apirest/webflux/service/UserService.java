package com.apirest.webflux.service;

import com.apirest.webflux.dto.UserDto;
import com.apirest.webflux.entity.User;
import com.apirest.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final UserRepository userRepository;
    public Mono<User> createUser(UserDto userDto){
        return userRepository.save(User.builder().age(userDto.getAge())
                .department(userDto.getDepartment()).name(userDto.getName()).salary(userDto.getSalary()).build());
    }

    public Flux<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Mono<User> findById(String userId){
        return userRepository.findById(userId);
    }

    public Mono<User> updateUser(String userId,  UserDto userDto){
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setAge(userDto.getAge());
                    dbUser.setSalary(userDto.getSalary());
                    return userRepository.save(dbUser);
                });
    }

    public Mono<User> deleteUser(String userId){
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }


}
