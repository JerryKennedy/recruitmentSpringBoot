package com.recruiting.pros.repository;

import com.recruiting.pros.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByEmail(String email);
    @Modifying
    @Query("UPDATE Employee e SET e.status = :status WHERE e.id = :id")
    void updateEmployeeStatus(@Param("id") int employeeId, @Param("status") String status);
    List<Employee> findByNameAndAppliedAndEmailAndStatus(String name, String applied, String email, String status);

}
