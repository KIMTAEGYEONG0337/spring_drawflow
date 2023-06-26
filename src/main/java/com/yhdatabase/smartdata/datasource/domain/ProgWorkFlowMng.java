package com.yhdatabase.smartdata.datasource.domain;

import com.yhdatabase.smartdata.datasource.repository.ProgWorkFlowMngRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cglib.core.Local;
import sun.security.x509.AttributeNameEnumeration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ProgWorkFlowMng {
    private Long flowId;
    private Long progId;
    private int flowSeq;
    private String flowType;
    private JSONObject flowAttr;
    private LocalDateTime crtdDttm;
    private LocalDateTime updtdttm;
    private LocalDateTime dltDttm;

    public ProgWorkFlowMng(Long flowId, Long progId, int flowSeq, String flowType, JSONObject flowAttr,
                                     LocalDateTime crtdDttm, LocalDateTime updtdttm, LocalDateTime dltDttm) {
        this.flowId = flowId;
        this.progId = progId;
        this.flowSeq = flowSeq;
        this.flowType = flowType;
        this.flowAttr = flowAttr;
        this.crtdDttm = crtdDttm;
        this.updtdttm = updtdttm;
        this.dltDttm = dltDttm;
    }

    public String findSql() { return this.flowAttr.getString("sql"); }

    public String findOrFilter() { return this.flowAttr.getString("orFilter").toLowerCase(); }

    public List<String> findColInfo() {
        List<String> colList = new ArrayList<>();

        JSONArray colJsonArray = (JSONArray) this.flowAttr.get("col_info");

        for(Object s : colJsonArray) {
            colList.add(s.toString());
        }

        return colList;
    }

    public Map<String, String[]> findCondList(List<String> colList) {
        Map<String, String[]> filterCondList = new HashMap<>();

        for(int i = 0; i < colList.size(); i++) {
            JSONArray jsonCondInfo = (JSONArray) this.flowAttr.get(colList.get(i));

            String[] condInfo = new String[jsonCondInfo.length()];
            for(int j = 0 ; j < jsonCondInfo.length(); i++) {
                condInfo[j] = jsonCondInfo.get(j).toString();
            }

            filterCondList.put(colList.get(i), condInfo);
        }

        return filterCondList;
    }

    public String findType() { return this.flowAttr.getString("type"); }

    public List<String> findPk() {
        List<String> pk = new ArrayList<>();

        JSONArray colJsonArray = (JSONArray) this.flowAttr.get("pk");

        for(Object s : colJsonArray) {
            pk.add(s.toString());
        }

        return pk;
    }

    public String findTableName() { return this.flowAttr.getString("table_name"); }
}
