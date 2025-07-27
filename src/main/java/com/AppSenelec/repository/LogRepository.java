package com.AppSenelec.repository;

import com.AppSenelec.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    @Query("SELECT l FROM Log l WHERE l.isDeleted = false")
    List<Log> findAllNotDeleted();
} 