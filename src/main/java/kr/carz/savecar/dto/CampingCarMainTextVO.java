package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarMainText;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingCarMainTextVO {

    private List<CampingCarMainTextTitleVO> campingCarMainTextTitleList;

}