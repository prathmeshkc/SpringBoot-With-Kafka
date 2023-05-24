package com.pcandroiddev.kafkaconsumerdatabase.repository;

import com.pcandroiddev.kafkaconsumerdatabase.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}
