package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeSeqIsLongTermVO {

    private Long imageId;
    private int sequence;
    private int isLongTerm;

}
