package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.dto.SetmealDTO;
import com.ren.reggie.entity.Setmeal;

import java.util.Collection;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 17:53
 * @description:
 **/
public interface SetmealService extends IService<Setmeal> {

    Page<SetmealDTO> getPage(Integer page, Integer pageSize, String name);

    SetmealDTO getDetailById(Long id);

    boolean saveSetmealDTO(SetmealDTO setmealDTO);

    boolean updateSetmealDTO(SetmealDTO setmealDTO);

    boolean deleteSetmealDTO(Collection<Long> ids);

}
