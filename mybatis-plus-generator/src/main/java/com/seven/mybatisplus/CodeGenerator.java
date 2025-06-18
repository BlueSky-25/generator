package com.seven.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.seven.mybatisplus.dto.ConfigDto;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.Types;

/**
 * @className: CodeGenerator
 * @author: wanyang
 * @date: 2025/6/18 11:19
 * @version: 1.0
 * @description: TODO
 */
public class CodeGenerator {
    public static void main(String[] args) throws Exception {
        // 加载配置信息
        InputStream inputStream = CodeGenerator.class.getClassLoader().getResourceAsStream("config.yaml");
        Yaml yaml = new Yaml();
        ConfigDto configDto = yaml.loadAs(inputStream, ConfigDto.class);

        FastAutoGenerator.create(configDto.getDatasource().getUrl(), configDto.getDatasource().getUsername(), configDto.getDatasource().getPassword())
                .globalConfig(builder -> builder
                        .author(configDto.getGenerator().getAuthor())
                        .outputDir(configDto.getGenerator().getOutputDir() + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent(configDto.getGenerator().getPackageName())
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            if (typeCode == Types.DATE ||
                                    typeCode == Types.TIMESTAMP ||
                                    typeCode == Types.TIME) {
                                // 自定义类型转换
                                return DbColumnType.DATE;
                            }

                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .strategyConfig(builder -> {
                            //builder.addTablePrefix("").addTablePrefix();
                            builder.addInclude(configDto.getGenerator().getTableNames());
                            builder.entityBuilder()
                                    .enableLombok()
                                    .enableFileOverride()
                                    .enableChainModel()
                                    .enableRemoveIsPrefix()
                                    .enableSerialAnnotation()
                                    .enableTableFieldAnnotation()
                                    .columnNaming(NamingStrategy.underline_to_camel)
                                    //.superClass()
                                    //.addSuperEntityColumns("")
                                    .javaTemplate("/templates/entity.java")
                            ;
                            builder.serviceBuilder()
                                    .enableFileOverride()
                                    .serviceTemplate("/templates/service.java")
                                    .serviceImplTemplate("/templates/serviceImpl.java")
                            ;
                            builder.mapperBuilder()
                                    .enableFileOverride()
                                    .mapperTemplate("/templates/mapper.java")
                                    .mapperXmlTemplate("/templates/mapper.xml")
                            ;
                        }
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
