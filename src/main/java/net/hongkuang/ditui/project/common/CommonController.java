package net.hongkuang.ditui.project.common;

import net.hongkuang.ditui.common.utils.file.FileUploadUtils;
import net.hongkuang.ditui.common.utils.file.FileUtils;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {

        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
        try {
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    @RequestMapping("common/image/get/{fileName}")
    public void fileDownload(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        String filExtension = fileName.substring(fileName.lastIndexOf("."));
        try {
            String filePath = RuoYiConfig.getUploadFolderPath() + fileName;
            response.setCharacterEncoding("utf-8");
            response.setContentType(FileUtils.contentType(filExtension));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败 {}", e.getMessage());
        }
    }

    /**
     * 保存头像
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam("upfile") List<MultipartFile> files) {
        try {
            if (files == null || files.isEmpty()) {
                return error();
            }

            List<String> uploadedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                log.debug("upload file:{}, contentType:{}", file.getOriginalFilename(), file.getContentType());
                String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadFolderPath(), file, file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                uploadedFiles.add(avatar);
            }
            return AjaxResult.success().put("files", uploadedFiles);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return error(e.getMessage());
        }
    }

    /**
     * 保存头像
     */
    @CrossOrigin
    @PostMapping("/common/ajaxUpload")
    @ResponseBody
    public AjaxResult ajaxUpload(HttpServletRequest request) {
        System.out.println("收到文件上传请求");
        Map<String, Object> result = new HashMap<>();
        try {
            String taskId = request.getParameter("taskId");
            Integer index = Integer.valueOf(request.getParameter("index"));
            log.info("文件上传附带参数：taskId:{},imgIndex:{}", taskId, index);
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile multipartFile = multiRequest.getFile(iter.next());
                    log.info("文件名称：{}", multipartFile.getOriginalFilename());
                    String fileName = multipartFile.getOriginalFilename();
                    if (fileName == null || fileName.trim().equals("")) {
                        continue;
                    }
                    String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadFolderPath(), multipartFile, multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
                    result.put("path", avatar);
                    result.put("taskId", taskId);
                    result.put("imgIndex", index);
                    return success().put("data", result);
                }
            }
        } catch (Exception e) {
            return error("图片上传失败!");
        }
        return error();
    }

    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
