package com.fuckSpring.repository;

import com.fuckSpring.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by upsmart on 17-5-11.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Integer> {
    User findByName(String name);
}
