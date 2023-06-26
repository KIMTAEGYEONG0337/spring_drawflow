package com.yhdatabase.smartdata.datasource.service;

import com.yhdatabase.smartdata.datasource.domain.ProgWorkFlowMng;
import com.yhdatabase.smartdata.datasource.repository.ProgWorkFlowMngRepository;
import com.yhdatabase.smartdata.datasource.repository.dto.ProgWorkFlowMngDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgWorkFlowMngService {
    private final ProgWorkFlowMngRepository progWorkFlowMngRepository;

    @Transactional
    public ProgWorkFlowMng save(ProgWorkFlowMng progWorkFlowMng) {
        return progWorkFlowMngRepository.save(progWorkFlowMng);
    }

    @Transactional
    public int update(ProgWorkFlowMngDto progWorkFlowMngDto) {
        return progWorkFlowMngRepository.update(progWorkFlowMngDto);
    }

    @Transactional
    public int delete(Long progId) {
        return progWorkFlowMngRepository.delete(progId);
    }

    @Transactional
    public Optional<ProgWorkFlowMng> findById(Long flowId) { return progWorkFlowMngRepository.findById(flowId); }

    @Transactional
    public List<String> getTables() { return progWorkFlowMngRepository.getTables(); }

    @Transactional
    public List<String> getTableCols(String table_name) { return progWorkFlowMngRepository.getTableCols(table_name); }
}
