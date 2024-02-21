package com.hna.hka.archive.management.system.util;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: Point
 * @Author: 郭凯
 * @Description: 测算当前GPS是否在描点内
 * @Date: 2020/6/23 10:39
 * @Version: 1.0
 */
public class Point {

    private Double x;
    private Double y;
    public Point (Double x , Double y) {
        this.x = x;
        this.y = y;
    }
    public Double getX() {
        return x;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }

}
