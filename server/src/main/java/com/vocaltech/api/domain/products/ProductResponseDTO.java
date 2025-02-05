package com.vocaltech.api.domain.products;

import java.util.UUID;

public record ProductResponseDTO(
        UUID productId,
        String name,
        String description
) {
    public ProductResponseDTO(Product product) {
        this(
                product.getProductId(),
                product.getName(),
                product.getDescription());
    }
}
