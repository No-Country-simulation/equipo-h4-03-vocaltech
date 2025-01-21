package com.vocaltech.api.domain.entrepreneurs;

import com.vocaltech.api.domain.products.ProductResponseDTO;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record EntrepreneurResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        Entrepreneur.EntrepreneurType type,
        String description,
        Boolean MVP,
        Entrepreneur.ProductToDevelop productToDevelop,
        Boolean hireJunior,
        Boolean moreInfo,
        Set<ProductResponseDTO> products
) {

       public static EntrepreneurResponseDTO fromEntity(Entrepreneur entrepreneur) {
        return new EntrepreneurResponseDTO(
                entrepreneur.getEntrepreneurId(),
                entrepreneur.getName(),
                entrepreneur.getEmail(),
                entrepreneur.getPhone(),
                entrepreneur.getType(),
                entrepreneur.getDescription(),
                entrepreneur.getMVP(),
                entrepreneur.getProductToDevelop(),
                entrepreneur.getHireJunior(),
                entrepreneur.getMoreInfo(),
                entrepreneur.getProducts()
                        .stream()
                        .map(ProductResponseDTO::new)
                        .collect(Collectors.toSet())
        );

    }


}
