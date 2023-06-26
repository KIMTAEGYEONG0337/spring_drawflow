package com.yhdatabase.smartdata.datasource.service;


import com.yhdatabase.smartdata.datasource.domain.ProgMst;
import com.yhdatabase.smartdata.datasource.repository.ProgMstRepository;
import com.yhdatabase.smartdata.datasource.repository.dto.ProgMstDto;
import com.yhdatabase.smartdata.datasource.repository.dto.ResponseProgMstDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
//@ComponentScan(basePackages={"com.yhdatabase.smartdata.datasource.repository"})
public class ProgMstService {

    private final ProgMstRepository progMstRepository;

    @Transactional
    public ProgMst save(ProgMst progmst) {
        return progMstRepository.save(progmst);
    }

    @Transactional
    public Optional<ResponseProgMstDto> findById(Long progId) {
        return progMstRepository.findById(progId);
    }

    @Transactional
    public int update(ProgMstDto progMstDto) {
        return progMstRepository.update(progMstDto);
    }

    @Transactional
    public int delete(Long progId) {
        return progMstRepository.delete(progId);}

}
