package com.ren.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-01 15:06
 * @description: 自定义的元数据处理器
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作时自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("# 公共字段自动填充【insert】.... #");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 获取保存在线程中的用户id
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        // 清空线程中保存的值
        BaseContext.removeThread();
    }

    /**
     * 更新操作时自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("# 公共字段自动填充【update】.... #");
        log.info(metaObject.toString());

        metaObject.setValue("updateTime", LocalDateTime.now());
        // 获取保存在线程中的用户id
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        // 清空线程中保存的信息
        BaseContext.removeThread();
    }

}
