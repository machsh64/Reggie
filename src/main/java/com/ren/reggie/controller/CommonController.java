package com.ren.reggie.controller;

import com.ren.reggie.common.R;
import com.ren.reggie.common.UploadCommon;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 19:13
 * @description:
 **/
@Slf4j
@Api("Common控制层")
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${basePath}")
    private String basePath;

    /**
     * 文件上传 使用uuid修改名称
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        String notRepeatName = null;
        try {
            // 使用封装好的 自动修改文件名称并保存到upPath的方法保存图片 返回值为修改后的名称 ( 优化后使用uuid随机生成，不在进行同名扫描 )
            notRepeatName = UploadCommon.upLoadWithNotRepeatName(file, basePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("使用UUID生成的新的文件名称 : {} ", notRepeatName);
        return R.success(notRepeatName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response) {
        // 输入流，通过输入流读取文件内容
        FileInputStream is = null;
        ServletOutputStream os = null;
        try {
            is = new FileInputStream(new File(basePath + name));
            // 输出流，通过输出流将文件协会浏览器，在浏览器展示图片
            os = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
