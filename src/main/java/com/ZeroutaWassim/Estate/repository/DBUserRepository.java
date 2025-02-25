package com.ZeroutaWassim.Estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZeroutaWassim.Estate.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    public DBUser findByUsername(String username);
    public DBUser findByEmail(String email);
}