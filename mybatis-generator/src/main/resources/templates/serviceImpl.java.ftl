import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import ${packageName}.form.${entity}.Query${Entity}form;
import ${packageName}.form.${entity}.Save${Entity}form;
import ${packageName}.vo.${entity}.Query${Entity}Vo;
import ${packageName}.vo.${entity}.Query${Entity}DetailVo;
import java.util.List;

@Service
public class ${Entity}ServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(${Entity}ServiceImpl.class);

    @Resource
    private ${Entity}Mapper ${entity}Mapper;

    void save(Save${Entity}Form form){

    }

    PageResp<Query${Entity}Vo> page(Query${Entity}Form form){

    }

    Query${Entity}DetailVo detail(Long id){

    }

    void delete(Long id){

    }
}