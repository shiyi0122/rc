package com.hna.hka.archive.management.system.util;

import java.awt.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: QrCodeLogoConfig
 * @Author: 郭凯
 * @Description: 编辑二维码
 * @Date: 2020/5/20 17:16
 * @Version: 1.0
 */
public class QrCodeLogoConfig {

    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为二维码照片的1/6
    public static final int DEFAULT_LOGOPART = 6;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public QrCodeLogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public QrCodeLogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }

}
