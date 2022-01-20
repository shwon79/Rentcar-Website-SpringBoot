package kr.carz.savecar.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValuesVO {

    private List<ValuesForWebDTO> valuesList;

}
