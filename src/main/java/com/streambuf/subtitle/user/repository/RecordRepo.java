package com.streambuf.subtitle.user.repository;

import com.streambuf.subtitle.user.model.po.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepo extends JpaRepository<Record, Long> {

    @Query(value = "SELECT count(r.id) FROM Record r WHERE r.userId = ?1 and r.uploadTm LIKE CONCAT('%',?2,'%')")
    Integer countTodayUploadTimes(Long userId, String today);

    Optional<Record> findByIdAndUserId(Long id, Long userId);

    List<Record> findAllByUserId(Long userId);

}
