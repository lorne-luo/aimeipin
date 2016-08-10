package com.meidi.controller.backend;

import com.meidi.util.MdCommon;
import com.meidi.util.MdConstants;
import com.meidi.util.MdModel;
import com.meidi.util.ResponseUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/5/8.
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController implements MdConstants {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "action") String action) {
        if ("config".equals(action)) {
            try {
                InputStream inputStream = UEditorController.class.getClassLoader().getResourceAsStream("/static/ueditor/jsp/config.json");

                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Map uploadImage(HttpServletRequest request, HttpServletResponse response) {
        MdModel model = new MdModel(request);
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断是否有文件上传
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

                MultipartFile multipartFile = multipartHttpServletRequest.getFile("fileName");
                if (!MdCommon.isEmpty(multipartFile)) {
                    String originalName = multipartFile.getOriginalFilename();
                    if (!MdCommon.isEmpty(originalName)) {
                        //获取文件后缀名
                        String fileNameSuffix = originalName.substring(originalName.lastIndexOf(".") + 1, originalName.length());
                        //生成临时文件名
                        String newName = MdCommon.getNowDate() + MdCommon.getRandomNum(8) + "." + fileNameSuffix;

                        File temporaryFile = new File(IMAGE_TEXT_PATH + "/" + newName);

                        multipartFile.transferTo(temporaryFile);

                        model.put("url", newName);
                        model.put("size", multipartFile.getSize());
                        model.put("type", newName.substring(newName.lastIndexOf(".")));
                        model.put("state", "SUCCESS");

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
