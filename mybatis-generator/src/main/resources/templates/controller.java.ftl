package packageName.controller.admin;

import java.lang.Void;
import com.jiawa.train.common.resp.PageResp;
import ${packageName}.form.${Entity}.Query${Entity}Form;
import ${packageName}.form.${Entity}.Save${Entity}Form;
import ${packageName}.vo.${Entity}.Query${Entity}Vo;
import ${packageName}.service.${Entity}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${entity}")
public class ${Entity}AdminController {

    @Resource
    private ${Entity}Service ${entity}Service;

    @PostMapping("/save")
    public void save(@Valid @RequestBody Save${Entity}form from) {
        ${entity}Service.save(from);
    }

    @GetMapping("/page")
    public PageResp<Query${Entity}Vo> page(@Valid Query${Entity}Form form) {
        PageResp<Query${Entity}Vo> list = ${entity}Service.page(form);
        return list;
    }

    @GetMapping("/detail/{id}")
    public Query${Entity}DetailVo detail(@PathVariable Long id) {
        return ${entity}Service.detail(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        ${entity}Service.delete(id);
    }

}
