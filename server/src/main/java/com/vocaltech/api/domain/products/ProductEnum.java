package com.vocaltech.api.domain.products;

import java.util.UUID;

public enum ProductEnum {
    LIDERAR_VOZ("68b0db22-378c-423a-8544-f17ec2b8fa8e", "Liderar a través de la voz"),
    ENTRENAMIENTO_PERSONALIZADO("a7a1c248-d96b-487a-8818-27d738ef069e", "Entrenamiento personalizado"),
    CAPACITACION_EMPRESAS("112890f8-d4e0-476d-9cf6-c3608c49f273", "Capacitación para empresas"),
    COACHING_INDIVIDUAL("2e32833b-66f7-4b15-9dcf-0ea947ca35b1", "Coaching individual"),
    FORTALECER_VOZ_EMPRESA("2e61d702-9d05-482c-aa05-077ad548a5fb", "Fortalecer la voz de la empresa"),
    CHARLAS_INSPIRADORAS("b17b9e6b-369e-4008-88be-728cfac8460e", "Charlas inspiradoras"),
    PITCH_VENTAS("d38a4799-909f-4c6f-9962-1e00eda29dac", "Pitch de ventas"),
    STORYTELLING("ef796031-dc54-45b2-87c5-7912ae32add0", "Storytelling"),
    TECNICAS_VOCALES("13e85176-2970-4a53-a1bb-ede0298408ed", "Técnicas vocales"),
    MVP("98f07096-c649-4151-a67f-1b80cb5f2964", "MVP"),
    CONTRATACION_IT_JUNIOR("32d2020f-9b1c-4eb6-b4ee-bd1b3803f558", "Contratación de talento IT junior");

    private final UUID id;
    private final String name;

    ProductEnum(String id, String name) {
        this.id = UUID.fromString(id);
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ProductEnum fromId(UUID id) {
        for (ProductEnum product : values()) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        throw new IllegalArgumentException("No se encontró un servicio con el ID proporcionado: " + id);
    }

    public static ProductEnum fromName(String name) {
        for (ProductEnum product : values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException("No se encontró un servicio con el nombre proporcionado: " + name);
    }
}
