package com.onegateafrica.dto;

import com.onegateafrica.Entity.QualityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QualityPostDto {

        private QualityEntity qualityEntity;
        private String imageUri;

}
