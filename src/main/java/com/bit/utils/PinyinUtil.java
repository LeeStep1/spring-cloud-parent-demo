package com.bit.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/1/13 14:08
 **/
public class PinyinUtil {

	public static String toPinYinString(char c) {
		String[] pyChars = null;
		try {
			pyChars = PinyinHelper.toHanyuPinyinStringArray(c);
			if(pyChars != null && pyChars.length >= 1){
				return pyChars[0].charAt(0)+"";
			}else if(StringUtils.isAlpha(c+"") || StringUtils.isNumeric(c+"")){
				return c+"";
			}else{
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 取得汉字拼音缩写
	 *
	 * @param str 汉字
	 * @return 拼音缩写大写
	 */
	public static String toPinYinString(String str) {
		String ret = "";
		for(char c : str.toCharArray()){
			ret += toPinYinString(c);
		}
		return ret.toUpperCase();
	}


	/**
	 * 获取汉字串拼音，英文字符不变 【首字母大写】
	 * @param chinese 汉字串
	 * @return 汉语拼音
	 */
	public static String getFullSpell(String chinese) {
		// 用StringBuffer（字符串缓冲）来接收处理的数据
		StringBuffer sb = new StringBuffer();
		//字符串转换字节数组
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		//转换类型（大写or小写）
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//定义中文声调的输出格式
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		//定义字符的输出格式
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		for (int i = 0; i < arr.length; i++) {
			//判断是否是汉子字符
			if (arr[i] > 128) {
				try {
					sb.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				// 如果不是汉字字符，直接拼接
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}



	public static void main(String[] args) {
		System.out.println(PinyinUtil.getFullSpell("邵振"));

	}
}
