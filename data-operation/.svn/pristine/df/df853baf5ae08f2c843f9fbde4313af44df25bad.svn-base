
package com.hifo.dataoperation.util;

/**
 * 异常消息处理工具类
 * @author weisibin
 * @date 2020年4月17日14:04:13
 */
public final class ExceptionMsgUtil {
    /**
     * 获取异常堆栈字符串信息
     * @param e 异常堆栈
     * @return String 异常堆栈字符串
     */
    public static String getStackMsg(Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.toString() + "\n");
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }
}
