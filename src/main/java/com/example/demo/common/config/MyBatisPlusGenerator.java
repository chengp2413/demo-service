package com.example.demo.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Plus Generator
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/24 12:22
 */
public class MyBatisPlusGenerator {
    /**
     * 数据库驱动
     */
    private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    /**
     * 数据库URL
     */
    private static final String JDBC_URL = "jdbc:oracle:thin:@127.0.0.1:1521/orcl";
    /**
     * 数据库用户名
     */
    private static final String JDBC_USERNAME = "chengp";
    /**
     * 数据库密码
     */
    private static final String JDBC_PASSWORD = "chengp";
    /**
     * 待生成的表名（用,分隔）
     */
    private static final String[] TABLES = {"DEMO_TEST"};
    /**
     * 表的SCHEMA，不加到生成的类名中(若表属于当前登录用户，则不需填写)
     */
    private static final String PREFIX = "";
    /**
     * 基础项目路径
     */
    private static final String BASE_PATH = "/src/main/java";
    /**
     * Mapper文件名后缀
     */
    private static final String MAPPER_SUFFIX = "Mapper";
    /**
     * Mapper文件生成路径
     */
    private static final String MAPPER_PATH = "com.example.demo.infrastructure.persistence.mapper";
    /**
     * Mapper.xml文件名后缀
     */
    private static final String XML_SUFFIX = "Mapper";
    /**
     * Mapper.xml文件生成路径
     */
    private static final String XML_PATH = "/src/main/resources/sqlmap";
    /**
     * 实体文件名后缀
     */
    private static final String ENTITY_SUFFIX = "PO";
    /**
     * 实体文件生成路径
     */
    private static final String ENTITY_PATH = "com.example.demo.domain.po";
    /**
     * Repository接口文件名后缀
     */
    private static final String REPO_SUFFIX = "Repo";
    /**
     * Repository接口文件生成路径
     */
    private static final String REPO_PATH = "com.example.demo.domain.repository";
    /**
     * Repository接口实现类文件名后缀
     */
    private static final String REPOIMPL_SUFFIX = "RepoImpl";
    /**
     * Repository接口文件实现类生成路径
     */
    private static final String REPOIMPL_PATH = "com.example.demo.infrastructure.persistence.repoimpl";
    /**
     * Controller文件名后缀（默认不生成）
     */
    private static final String CONTROLLER_SUFFIX = "Controller";
    /**
     * Controller文件生成路径（默认不生成）
     */
    private static final String CONTROLLER_PATH = "";
    /**
     * 作者名
     */
    private static final String AUTHOR = "chengp";


    public static void main(String[] args) {
        //获取当前项目路径
        String projectPath = System.getProperty("user.dir");
        //代码生成器
        AutoGenerator generator = new AutoGenerator();
        //数据库配置
        configDataSource(generator);
        //全局配置
        configGlobal(generator, projectPath);
        //包相关配置
        configPackage(generator);
        //策略配置
        configStrategy(generator);
        //自定义配置
        cofnigCustom(generator, projectPath);
        //模版引擎配置
        configTemplate(generator);
        generator.execute();
    }

