package com.yhdatabase.smartdata.datasource.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    Long nodeId;
    String nodeType;

    int resultNum;
    String timeDiff;

    public ResultDto(Long nodeId, String nodeType, int resultNum, String timeDiff) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.resultNum = resultNum;
        this.timeDiff = timeDiff;
    }

    public  ResultDto() {

    }
}
