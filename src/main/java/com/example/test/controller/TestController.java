package com.example.test.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.example.test.vo.DataDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/swagger")
@Api(value = "Swagger2 在线接口文档")
public class TestController {

    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据用户唯一标识获取用户信息")
    public String getUserInfo(@PathVariable @ApiParam(value = "用户唯一标识") Long id) {
        // 模拟数据库中根据id获取User信息
        return null;
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据用户唯一标识获取用户信息1")
    public String getUserInfo1(HttpServletResponse response) throws IOException {
        // 获取数据
        List<DataDTO> dataList = new ArrayList<>();
        int j = 10000;
        for (int i = 0; i < j; i++) {
            DataDTO dataDTO = new DataDTO();
            dataDTO.setId("123");
            dataDTO.setTabNbr("123");
            dataDTO.setReplenishItemStoreId("123");
            dataList.add(dataDTO);
        }
        // 设置文件名
        String fileName = "123.xlsx";

        // 设置响应头
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        // 创建输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BigDecimal bigDecimal1 = new BigDecimal(j);
        BigDecimal bigDecimal2 = new BigDecimal(0.00);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        // 创建excel对象
        EasyExcel.write(outputStream, DataDTO.class).sheet("数据").registerWriteHandler(new RowWriteHandler() {
            @Override
            public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {
               if (integer != 0) {
                   BigDecimal bigDecimal = new BigDecimal(integer);
                   BigDecimal divide = bigDecimal.divide(bigDecimal1, 2, BigDecimal.ROUND_DOWN);
                   BigDecimal multiply = divide.multiply(new BigDecimal(100));
                   if (bigDecimal2.compareTo(multiply) != 0) {
                       System.out.println("aa" + multiply.toString());
                       bigDecimal2.add(multiply);
                       System.out.println(atomicInteger.getAndIncrement());
                   }
               }
            }

            @Override
            public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
//                System.out.println("cc" + String.valueOf(integer));
            }

            @Override
            public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
//                System.out.println("dd" + String.valueOf(integer));
            }
        }).doWrite(dataList);
        // 关闭输出流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//        File tmp = File.createTempFile("123", ".xlsx");
        MultipartFile multipartFile = getMultipartFile(inputStream);

//        MultipartFile multipartFile = new MockMultipartFile("123.xlsx" , outputStream.toByteArray());
        File file = new File("D:/test/123.xlsx");
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        outputStream.flush();
        outputStream.close();
        return null;
    }


    public static MultipartFile getMultipartFile(InputStream in) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , "123");
        try (InputStream input = in;
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        return new CommonsMultipartFile(item);
    }


    @PostMapping("/upload")
    @ApiOperation(value = "上传")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取上传的文件
        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        // 将上传的文件保存到服务器的硬盘上
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = "D:/test"; // 指定上传文件的保存路径
        Files.copy(fileContent, Paths.get(uploadPath).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    }
}