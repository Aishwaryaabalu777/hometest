package com.example.auth.repository;

import com.example.auth.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log,Integer> {

    List<Log> findByEmail(String username);
}
