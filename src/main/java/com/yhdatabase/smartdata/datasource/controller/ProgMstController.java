package com.yhdatabase.smartdata.datasource.controller;

import com.yhdatabase.smartdata.datasource.domain.ProgMst;
import com.yhdatabase.smartdata.datasource.repository.dto.ProgMstDto;
import com.yhdatabase.smartdata.datasource.repository.dto.ResponseProgMstDto;
import com.yhdatabase.smartdata.datasource.service.ProgMstService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class ProgMstController {

    private final ProgMstService progMstService;

    @PostMapping("/project")
    public Long saveProgMst(@RequestBody ProgMst progMst) {
        ProgMst savedProgMst = progMstService.save(progMst);

        return savedProgMst.getProgId();
    }

    @GetMapping("/project/load/{progId}")
    public Optional<ResponseProgMstDto> loadProgMst(@PathVariable String progId) {
        return progMstService.findById(Long.parseLong(progId));
    }

    @PostMapping("/project/update/{progId}")
    public String updateProgMst(@RequestBody ProgMstDto progMstDto) {
        if (progMstService.update(progMstDto) > 0) {
            System.out.println("success");
            return "success";
        }
        else {
            System.out.println("fail" + progMstDto);
            return "fail";
        }
    }

    @PostMapping("/project/delete/{progId}")
    public String deleteProgMst(@PathVariable String progId) {
        if (progMstService.delete(Long.parseLong(progId)) > 0) {
            System.out.println("success");
            return "success";
        }
        else
            return "fail";
    }
}
