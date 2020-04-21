package com.hifo.dataoperation.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hifo.dataoperation.vo.PlUploadVO;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller父类，包含通用控制器
 *
 * @author whc
 * @date 2019/1/3 14:51
 */
@Log
@Controller
public class BasicController {

    @Value("${web.upload-path}")
    private String FilePath;
    @Value("${web.rel-path}")
    private String RelPath;

    /**
     * 返回分页数据
     *
     * @param count 总条数
     * @param data  当前页数据
     * @return json string
     */
    protected String pageData(long count, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", count);
        jsonObject.put("data", JSONArray.toJSON(data));
        return jsonObject.toJSONString();
    }

    /**
     * 上传文件
     *
     * @param request HttpServletRequest
     * @return map 绝对路径和相对路径
     * @throws Exception 所有异常
     */
    protected Map<String, String> saveUploadFile(HttpServletRequest request) throws Exception {
        String tempPath = getTempPath().get("absolutePath");
        /* 获得磁盘文件条目工厂 */
        DiskFileItemFactory factory = new DiskFileItemFactory();
        /* 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室 */
        factory.setSizeThreshold(1024 * 1024);
        /* 设置暂时存放的存储室，这个存储室可以和最终存储文件的目录不同（上传大的文件时减少内存占用） */
        factory.setRepository(new File(tempPath));
        /* 处理上传文件 */
        ServletFileUpload upload = new ServletFileUpload(factory);
        /* 设置最大文件尺寸，这里是4MB */
        upload.setSizeMax(4 * 1024 * 1024);

        /* 提交上来的文件都在这个list里面（可以上传多个文件） */
        List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
        /* 设置文件上传路径（正确的做法应该是放到配置文件中）*/
        for (FileItem fi : items) {
            String fileName = fi.getName();
            if (fileName != null) {
                /* 为文件名加上时间戳 */
                String nameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                String newFileName = nameWithoutSuffix + "_" + System.currentTimeMillis() + "." + suffix;
                /* 检查保存目录：[classpath]/tempFile */
                String savedFilePath = tempPath + File.separator;
                File savedFileFolder = new File(savedFilePath);
                if (!savedFileFolder.exists()) {
                    if (!savedFileFolder.mkdirs()) {
                        throw new Exception("请检查上传路径");
                    }
                }
                /* 保存文件 */
                File savedFile = new File(savedFilePath, newFileName);
                fi.write(savedFile);
                Map<String, String> map = new HashMap<>(2);
                map.put("absolutePath", savedFile.getAbsolutePath());
                map.put("relativePath", RelPath + newFileName);
                return map;

            }
        }
        throw new Exception("上传失败");
    }

    /**
     * 获取上传、下载文件的临时路径
     *
     * @return 路径:String
     */
    protected Map<String, String> getTempPath() throws Exception {
        Map<String, String> map = new HashMap<>(2);
        map.put("absolutePath", FilePath);
        map.put("relativePath", RelPath);
        return map;
    }

    /**
     * 上传文件添加时间戳
     *
     * @param fileName
     * @return
     */
    public String formatFileName(String fileName) {
        String nameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        fileName = nameWithoutSuffix + "_" + System.currentTimeMillis() + "." + suffix;
        return fileName;
    }

    /**
     * 单文件保存
     *
     * @param file
     * @return
     */
    public Map<String, String> saveFile(MultipartFile file) {
        Map<String, String> map = new HashMap<>(2);
        if (!file.isEmpty()) {
            //转存文件
            try {
                //设置文件的保存路径
                String tempPath = getTempPath().get("absolutePath");
                /* 为文件名加上时间戳 */
                String newFileName = formatFileName(file.getOriginalFilename());
                File savedFileFolder = new File(tempPath);
                if (!savedFileFolder.exists()) {
                    if (!savedFileFolder.mkdirs()) {
                        throw new Exception("请检查上传路径");
                    }
                }
                /* 保存文件 */
                file.transferTo(new File(tempPath + newFileName));
                map.put("absolutePath", tempPath + newFileName);
                map.put("relativePath", RelPath + newFileName);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return map;
    }

    /**
     * 多文件保存
     *
     * @param files
     * @return
     */
    public Map<String, Object> saveFiles(MultipartFile[] files) {
        Map<String, Object> map = null;
        try {
            if (files != null && files.length > 0) {
                map = new HashMap<>(files.length);
                for (MultipartFile file : files) {
                    map.put(file.getOriginalFilename(), saveFile(file));
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return map;
    }

    /**
     * 分块写入文件
     *
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public void writeWithBlock(String target, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1 && chunk != 0) {
            randomAccessFile.seek(chunk * (targetSize - srcSize) / chunk);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf, 0, len);
        }
        randomAccessFile.close();
    }

    /**
     * 分块上传文件
     *
     * @param plUpload
     * @param file
     * @throws IOException
     */
    public Map<String, String> uploadWithBlock(PlUploadVO plUpload, MultipartFile file) {
        Map<String, String> map = new HashMap<>(2);
        try {
            String fileName = getFileName(plUpload.getMd5(), file.getOriginalFilename(), plUpload.getChunks());
            writeWithBlock(getTempPath().get("absolutePath") + fileName, plUpload.getSize(), file.getInputStream(), file.getSize(), plUpload.getChunks(), plUpload.getChunk());
            addChunk(plUpload.getMd5(), plUpload.getChunk());
            if (isUploaded(plUpload.getMd5())) {
                removeKey(plUpload.getMd5());

                /* 为文件名加上时间戳 */
                String newFileName = formatFileName(file.getOriginalFilename());
                map.put("absolutePath", getTempPath().get("absolutePath") + newFileName);
                map.put("relativePath", RelPath + newFileName);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return map;
    }

    /**
     * 获取随机生成的文件名
     *
     * @param key
     * @param chunks
     * @return
     */
    public String getFileName(String key, String name, int chunks) {
        if (!isExist(key)) {
            synchronized (this) {
                if (!isExist(key)) {
                    chunkMap.put(key, new FileValue(name, chunks));
                }
            }
        }
        return chunkMap.get(key).name;
    }

    /**
     * 判断文件所有分块是否已上传
     *
     * @param key
     * @return
     */
    public boolean isUploaded(String key) {
        if (isExist(key)) {
            for (boolean b : chunkMap.get(key).status) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否有分块已上传
     *
     * @param key
     * @return
     */
    private boolean isExist(String key) {
        return chunkMap.containsKey(key);
    }

    /**
     * 为文件添加上传分块记录
     *
     * @param key
     * @param chunk
     */
    public void addChunk(String key, int chunk) {
        chunkMap.get(key).status[chunk] = true;
    }

    /**
     * 从map中删除键为key的键值对
     *
     * @param key
     */
    public void removeKey(String key) {
        if (isExist(key)) {
            chunkMap.remove(key);
        }
    }

    /**
     * 内部类记录分块上传文件信息
     */
    private class FileValue {
        String name;
        boolean[] status;

        FileValue(String name, int chunk) {
            this.name = generateFileName(name, chunk);
            this.status = new boolean[chunk];
        }
    }

    /**
     * 临时文件文件名
     *
     * @param name
     * @param chunk
     * @return
     */
    private String generateFileName(String name, Integer chunk) {
        /* 为文件名加上分块号 */
        String nameWithoutSuffix = name.substring(0, name.lastIndexOf("."));
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        String fileName = nameWithoutSuffix + "_" + chunk + "." + suffix;
        return fileName;
    }

    private static Map<String, FileValue> chunkMap = new HashMap<>();
}
