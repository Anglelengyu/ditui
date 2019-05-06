package net.hongkuang.ditui.common.utils.file;

import java.io.*;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
public class FileUtils {
    /**
     * 输出指定文件的byte数组
     *
     * @param filename 文件
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeBytes(String filePath, byte[] fileBytes) throws IOException {
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), fileBytes);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static InputStream readByFile(String folder, String fileName) throws FileNotFoundException {
        return new FileInputStream(folder + File.separator + fileName);
    }

    public static boolean isExists(String folder, String file) {
        return new File(folder + File.separator + file).exists();
    }

    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")
                || FilenameExtension.toUpperCase().equals(".BMP")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")
                || FilenameExtension.toUpperCase().equals(".GIF")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png") || FilenameExtension.toUpperCase().equals(".JPEG")
                || FilenameExtension.toUpperCase().equals(".JPG") || FilenameExtension.toUpperCase().equals(".PNG")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")
                || FilenameExtension.toUpperCase().equals(".TXT")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")
                || FilenameExtension.toUpperCase().equals(".VSD")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt") || FilenameExtension.toUpperCase().equals(".PPTX")
                || FilenameExtension.toUpperCase().equals(".PPT")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc") || FilenameExtension.toUpperCase().equals(".DOCX")
                || FilenameExtension.toUpperCase().equals(".DOC")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")
                || FilenameExtension.toUpperCase().equals(".XML")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".pdf") || FilenameExtension.equals(".PDF")
                || FilenameExtension.toUpperCase().equals(".PDF")) {
            return "application/pdf";
        }
        return null;
    }
}
