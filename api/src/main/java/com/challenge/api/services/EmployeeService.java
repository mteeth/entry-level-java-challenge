package com.challenge.api.services;

import com.challenge.api.model.Employee;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getByUuid(UUID id);

    Employee create(Employee toCreate);
}
