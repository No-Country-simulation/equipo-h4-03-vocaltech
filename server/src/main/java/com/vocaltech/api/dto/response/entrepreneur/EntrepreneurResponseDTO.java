package com.vocaltech.api.dto.response.entrepreneur;

import com.vocaltech.api.model.Entrepreneur;
import java.util.UUID;

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
        Boolean moreInfo
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
                entrepreneur.getMoreInfo()
        );
    }


}
