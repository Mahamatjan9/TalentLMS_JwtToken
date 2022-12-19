package com.example.spring_boot_restcontroller.service.Impl;

import com.example.spring_boot_restcontroller.converter.company.CompanyConverterRequest;
import com.example.spring_boot_restcontroller.converter.company.CompanyConverterResponse;
import com.example.spring_boot_restcontroller.dto.request.CompanyRequest;
import com.example.spring_boot_restcontroller.dto.response.CompanyResponse;
import com.example.spring_boot_restcontroller.service.CompanyService;
import com.example.spring_boot_restcontroller.entity.Company;
import com.example.spring_boot_restcontroller.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverterRequest companyConverterRequest;
    private final CompanyConverterResponse companyConverterResponse;

    @Override
    public List<CompanyResponse> getAllCompany() {
        return companyConverterResponse.getAll(companyRepository.findAll());
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id).get();
        return companyConverterResponse.create(company);
    }

    @Override
    public CompanyResponse saveCompany(CompanyRequest companyRequest) {
        Company company = companyConverterRequest.create(companyRequest);
        companyRepository.save(company);
        return companyConverterResponse.create(company);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).get();
        companyConverterRequest.update(company, companyRequest);
        return companyConverterResponse.create(companyRepository.save(company));
    }

    @Override
    public CompanyResponse deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id).get();
        companyRepository.delete(company);
        return companyConverterResponse.create(company);
    }
}
