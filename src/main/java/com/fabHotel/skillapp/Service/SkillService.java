package com.fabHotel.skillapp.Service;

import com.fabHotel.skillapp.Repository.DepartmentRepository;
import com.fabHotel.skillapp.Entity.Department;
import com.fabHotel.skillapp.Entity.Skill;
import com.fabHotel.skillapp.Repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Skill createSkill(String name, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found"));
        Skill skill = new Skill();
        skill.setName(name);
        skill.setDepartment(department);
        return skillRepository.save(skill);
    }
}
