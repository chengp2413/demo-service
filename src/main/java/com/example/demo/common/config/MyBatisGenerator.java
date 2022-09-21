package com.example.demo.common.config;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mybatis Generator
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/23 9:35
 */
public class MyBatisGenerator implements CommentGenerator {
    /**
     * 参数配置
     */
    private Properties properties;
    /**
     * 系统参数
     */
    private Properties systemPro;
    /**
     * 日期显示标志 true-不显示 false-显示
     */
    private boolean suppressDate;
    /**
     * 注释显示标志 true-不显示 false-显示
     */
    private boolean suppressAllComments;
    /**
     * 库表字段备注显示标志 true-显示 false-不显示
     */
    private boolean addRemarkComments;
    /**
     * 日期格式
     */
    private SimpleDateFormat dateFormat;
    /**
     * 作者
     */
    private String author;

    /**
     * 构造函数
     */
    public MyBatisGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            this.dateFormat = new SimpleDateFormat(dateFormatString);
        }
        this.author = StringUtility.stringHasValue(properties.getProperty("author")) ? properties.getProperty("author") : "";
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            String shortName = innerClass.getType().getShortName();
            MyBatisGeneratorEnum myBatisGeneratorEnum = MyBatisGeneratorEnum.find(shortName);
            innerClass.addJavaDocLine("/**");
            if (null == myBatisGeneratorEnum) {
                if (StringUtility.stringHasValue(introspectedTable.getRemarks())) {
                    innerClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
                    innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
                } else {
                    innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
                }
            } else {
                innerClass.addJavaDocLine(" * " + myBatisGeneratorEnum.getClassDesc());
            }
            innerClass.addJavaDocLine(" *");
            innerClass.addJavaDocLine(" * @author " + this.author);
            innerClass.addJavaDocLine(" * @date " + this.dateFormat.format(new Date()));
            innerClass.addJavaDocLine(" */");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!this.suppressAllComments && this.addRemarkComments) {
            field.addJavaDocLine("/**");
            String remarks = introspectedColumn.getRemarks();
            if (StringUtility.stringHasValue(remarks)) {
                String[] remarkLines = remarks.split(this.systemPro.getProperty("line.separator"));
                for (String remarkLine : remarkLines) {
                    field.addJavaDocLine(" * " + remarkLine);
                }
            } else {
                field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
            }
            field.addJavaDocLine(" */");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments && this.addRemarkComments) {
            MyBatisGeneratorEnum myBatisGeneratorEnum = MyBatisGeneratorEnum.find(field.getName());
            field.addJavaDocLine("/**");
            if (null == myBatisGeneratorEnum) {
                field.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
            } else {
                field.addJavaDocLine(" * " + myBatisGeneratorEnum.getMethodDesc());
            }
            field.addJavaDocLine(" */");
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            topLevelClass.addJavaDocLine("/**");
            if (StringUtility.stringHasValue(introspectedTable.getRemarks())) {
                topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
                topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable() + " 实体类");
            } else {
                topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable() + " 实体类");
            }
            topLevelClass.addJavaDocLine(" *");
            topLevelClass.addJavaDocLine(" * @author " + this.author);
            topLevelClass.addJavaDocLine(" * @date " + this.dateFormat.format(new Date()));
            topLevelClass.addJavaDocLine(" */");
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        if (!this.suppressAllComments) {
            String shortName = innerClass.getType().getShortName();
            MyBatisGeneratorEnum myBatisGeneratorEnum = MyBatisGeneratorEnum.find(shortName);
            innerClass.addJavaDocLine("/**");
            if (null == myBatisGeneratorEnum) {
                if (StringUtility.stringHasValue(introspectedTable.getRemarks())) {
                    innerClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
                    innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
                } else {
                    innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
                }
            } else {
                innerClass.addJavaDocLine(" * " + myBatisGeneratorEnum.getClassDesc());
            }
            innerClass.addJavaDocLine(" *");
            innerClass.addJavaDocLine(" * @author " + this.author);
            innerClass.addJavaDocLine(" * @date " + this.dateFormat.format(new Date()));
            innerClass.addJavaDocLine(" */");
        }
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!this.suppressAllComments) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * " + method.getName());
            method.addJavaDocLine(" *");
            method.addJavaDocLine(" * @return " + introspectedColumn.getActualColumnName() + " " + introspectedColumn.getRemarks());
            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!this.suppressAllComments) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * " + method.getName());
            method.addJavaDocLine(" *");
            Parameter parameter = method.getParameters().get(0);
            method.addJavaDocLine(" * @param " + parameter.getName() + " " + introspectedColumn.getRemarks());
            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            MyBatisGeneratorEnum myBatisGeneratorEnum = MyBatisGeneratorEnum.find(method.getName());
            FullyQualifiedJavaType returnType = method.getReturnType();
            method.addJavaDocLine("/**");
            if (null != myBatisGeneratorEnum) {
                method.addJavaDocLine(" * " + myBatisGeneratorEnum.getMethodDesc());
            } else {
                method.addJavaDocLine(" * " + method.getName());
            }
            method.addJavaDocLine(" *");
            List<Parameter> parameters = method.getParameters();
            if (!parameters.isEmpty()) {
                for (Parameter parameter : parameters) {
                    method.addJavaDocLine(" * @param " + parameter.getName() + " 参数");
                }

            }
            if (null != returnType) {
                method.addJavaDocLine(" * @return " + returnType.getShortName());
            }
            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    /**
     * 测试实施
     *
     * @param args args
     */
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File("D:\\chengp\\IDEA_workspace\\demo-service\\src\\main\\resources\\mybatisGenerator\\mybatisGeneratorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
