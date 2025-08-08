package org.lecoder.easyflow.modules.flowlong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.service.IFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Administrator
 */
@Api(tags = "温江上传接口")
@RestController
@RequestMapping("/sys/file")
@AllArgsConstructor
public class UploadFileController {


    private final IFileService fileService;

    @ApiOperation("上传文件到minio")
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestPart MultipartFile file, String name) {
        try {
            File tempFile = File.createTempFile("temp-", file.getOriginalFilename());
            file.transferTo(tempFile);
            return AjaxResult.success(fileService.uploadFile(tempFile, name));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
