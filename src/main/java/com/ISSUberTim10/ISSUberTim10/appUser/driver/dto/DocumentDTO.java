package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String name;
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
