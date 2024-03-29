package com.ssafy.ibalance.diary.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DiaryMenuResponse {
    private String menuId;
    private String menuName;
    private List<String> menuMaterial;
    private String menuImgUrl;
}
