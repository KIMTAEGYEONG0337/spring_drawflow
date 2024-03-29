package com.yhdatabase.smartdata.datasource.service;

import com.yhdatabase.smartdata.datasource.domain.ProgWorkFlowMng;
import com.yhdatabase.smartdata.datasource.repository.DataProcessRepository;
import com.yhdatabase.smartdata.datasource.repository.ProgWorkFlowMngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataProcessService {
    private final DataProcessRepository dataProcessRepository;

    @Transactional
    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {
        List<Map<String, Object>> result = dataProcessRepository.findSQLResult(progWorkFlowMng);

        return result;
    }

    public List<Map<String, Object>> filterSQLResult(List<Map<String, Object>> sqlResult, Optional<ProgWorkFlowMng> progWorkFlowMng) {

        List<Map<String, Object>> result = new ArrayList<>();
        List<String> colList = progWorkFlowMng.get().findColInfo();
        Map<String, String[]> filterCondList = progWorkFlowMng.get().findCondList(colList);
        //and or 판단
        String orFilter = progWorkFlowMng.get().findOrFilter();

        for (Map<String, Object> row : sqlResult) {
            int checkCond = 0;
            int condCnt = 0;
            for( String key : filterCondList.keySet() ){
                String cond = filterCondList.get(key)[0];
                String condValue = filterCondList.get(key)[1];

                switch (cond) {
                    case ">":
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) > Integer.parseInt(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "<" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) < Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case ">=" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) >= Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case "<=" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) <= Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case "=" :
                        condCnt++;
                        if (row.get(key).toString().equals(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "LIKE":
                        condCnt++;
                        if (row.get(key).toString().contains(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "NOT LIKE":
                        condCnt++;
                        if (!row.get(key).toString().contains(condValue)) {
                            checkCond++;
                        }
                        break;
                }
            }

            if (orFilter.equals("or")) {
                if(checkCond > 0)
                    result.add(row);
            } else if (orFilter.equals("and")){
                if(condCnt == checkCond)
                    result.add(row);
            }
        }

        //filterSQLResult 출력값
        /*System.out.println("filterSQLResult");
        for (Map<String, Object> row : result) {
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }*/

        return result;
    }
}
