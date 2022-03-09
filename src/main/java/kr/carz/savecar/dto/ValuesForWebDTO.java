package kr.carz.savecar.dto;

import kr.carz.savecar.domain.ValuesForWeb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValuesForWebDTO {

    private String title;
    private String value;

    public ValuesForWeb toEntity() {
        return ValuesForWeb.builder()
                .title(title)
                .value(value)
                .build();

    }
}
