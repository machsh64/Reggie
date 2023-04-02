package com.ren.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-10 16:29
 * @description: 这是一个用来上传时防止文件重名的工具类
 **/
@Slf4j
public class UploadCommon {

    private static int num;

    /**
     * 自动上传不会重名的文件
     *
     * @param file
     * @param basePath
     * @return 返回一个修改名字后的文件名
     * @throws IOException
     */
    public static String upLoadWithNotRepeatName(MultipartFile file, String basePath) throws IOException {
        //num = 1; // 每次存储文件重置num值
        //String notRepeatName = getNotRepeatName(file.getOriginalFilename(), basePath);  // 此段报废，使用uud生成新的文件名不需要扫描文件库造成资源浪费
        HashMap<String, String> nameBody = getFileNameBody(file.getOriginalFilename());
        String notRepeatName = UUID.randomUUID().toString() + "." + nameBody.get("ex");
        File dir = new File(basePath);
        if (!dir.exists()) {  // 判断当前目录是否存在
            boolean b = dir.mkdirs();// 如果目录不存在则创建目录
            if (!b){
                log.error("create mkdirs failed");
            }
        }
        file.transferTo(new File(basePath + notRepeatName));
        return notRepeatName;
    }

    public static boolean removePicWithName(String basePath, String picName) {
        Path path = Paths.get(basePath + picName);
        boolean b = false;
        try {
            b = Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 修改重名的文件
     *
     * @param fileName
     * @param basePath
     * @return
     */
    private static String getNotRepeatName(String fileName, String basePath) {
        HashMap<String, String> nameBody = getFileNameBody(fileName);
        String fileNameNoEx = nameBody.get("head");  // 无后缀文件名
        String extensionName = nameBody.get("ex");  // 文件后缀名
        File[] direct = new File(basePath).listFiles();
        for (int i = 0; i < Objects.requireNonNull(direct).length; i++) {
            if (fileName.equals(direct[i].getName())) {
                fileName = fileNameNoEx + "_" + (num++) + "." + extensionName;
            }
        }
        return fileName;
    }

    /**
     * 获取文件的名与后缀
     *
     * @param fileName
     * @return
     */
    private static HashMap<String, String> getFileNameBody(String fileName) {
        HashMap<String, String> map = new HashMap<>();
        String fileNameWithOutEx = null;
        String extensionName = null;
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                fileNameWithOutEx = fileName.substring(0, dot);
            }
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                extensionName = fileName.substring(dot + 1);
            }
        }
        map.put("head", fileNameWithOutEx);
        map.put("ex", extensionName);
        return map;
    }
}
