package com.yhdatabase.smartdata.datasource.repository;

import com.yhdatabase.smartdata.datasource.domain.ProgMst;
import com.yhdatabase.smartdata.datasource.repository.dto.ProgMstDto;
import com.yhdatabase.smartdata.datasource.repository.dto.ResponseProgMstDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

@Slf4j
public class ProgMstRepository {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public ProgMstRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("prog_mst")
                .usingGeneratedKeyColumns("prog_id");
    }

    public ProgMst save(ProgMst progMst) {
        progMst.setCrtdDttm(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(progMst);
        Number key = jdbcInsert.executeAndReturnKey(param);

        progMst.setProgId(key.longValue());

        return progMst;
    }

    public Optional<ResponseProgMstDto> findById(Long progId) {
        String sql = "select * from prog_mst where prog_id = :progId";

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("progId", progId);

            ResponseProgMstDto responseProgMstDto = template.queryForObject(sql, param, ResponseProgMstDtoRowMapper());
            return Optional.of(responseProgMstDto);
        } catch (EmptyResultDataAccessException e) {
            log.info("Optional<progMst>.empty(), prog_is={}", progId);
            return Optional.empty();
        }
    }

    public int update(ProgMstDto progMstDto) {
        String sql = "update prog_mst " + "" +
                "set prog_nm=:progNm, prog_desc=:progDesc, view_attr=:viewAttr, use_yn=:useYn, updt_dttm=:updtDttm " +
                "where prog_id=:progId";

        progMstDto.setUpdtDttm(LocalDateTime.now());

        System.out.println(progMstDto.getProgNm() + "\n");
        System.out.println(progMstDto.getProgDesc() + "\n");
        System.out.println(progMstDto.getViewAttr() + "\n");
        System.out.println(progMstDto.getUseYn() + "\n");
        System.out.println(progMstDto.getUpdtDttm() + "\n");
        System.out.println(progMstDto.getProgId() + "\n");

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progNm", progMstDto.getProgNm())
                .addValue("progDesc", progMstDto.getProgDesc())
                .addValue("viewAttr", progMstDto.getViewAttr(), Types.OTHER)
                .addValue("useYn", progMstDto.getUseYn())
                .addValue("updtDttm", progMstDto.getUpdtDttm())
                .addValue("progId", progMstDto.getProgId());

        return template.update(sql, param);
    }

    public int delete(Long progId) {
        String sql = "delete from prog_mst where prog_id = :progId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progId", progId);

        return template.update(sql, param);
    }

    private RowMapper<ResponseProgMstDto> ResponseProgMstDtoRowMapper() {
        return BeanPropertyRowMapper.newInstance(ResponseProgMstDto.class);
    }

    private RowMapper<ProgMst> progMstRowMapper() {
        return ((rs, rowNum) -> {
            ProgMst progMst = new ProgMst();
            progMst.setProgId(rs.getLong("prog_id"));
            progMst.setProgNm(rs.getString("prog_nm"));
            progMst.setProgDesc(rs.getString("prog_desc"));
            progMst.setViewAttr(new JSONObject(rs.getString("view_attr")));
            progMst.setCrtdDttm(rs.getObject("crtd_dttm", LocalDateTime.class));
            progMst.setUpdtDttm(rs.getObject("updt_dttm", LocalDateTime.class));
            progMst.setDltDttm(rs.getObject("dlt_dttm", LocalDateTime.class));
            return progMst;
        });
    }
}
