package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @Size(max = 100, message = "Field (name) cannot be longer than 100 characters!")
    private String name;
    @NotNull(message = "Field (documentImage) is required!")
    private String documentImage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer driverId;

    public DocumentDTO(Document document) {
        this.id = Math.toIntExact(document.getId());
        this.name = document.getTitle();
        this.documentImage = document.getImage();
        this.driverId = Math.toIntExact(document.getDriver().getId());
    }
}
