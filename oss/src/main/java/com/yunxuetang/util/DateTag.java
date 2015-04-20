package com.yunxuetang.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @discription: 将数据库中long类型的时间转化为data类型，以便输出到jsp页面 jstl中只能将date类型的数据格式化输出，long
 *               类型需要自己写tld来自定义标签输出
 * @author wangpengyong
 * @return:yyyy-MM-dd HH:mm:ss
 * @time：2014-11-21
 */
public class DateTag extends TagSupport {
	private static final long serialVersionUID = -2312310581852395045L;
	private String value;

	@Override
	public int doStartTag() throws JspException {
		String vv = "" + value;
		long time = Long.valueOf(vv);
		if(time!=0){
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String s = dateformat.format(c.getTime());
			try {
				pageContext.getOut().write(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				pageContext.getOut().write("------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return super.doStartTag();
	}

	public void setValue(String value) {
		this.value = value;
	}
}