package com.example.spring_boot_restcontroller.service.Impl;

import com.example.spring_boot_restcontroller.converter.course.CourseConverterRequest;
import com.example.spring_boot_restcontroller.converter.course.CourseConverterResponse;
import com.example.spring_boot_restcontroller.dto.request.CourseRequest;
import com.example.spring_boot_restcontroller.dto.response.CourseResponse;
import com.example.spring_boot_restcontroller.service.CourseService;
import com.example.spring_boot_restcontroller.entity.Company;
import com.example.spring_boot_restcontroller.entity.Course;
import com.example.spring_boot_restcontroller.repository.CompanyRepository;
import com.example.spring_boot_restcontroller.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    private final CourseConverterRequest courseConvertRequest;
    private final CourseConverterResponse courseConvertResponse;

    @Override
    public List<CourseResponse> getAllCourse() {
        return courseConvertResponse.getAll(courseRepository.findAll());
    }

    @Override
    public List<CourseResponse> getAllCourse(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        return courseConvertResponse.getAll(company.getCourses());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id).get();
        return courseConvertResponse.create(course);
    }

    @Override
    public CourseResponse saveCourse(Long companyId, CourseRequest courseRequest) throws IOException {
        Company company = companyRepository.findById(companyId).get();
        Course course = courseConvertRequest.create(courseRequest);
        company.addCourse(course);
        course.setCompany(company);
        return courseConvertResponse.create(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id).get();
        courseConvertRequest.update(course, courseRequest);
        return courseConvertResponse.create(courseRepository.save(course));
    }

    @Override
    public CourseResponse deleteCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        courseRepository.delete(course);
        return courseConvertResponse.create(course);
    }
}