    /**
     * 数据库相关配置
     *
     * @param generator generator
     * @author chengp
     * @date 2022/04/26
     */
    private static void configDataSource(AutoGenerator generator) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.ORACLE);
        dataSourceConfig.setDriverName(JDBC_DRIVER);
        dataSourceConfig.setUrl(JDBC_URL);
        dataSourceConfig.setUsername(JDBC_USERNAME);
        dataSourceConfig.setPassword(JDBC_PASSWORD);
        generator.setDataSource(dataSourceConfig);
    }

    /**
     * 全局配置
     *
     * @param generator   generator
     * @param projectPath projectPath
     * @author chengp
     * @date 202204/26
     */
    private static void configGlobal(AutoGenerator generator, String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        //设置文件输出路径
        globalConfig.setOutputDir(projectPath.concat(BASE_PATH));
        //设置作者
        globalConfig.setAuthor(AUTHOR);
        //生成完后是否打开输出目录
        globalConfig.setOpen(false);
        //是否覆盖生成过的已有文件
        globalConfig.setFileOverride(true);
        //是否开启activeRecord模式
        globalConfig.setActiveRecord(false);
        //是否在xml中添加二级缓存配置,默认false
        globalConfig.setEnableCache(false);
        //XML文件返回对象定义ResultMap
        globalConfig.setBaseResultMap(true);
        //XML返回对象字段列表columList
        globalConfig.setBaseColumnList(true);
        //设置主键字段类型
        globalConfig.setIdType(IdType.INPUT);
        //开启swagger2模式,实体属性Swagger2注解,默认false
        globalConfig.setSwagger2(false);
        //生成的文件名定义，%s会自动填充表实体属性
        globalConfig.setMapperName("%s" + MAPPER_SUFFIX);
        globalConfig.setXmlName("%s" + XML_SUFFIX);
        globalConfig.setEntityName("%s" + ENTITY_SUFFIX);
        globalConfig.setServiceName("%s" + REPO_SUFFIX);
        globalConfig.setServiceImplName("%s" + REPOIMPL_SUFFIX);
        globalConfig.setControllerName("%s" + CONTROLLER_SUFFIX);
        generator.setGlobalConfig(globalConfig);
    }

    /**
     * 相关包路径配置
     *
     * @param generator generator
     * @author chengp
     * @date 2022/04/26
     */
    private static void configPackage(AutoGenerator generator) {
        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setModuleName("");
        packageConfig.setParent(ENTITY_PATH.substring(0, 3));
        packageConfig.setService(REPO_PATH.substring(4));
        packageConfig.setServiceImpl(REPOIMPL_PATH.substring(4));
        packageConfig.setEntity(ENTITY_PATH.substring(4));
        packageConfig.setMapper(MAPPER_PATH.substring(4));
//        //.xml文件由自定义配置生成文件路径
//        packageConfig.setXml(XML_PATH);
//        //controller文件默认不生成
//        packageConfig.setController(CONTROLLER_PATH);
        generator.setPackageInfo(packageConfig);
    }

    /**
     * 策略配置
     *
     * @param generator generator
     * @author chengp
     * @date 2022/04/26
     */
    private static void configStrategy(AutoGenerator generator) {
        StrategyConfig strategy = new StrategyConfig();
        //表的前缀
        strategy.setTablePrefix(PREFIX);
        //表名下划线转为驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //字段名下划线转为驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //生成哪些表
        strategy.setInclude(TABLES);
        strategy.setControllerMappingHyphenStyle(true);
        //设置模版引擎的类型 freemarker使用ftl文件，velocity使用vm文件
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        //是否使用lombok
        strategy.setEntityLombokModel(true);
        //设置是否restful控制器
        strategy.setRestControllerStyle(true);
        //设置布尔类型字段是否去掉is前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        //全局大写命名
        //strategy.setCapitalMode(true)
        //全局下划线命名
        //strategy.setDbColumnUnderline(true)
        //自定义实体父类
        //strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        //自定义实体，公共字段
        //strategy.setSuperEntityColumns(new String[]{"test_id", "age"});
        //自定义Mapper父类
        //strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        //自定义Repository父类
        //strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        //自定义RepositoryImpl实现类父类
        //strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        //自定义Controller父类
        //strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        //实体是否生成字段常量（默认 false）
        //strategy.setEntityColumnConstant(true);
        generator.setStrategy(strategy);
    }

    /**
     * 自定义配置
     *
     * @param generator   generator
     * @param projectPath projectPath
     * @author chengp
     * @date 2022/04/26
     */
    private static void cofnigCustom(AutoGenerator generator, String projectPath) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // do nothing
            }
        };

        //.xml文件模板引擎路径
        String templatePath = "/templates/mapper.xml.ftl";
        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //自定义输出文件名
                String tableName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableInfo.getName());
                return projectPath.concat(XML_PATH).concat("/").concat(tableName).concat(XML_SUFFIX)
                        .concat(StringPool.DOT_XML);
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);
    }

    /**
     * 模版引擎配置
     *
     * @param generator generator
     * @author chengp
     * @date 2022/04/26
     */
    private static void configTemplate(AutoGenerator generator) {
        TemplateConfig templateConfig = new TemplateConfig();
        //不生成Controller文件
        templateConfig.setController(null);
        //.xml文件由自定义配置生成
        templateConfig.setXml(null);
        //使用自定义模板
        templateConfig.setEntity("/mybatisPlusTemplates/entity.java");
        templateConfig.setMapper("/mybatisPlusTemplates/mapper.java");
        templateConfig.setService("/mybatisPlusTemplates/service.java");
        templateConfig.setServiceImpl("/mybatisPlusTemplates/serviceImpl.java");
        generator.setTemplate(templateConfig);
    }

}
