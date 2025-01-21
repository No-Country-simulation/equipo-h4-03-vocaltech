package com.vocaltech.api.domain.companies;

import com.vocaltech.api.domain.entrepreneurs.Entrepreneur;
import com.vocaltech.api.domain.entrepreneurs.EntrepreneurResponseDTO;
import com.vocaltech.api.domain.products.ProductResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record CompanyResponseDTO(
        UUID id,
        String companyName,
        String sector,
        Company.CompanySize size,
        String contactName,
        String email,
        String phone,
        String description,
        Boolean MVP,
        Company.DevelopmentStage developmentStage,
        Boolean hireJunior,
        List<String> talentProfile,
        Boolean moreInfo,
        Set<ProductResponseDTO> products
) {
    public static CompanyResponseDTO fromEntity(Company company) {
        return new CompanyResponseDTO(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getSector(),
                company.getSize(),
                company.getContactName(),
                company.getContactEmail(),
                company.getContactPhone(),
                company.getDescription(),
                company.getMVP(),
                company.getDevelopmentStage(),
                company.getHireJunior(),
                company.getTalentProfile(),
                company.getMoreInfo(),
                company.getProducts()
                        .stream()
                        .map(ProductResponseDTO::new)
                        .collect(Collectors.toSet())
        );

    }
}
