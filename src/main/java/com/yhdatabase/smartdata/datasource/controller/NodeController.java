package com.yhdatabase.smartdata.datasource.controller;

import com.yhdatabase.smartdata.datasource.domain.ProgWorkFlowMng;
import com.yhdatabase.smartdata.datasource.repository.dto.ProgWorkFlowMngDto;
import com.yhdatabase.smartdata.datasource.repository.dto.ResultDto;
import com.yhdatabase.smartdata.datasource.service.DataProcessService;
import com.yhdatabase.smartdata.datasource.service.OutPutTableService;
import com.yhdatabase.smartdata.datasource.service.ProgWorkFlowMngService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class NodeController {

    private final ProgWorkFlowMngService progWorkFlowMngService;

    private final DataProcessService dataProcessService;

    private final OutPutTableService outPutTableService;

    @PostMapping("/project/save-node/{progId}")
    public Long saveProgWorkFlowMng(@RequestBody ProgWorkFlowMng progWorkFlowMng) {

        ProgWorkFlowMng savedProgWorkFlowMng = progWorkFlowMngService.save(progWorkFlowMng);

        return savedProgWorkFlowMng.getFlowId();
    }

    @PostMapping("/project/update-node/{progId}/{flowId}")
    public int updateNode(@RequestBody ProgWorkFlowMngDto progWorkFlowMngDto) {
        return progWorkFlowMngService.update(progWorkFlowMngDto);
    }

    @PostMapping("/project/delete-node/{progId}/{flowId}")
    public int deleteNode(@PathVariable String flowId) {
        return progWorkFlowMngService.delete(Long.parseLong(flowId));
    }

    @GetMapping("/project/sql-result/{progId}")
    public List<ResultDto> getResult(@PathVariable String progId, @RequestParam(value = "flowSeq") List<String> flowSeq){
        //List<ProgWorkFlowMng> nodeList = progWorkFlowMngService.findByProgId(Long.parseLong(progId));

        List<ProgWorkFlowMng> nodeList = new ArrayList<>();
        for(String s : flowSeq) {
            nodeList.add(progWorkFlowMngService.findById(Long.parseLong(s)).get());
        }

        List<Map<String, Object>> result = null;
        List<ResultDto> resultDto = new ArrayList<>();

        Long start;
        Long end;
        String sqlTime = "";
        String filterTime = "";
        String outputTime = "";

        for (ProgWorkFlowMng cur : nodeList) {
            String flowType = cur.getFlowType();

            switch(flowType) {
                case "select" :
                    start = System.currentTimeMillis();

                    result = dataProcessService.findSQLResult(Optional.of(cur));

                    end = System.currentTimeMillis();
                    sqlTime += timeDiff(start, end);

                    resultDto.add(new ResultDto(cur.getFlowId(), "select", result.size(), sqlTime));
                    break;
                case "filter" :
                    start = System.currentTimeMillis();

                    result = dataProcessService.filterSQLResult(result, Optional.of(cur));

                    end = System.currentTimeMillis();
                    filterTime += timeDiff(start, end);

                    resultDto.add(new ResultDto(cur.getFlowId(),"filter", result.size(), filterTime));
                    break;
                case "output" :
                    start = System.currentTimeMillis();

                    int resultNum = outPutTableService.processOutputNode(result, cur);

                    end = System.currentTimeMillis();
                    outputTime += timeDiff(start, end);

                    resultDto.add(new ResultDto(cur.getFlowId(),"output", resultNum, outputTime));
                    break;
            }
        }

        System.out.println("SQL node 실행 시간 - " + sqlTime);
        System.out.println("Filter node 실행 시간 - " + filterTime);
        System.out.println("Output node 실행 시간 - " + outputTime);

        //필터노드 정보까지만 있음.
        // 필터 노드를 insert/update/delete 처리하여, 다른 테이블에 옮긴 정보들은 옮긴 디비 테이블에서 확인 가능. 여기서는 몇개 처리됬는지만 반환.
        /*System.out.println("컨트롤러 후");
        for (Map<String, Object> row : result) {
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }*/


        return resultDto;
    }

    @GetMapping("project/get-tables")
    public List<String> getTables(){
        return progWorkFlowMngService.getTables();
    }

    @GetMapping("project/{table_name}")
    public List<String> getTableCols(@PathVariable String table_name){
        return progWorkFlowMngService.getTableCols(table_name);
    }

    public String timeDiff(Long start, Long end) {
        long executionTimeMillis = end - start;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(executionTimeMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(executionTimeMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(executionTimeMillis);

        return "실행 시간: " + hours + "시간 " + minutes + "분 " + seconds + "초";
    }
}
