package com.example.kollection.test.servise;

import static org.junit.jupiter.api.Assertions.*;

import com.example.kollection.test.exceptions.EmployeeNotFoundException;
import com.example.kollection.test.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class DepartmentServiceTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        var employees = List.of(
                new Employee("test", "test1", 1_000, 1),
                new Employee("test2", "test2", 10_000, 1),
                new Employee("test3", "test3", 2_000, 2),
                new Employee("test4", "test4", 20_000, 2),
                new Employee("test5", "test5", 200_000, 2),
                new Employee("test6", "test6", 300_000, 3)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    void testDepartmentMaxSalary() {

        assertThat(departmentService.findMaxSalary(1)).isEqualTo(new Employee("test2",
                "test2", 10_000, 1));
        assertThat(departmentService.findMaxSalary(3)).isEqualTo(new Employee("test6",
                "test6", 300_000, 3));
        assertThrows(EmployeeNotFoundException.class, () -> departmentService.findMaxSalary(1111));
    }
    @Test
    void testDepartmentMinSalary() {
        var actual1 = departmentService.findMinSalary(1);
        assertThat(actual1.isPresent()).isTrue();
        assertThat(departmentService.findMinSalary(1)).isEqualTo(new Employee("test2",
                "test2", 10_000, 1));

        var actual2 = departmentService.findMinSalary(3);
        assertThat(actual2.isPresent()).isTrue();
        assertThat(actual2.get()).isEqualTo(new Employee("test6",
                "test6", 300_000, 3));
        var actual3 = departmentService.findMinSalary(1111);
        assertThat(actual3.isEmpty()).isTrue();
    }

    @Test
    void testByDepartment() {

        var actual = departmentService.findByDepartment(2);
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("test3", "test3", 2_000, 2),
                new Employee("test4", "test5", 20_000, 2),
                new Employee("test5", "test5", 200_000, 2));
        var actual2 = departmentService.findByDepartment(21111);
        assertThat(actual2).isEmpty();
    }

    @Test
    void testGroupByDepartment() {
        var actual = departmentService.groupByDepartment();

        var expected = Map.of(
                1,List.of(new Employee("test", "test1", 1_000, 1),
                        new Employee("test2", "test2", 10_000, 1),
                        2, List.of(new Employee ("test3", "test3", 2_000, 2),
                                new Employee("test4", "test4", 20_000, 2)),
                        new Employee("test5", "test5", 200_000, 2)),
                3, List.of(new Employee("test6", "test6", 300_000, 3)));
        assertThat(actual).isEqualTo(expected);

    }

}