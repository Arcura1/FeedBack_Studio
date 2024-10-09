package org.example.feedbackstudio.login.dao;

import org.example.feedbackstudio.login.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {



}