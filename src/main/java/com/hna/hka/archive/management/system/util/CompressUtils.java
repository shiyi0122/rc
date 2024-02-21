package com.hna.hka.archive.management.system.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * @Author zhang
 * @Date 2022/2/16 17:34
 * 压缩图片工具类
 */
public class CompressUtils {


    /**
     *第一种方法
     * @param filePath  目标文件地址
     * @param multipartFile  源文件
     * @return
     */

    public static  Boolean upload(File filePath, MultipartFile multipartFile){

        boolean rs = false;
        try {
                File parentFile = filePath.getParentFile();
                if (!parentFile.exists()) {
                    boolean mkdirs = parentFile.mkdirs();
                }
                Thumbnails.of(multipartFile.getInputStream()).scale(1f).outputQuality(0.5f).toFile(filePath);
            rs = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**第二种方法

     */
    /**
     * 实现图像的等比缩放和缩放后的截取, 处理成功返回true, 否则返回false
     *
     * @param in
     *            图片输入流
     * @param saveFile
     *            压缩后的图片输出流
     * @param compress_width
     *            压缩后宽度
     * @throws Exception
     */
    public static boolean compress(InputStream in, File saveFile, float compress_width) {
        if (null == in || null == saveFile || compress_width < 1) {// 检查参数有效性
            // LoggerUtil.error(ImageHelper.class, "--invalid parameter, do
            // nothing!");
            return false;
        }

        BufferedImage srcImage = null;
        double multiple = 1.0d;
        try {
            srcImage = ImageIO.read(in);
            int original_width = srcImage.getWidth(); // 原始宽度
            if (original_width > compress_width) {
                multiple = original_width / compress_width; // 计算要达到指定宽度要缩放的倍数
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 原图的大小
        int width = (int) (srcImage.getWidth() / multiple);
        int hight = (int) (srcImage.getHeight() / multiple);

        srcImage = resize(srcImage, width, hight);

        // 缩放后的图像的宽和高
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
        if (w == width) {
            // 计算X轴坐标
            int x = 0;
            int y = h / 2 - hight / 2;
            try {
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
        else if (h == hight) {
            // 计算X轴坐标
            int x = w / 2 - width / 2;
            int y = 0;
            try {
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * 实现图像的等比缩放
     *
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx < sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }


    /**
     * 实现缩放后的截图
     *
     * @param image
     *            缩放后的图像
     * @param subImageBounds
     *            要截取的子图的范围
     * @param subImageFile
     *            要保存的文件
     * @throws IOException
     */
    private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException {
        if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth() || subImageBounds.height - subImageBounds.y > image.getHeight()) {
            // LoggerUtil.error(ImageHelper.class, "Bad subimage bounds");
            return;
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width, subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
        ImageIO.write(subImage, formatName, subImageFile);
    }

}
