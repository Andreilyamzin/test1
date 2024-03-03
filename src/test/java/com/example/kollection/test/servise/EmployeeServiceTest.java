package com.example.kollection.test.servise;

import com.example.kollection.test.exceptions.EmployeeAlreadyAddedException;
import com.example.kollection.test.exceptions.EmployeeNotFoundException;
import com.example.kollection.test.exceptions.EmployeeStorageIsFullException;
import com.example.kollection.test.model.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void testAdd() {
        employeeService.add("test", "testtest", 5_000, 1);
        employeeService.add("TEsTTEST", "TeSteStTest", 2_000, 3);

        var actual1 = employeeService.find("test", "TESTTEST");
        assertThat(actual1).isNotNull();
        assertThat(actual1.getFirstName()).isEqualTo("test");
        assertThat(actual1.getLastName()).isEqualTo("testtest");
        assertThat(actual1.getDepartment()).isEqualTo(1);
        assertThat(actual1.getSalary()).isEqualTo(5_000);

        var actual2 = employeeService.find("TEsTTEST", "TeSteStTest");
        assertThat(actual2).isNotNull();
        assertThat(actual2.getFirstName()).isEqualTo("TEsTTEST");
        assertThat(actual2.getLastName()).isEqualTo("TeSteStTest");
        assertThat(actual2.getDepartment()).isEqualTo(3);
        assertThat(actual2.getSalary()).isEqualTo(2_000);
    }

    @Test
    void testAddDuplicate() {
        employeeService.add("test", "testtest", 5_000, 1);
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.add("test", "testtest", 3_000, 1));
    }

    @Test
    void testMax() {
        employeeService.add("test1", "testtest", 5_000, 1);
        employeeService.add("test2", "testtest", 5_000, 1);
        employeeService.add("test3", "testtest", 5_000, 1);
        employeeService.add("test4", "testtest", 5_000, 1);
        employeeService.add("test5", "testtest", 5_000, 1);
        employeeService.add("test6", "testtest", 5_000, 1);
        employeeService.add("test7", "testtest", 5_000, 1);
        employeeService.add("test8", "testtest", 5_000, 1);
        employeeService.add("test9", "testtest", 5_000, 1);
        employeeService.add("test10", "testtest", 5_000, 1);
        assertThrows(EmployeeStorageIsFullException.class, () -> employeeService.add("test", "testtest", 5_000, 1));
    }

    @Test
    void testGetAll() {
        employeeService.add("test1", "testtest", 5_000, 1);
        employeeService.add("test2", "testtest", 2_000, 3);
        var actual = employeeService.getAll();
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("test1", "testtest", 5_000, 1),
                new Employee("test2", "testtest", 2_000, 3)

        );
    }

    @Test
    void testNotFound() {
    assertThrows(EmployeeNotFoundException.class,() -> employeeService.find("wqe","fasf"));
    }

    @Test
    void testRemove() {
    assertThrows(EmployeeNotFoundException.class,() -> employeeService.remove("wqe","fasf",1_000,1));
        employeeService.add("testus", "testtest", 1_000, 1);
        employeeService.add("testis", "testtest", 2_000, 2);
        var actual = employeeService.find("testus", "testtest");
        assertThat(actual).isNotNull();
        employeeService.remove("testus", "testtest", 2_000, 2);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.find("testus", "testtest"));


    }

}