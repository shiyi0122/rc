package com.hna.hka.archive.management.system.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hna.hka.archive.management.system.model.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: QrCodeUtil
 * @Author: 郭凯
 * @Description: 生成二维码
 * @Date: 2020/5/20 17:08
 * @Version: 1.0
 */
public class QrCodeUtil {

    public static boolean qrCode(String robotCode) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();

            // 设置UTF-8， 防止中文乱码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码四周白色区域的大小
            hints.put(EncodeHintType.MARGIN, 2);
            // 设置二维码的容错性
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // width:图片完整的宽;height:图片完整的高
            // 因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
            int width = 450;
            int height = 550;
            // 画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
            String wxRobotCode = Info.getQrWechatPath() + "?" + "id=" + robotCode;//拼接小程序前缀地址
            //String wxRobotCode = Info.getQrWechatPath() + robotCode;//拼接小程序前缀地址
            BitMatrix bitMatrix = multiFormatWriter.encode(wxRobotCode, BarcodeFormat.QR_CODE, width, height, hints);
            // qrcFile用来存放生成的二维码图片（无logo，无文字）

            File logoFile = new File(Info.getLogoPath());
//            File logoFile = new File("F:");
            // 开始画二维码
            BufferedImage barCodeImage = MatrixToImageWriter.writeToFile(bitMatrix, "jpg");
            // 在二维码中加入图片
            QrCodeLogoConfig logoConfig = new QrCodeLogoConfig(); // LogoConfig中设置Logo的属性
            BufferedImage image = MatrixToImageWriter.addLogo_QRCode(barCodeImage, logoFile, logoConfig);
            int font = 18; // 字体大小
            int fontStyle = 1; // 字体风格
            // 用来存放带有logo+文字的二维码图片
            String newImageWithText = Info.getUploadPath() + robotCode + ".jpg";
            // 在二维码下方添加文字（文字居中）
            boolean pressText = MatrixToImageWriter.pressText(robotCode, newImageWithText, image, fontStyle, Color.black, font, width, height);
            if (pressText) {
                return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //测试
    public static void main(String[] args) {
        boolean qrCode = qrCode("2000312");
        if (qrCode) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
    }

}
