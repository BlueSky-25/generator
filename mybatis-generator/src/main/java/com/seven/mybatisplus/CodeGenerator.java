package com.seven.mybatisplus;

import com.seven.mybatisplus.utils.DbUtil;
import com.seven.mybatisplus.utils.Field;
import com.seven.mybatisplus.utils.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @className: CodeGenerator
 * @author: wanyang
 * @date: 2025/6/18 13:42
 * @version: 1.0
 * @description: TODO
 */
public class CodeGenerator {

    private static String basePath = "src/main/java/";
    private static String pomPath = "mybatis-generator/pom.xml";
    private static String configFilePath = "config.properties";
    static Properties properties = null;

    static {
        try {
            properties = getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String outputDir = properties.getProperty("generator.outputDir");
        File outDirFile = new File(outputDir);
        if (!outDirFile.exists()) {
            outDirFile.mkdirs();
        }

    }

    public static void main(String[] args) throws Exception {
        // 获取mybatis-generator
        String generatorPath = getGeneratorPath();

        //初始化数据库配置
        DbUtil.init(properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password"));

        // 读取table节点
        Document document = new SAXReader().read("mybatis-generator/" + generatorPath);
        List<Node> nodes = document.selectNodes("//table");
        for (Node table : nodes) {
            Node tableName = table.selectSingleNode("@tableName");
            Node domainObjectName = table.selectSingleNode("@domainObjectName");
            // 示例：表名 test
            // Entity = Test
            String Entity = domainObjectName.getText();
            // entity = test
            String entity = Entity.substring(0, 1).toLowerCase() + Entity.substring(1);
            // entity_ = t_test
            String _entity = tableName.getText().replaceAll("_", "-");
            // 表中文名
            String tableNameCn = DbUtil.getTableComment(tableName.getText());
            List<Field> fieldList = DbUtil.getColumnByTableName(tableName.getText());
            Set<String> typeSet = getJavaTypes(fieldList);

            // 组装参数
            Map<String, Object> param = new HashMap<>();
            param.put("Entity", Entity);
            param.put("entity", entity);
            param.put("_entity", _entity);
            param.put("tableNameCn", tableNameCn);
            param.put("fieldList", fieldList);
            param.put("typeSet", typeSet);
            param.put("packageName", properties.get("generator.packageName"));
            param.put("author", properties.get("generator.author"));

            gen(Entity, param, "controller", "controller");
            gen(Entity, param, "service", "service");
            gen(Entity, param, "form/" + entity, "saveForm");
            gen(Entity, param, "form/" + entity, "queryForm");
            gen(Entity, param, "vo/" + entity, "queryVo");
        }
    }

    private static void gen(String Domain, Map<String, Object> param, String packageName, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".java.ftl");
        String toPath = (String) properties.get("generator.outputDir") +
                ((String) properties.get("generator.packageName")).replaceAll("\\.", "/") +
                "/" +
                packageName + "/";
        new File(toPath).mkdirs();
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        String fileName = toPath + Domain + Target + ".java";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        return node.getText();
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }

    private static Properties getProperties() throws IOException {
        InputStream inputStream = CodeGenerator.class.getClassLoader().getResourceAsStream(configFilePath);
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}
