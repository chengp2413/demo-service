package com.example.demo.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mybatis Generator枚举
 *
 * @author chengp
 * @version 1.0
 * @date 2022/4/24 17:17
 */
@Getter
@AllArgsConstructor
public enum MyBatisGeneratorEnum {
    CRITERION("Criterion", "存储字段的条件", "构造函数"),
    CRITERIA("Criteria", "查询条件类，继承GeneratedCriteria类", "构造函数"),
    GENERATEDCRITERIA("GeneratedCriteria", "Criteria父类，保存一组Criteria对象", "构造函数"),
    CLEAR("clear", null, "清楚查询条件"),
    CREATECRITERIAINTERNAL("createCriteriaInternal", null, "内部构建查询条件"),
    CREATECRITERIA("createCriteria", null, "创建查询条件"),
    OR("or", null, "创建一个or查询条件"),
    GETOREDCRITERIA("getOredCriteria", null, "获取当前的查询条件实例"),
    ISDISTINCT("isDistinct", null, "获取是否过滤重复数据"),
    SETDISTINCT("setDistinct", null, "设置是否过滤重复数据"),
    GETORDERBYCLAUSE("getOrderByClause", null, "获取排序字段"),
    SETORDERBYCLAUSE("setOrderByClause", null, "设置排序字段"),
    COUNTBYEXAMPLE("countByExample", null, "根据指定的条件获取数据库记录数"),
    DELETEBYEXAMPLE("deleteByExample", null, "根据指定条件删除数据库记录"),
    DELETEBYKEY("deleteByPrimaryKey", null, "根据主键删除数据库记录"),
    INSERT("insert", null, "新写入数据库记录"),
    INSERTSELECTIVE("insertSelective", null, "动态写入数据库记录"),
    SELECTBYEXAMPLE("selectByExample", null, "根据指定条件查询数据库记录"),
    SELECTBYKEY("selectByPrimaryKey", null, "根据主键查询数据库记录"),
    UPDATEBYEXAMPLESELECTIVE("updateByExampleSelective", null, "动态根据指定条件更新数据库记录"),
    UPDATEBYEXAMPLE("updateByExample", null, "根据指定条件更新数据库记录"),
    UPDATEBYKEYSELECTIVE("updateByPrimaryKeySelective", null, "动态根据主键更新数据库记录"),
    UPDATEBYKEY("updateByPrimaryKey", null, "根据主键更新数据库记录"),
    ORDERBYCAUSE("orderByClause", null, "排序字段"),
    DISTINCT("distinct", null, "是否过滤重复数据"),
    ORDERCRITERIA("oredCriteria", null, "查询条件实例");

    private final String name;
    private final String classDesc;
    private final String methodDesc;

    public static MyBatisGeneratorEnum find(String name) {
        for (MyBatisGeneratorEnum value : MyBatisGeneratorEnum.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
