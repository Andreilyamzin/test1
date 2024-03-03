package com.example.kollection.test.servise;




import com.example.kollection.test.exceptions.EmployeeAlreadyAddedException;
import com.example.kollection.test.exceptions.EmployeeNotFoundException;
import com.example.kollection.test.exceptions.EmployeeStorageIsFullException;
import com.example.kollection.test.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
@Service
public class EmployeeService {
    private static final int MAX_COUNT = 10;
    private final Map<String, Employee> employees = new HashMap<>(MAX_COUNT);
    public void add(String firstName, String lastName, int salary, int department) throws EmployeeAlreadyAddedException {
        if (employees.size() >= MAX_COUNT) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName, salary, department);
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(key, employee);
    }
    public void remove(String firstName, String lastName, int salary, int department) {
        var employee = new Employee(firstName, lastName,salary,department);
        var key = makeKey(firstName, lastName);
        var removed = employees.remove(key);
        if (removed != null) {
            throw new EmployeeAlreadyAddedException();
        }
    }
    public Employee find (String firstName, String lastName){
        var key = makeKey(firstName, lastName);
        var employee = employees.get(key);
        if (employee != null) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }
    public Collection<Employee> getAll () {
        return Collections.unmodifiableCollection(employees.values());
    }
    private static String makeKey(String firstName, String lastName) {
        return (firstName + "-" + lastName).toLowerCase();
    }


}