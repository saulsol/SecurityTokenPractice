package com.example.securitytokenpractice.repository;

import com.example.securitytokenpractice.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String> {


}
