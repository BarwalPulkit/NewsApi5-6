package com.example.newApi.repository;

import com.example.newApi.contract.ApiLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiLogs, Long> {
    @Query(value = "SELECT user_id, COUNT(*) as occurrences " +
            "FROM api_logs " +
            "WHERE date >= CURRENT_DATE - INTERVAL '7 days' " +
            "GROUP BY user_id " +
            "ORDER BY occurrences DESC,user_id ASC", nativeQuery = true)
    public List<Object> getLeaderboard();

    ApiLogs findByRequestAndDateAndEndPoint(String request, LocalDate date, String endPoint);
}
