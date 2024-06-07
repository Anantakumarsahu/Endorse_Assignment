package com.fabHotel.skillapp.Service;

import com.fabHotel.skillapp.Repository.DepartmentRepository;
import com.fabHotel.skillapp.Entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return departmentRepository.save(department);
    }
}
