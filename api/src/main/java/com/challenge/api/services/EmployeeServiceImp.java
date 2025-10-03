package com.challenge.api.services;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private final Map<UUID, Employee> store = new ConcurrentHashMap<>();

    public EmployeeServiceImp() {
        seed("Rashy", "Manu", "rashyManu@gmail.com", "Manager", 105000, 29);
        seed("James", "Johnson", "JJ@gmail.com", "Developer", 138500, 37);
    }

    @Override
    public List<Employee> getAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Employee getByUuid(UUID id) {
        Employee e = store.get(id);
        if (e == null) throw new NoSuchElementException("Employee not found: " + id);
        return e;
    }

    @Override
    public Employee create(Employee req) {
        UUID id = Optional.ofNullable(req.getUuid()).orElse(UUID.randomUUID());
        EmployeeImp e = new EmployeeImp();
        e.setUuid(id);
        e.setFirstName(req.getFirstName());
        e.setLastName(req.getLastName());
        e.setFullName(req.getFullName());
        e.setEmail(req.getEmail());
        e.setJobTitle(req.getJobTitle());
        e.setSalary(req.getSalary());
        e.setAge(req.getAge());
        e.setContractHireDate(req.getContractHireDate() != null ? req.getContractHireDate() : Instant.now());
        e.setContractTerminationDate(req.getContractTerminationDate());
        store.put(id, e);
        return e;
    }

    private void seed(String fn, String ln, String email, String title, int salary, int age) {
        EmployeeImp e = new EmployeeImp();
        e.setUuid(UUID.randomUUID());
        e.setFirstName(fn);
        e.setLastName(ln);
        e.setEmail(email);
        e.setJobTitle(title);
        e.setSalary(salary);
        e.setAge(age);
        e.setContractHireDate(Instant.now().minusSeconds(86_400L * 365));
        e.setContractTerminationDate(null);
        store.put(e.getUuid(), e);
    }
}
