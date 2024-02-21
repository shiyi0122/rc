package com.hna.hka.archive.management.system.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: Tinypinyin
 * @Author: 郭凯
 * @Description: 汉字转拼音
 * @Date: 2020/6/8 10:57
 * @Version: 1.0
 */
public class Tinypinyin {

    /** (一个)中文 正则表达式 */
    private static final String CHINESE_REGEX = "[\u4e00-\u9fa5]";

    public static String tinypinyin(String originChinese) {
        boolean abbreviation = false;
        HanyuPinyinCaseType caseType = HanyuPinyinCaseType.LOWERCASE;
        char[] chineseCharArray = originChinese.trim().toCharArray();
        // 设置转换格式
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写(默认即为小写)
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder hanYuPinYinResult = new StringBuilder(6);
        boolean isChinese;
        String tempStr = null;
        for (char cStr : chineseCharArray) {
            isChinese = String.valueOf(cStr).matches(CHINESE_REGEX);
            // 如果字符是中文,则将中文转为汉语拼音
            if (isChinese) {
                try {
                    tempStr = PinyinHelper.toHanyuPinyinStringArray(cStr, defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                tempStr = abbreviation ? tempStr.substring(0, 1) : tempStr;
                hanYuPinYinResult.append(tempStr);
            } else {
                // 如果字符不是中文,则不转换
                hanYuPinYinResult.append(cStr);
            }
        }
        return hanYuPinYinResult.toString();
    }

}
