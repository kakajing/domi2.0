package com.domi.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Author 卡卡
 * Created by jing on 2016/12/16.
 */
@Controller
@RequestMapping("/pic")
public class PicController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PicController.class);
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
//
//    @Value("${REPOSITORY_PATH}")
//    private String REPOSITORY_PATH;
//    @Value("${IMAGE_SERVICE_BASE_URL}")
//    private String IMAGE_SERVICE_BASE_URL;
//
//  //  @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
//    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile,
//                         HttpServletResponse response) throws IOException {
//
//        //校验图片格式
//        boolean isLegal = false;
//        for (String type : IMAGE_TYPE) {
//            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)){
//                isLegal = true;
//                break;
//            }
//        }
//        // 封装Result对象，并且将文件的byte数组放置到result对象中
//        PicUploadResult picUploadResult = new PicUploadResult();
//        // 状态
//        picUploadResult.setError(isLegal ? 0 : 1);
//        //文件 新路径
//        String filePath = getFilePath(uploadFile.getOriginalFilename());
//        if (LOGGER.isDebugEnabled()){
//            LOGGER.debug("Pic file upload .[{}] to [{}] .",
//                    uploadFile.getOriginalFilename(), filePath);
//        }
//
//        String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath,"E:\\图片\\轮播图"),"\\", "/");
//        picUploadResult.setUrl(IMAGE_SERVICE_BASE_URL + picUrl);
//        File file =  new File(filePath);
//
//        // 写文件到磁盘
//        uploadFile.transferTo(file);
//
//        // 校验图片是否合法
//        isLegal = false;
//        try {
//            BufferedImage image = ImageIO.read(file);
//            if (image != null){
//                picUploadResult.setWidth(image.getWidth() + "");
//                picUploadResult.setHeight(image.getHeight() + "");
//                isLegal = true;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 状态
//        picUploadResult.setError(isLegal ? 0 : 1);
//
//        if (!isLegal) {
//            // 不合法，将磁盘上的文件删除
//            file.delete();
//        }
//
//        //将java对象序列化为json字符串
//        return mapper.writeValueAsString(picUploadResult);
//
//    }
//
//    //E:\图片\轮播图\images\\2015\\11\\13\\20151113111111111.jpg
//    private String getFilePath(String sourceName){
//        String baseFolder = REPOSITORY_PATH + File.separator + "images";
//        Date nowDate = new Date();
//
//        // yyyy/MM/dd
//        String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
//                + File.separator + new DateTime(nowDate).toString("MM")
//                + File.separator + new DateTime(nowDate).toString("dd");
//
//        File file = new File(fileFolder);
//        if (!file.isDirectory()){
//            // 如果目录不存在，则创建目录
//            file.mkdirs();
//        }
//
//        // 生成新的文件名
//        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
//                + RandomUtils.nextInt(100, 9999) + "."
//                + StringUtils.substringAfterLast(sourceName, ".");
//        return fileFolder + File.separator + fileName;
//
//
//    }
}
