package com.jiuliaye.eatsRush.controller;

import com.jiuliaye.eatsRush.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载

 * 主要用于菜品的展示，表现形式为在浏览器中直接打开，本质上就是服务端将文件以流的形式写回浏览器。
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${eatsRush.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){    //方法形参的名称需要与页面的file域的name属性一致。

        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //1、原始文件名
        String originalFilename = file.getOriginalFilename();//abc.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));  //.jpg
        //2、使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;//dfsdfdfd.jpg
        //3、创建一个目录对象
        File dir = new File(basePath);
        //4、判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }
        //5、将临时文件转存到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        try {
            //1、输入流，通过输入流读取文件内容（从服务器输入）
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //2、输出流，通过输出流将文件写回浏览器（输出到response）
            ServletOutputStream outputStream = response.getOutputStream();
            //3、设置response的类型
            response.setContentType("image/jpeg");

            //4、读取输入流 -> 写入到输出流
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //5、关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
