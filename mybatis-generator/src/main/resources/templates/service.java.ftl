package ${packageName}.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${packageName}.form.${entity}.Query${Entity}form;
import ${packageName}.form.${entity}.Save${Entity}form;
import ${packageName}.vo.${entity}.Query${Entity}Vo;
import ${packageName}.vo.${entity}.Query${Entity}DetailVo;

public interface I${Entity}Service {

    void save(Save${Entity}Form form);

    PageResp<Query${Entity}Vo> page(Query${Entity}Form form);

    Query${Entity}DetailVo detail(Long id);

    void delete(Long id);
}
