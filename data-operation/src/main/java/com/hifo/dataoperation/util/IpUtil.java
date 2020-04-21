package com.hifo.dataoperation.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip工具类
 *
 * @author whc
 * 2019/3/15
 */
public class IpUtil {

    /**
     * 获取远程请求的ip
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getRealIp(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取ip v2
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP) {
            return request.getRemoteAddr();
        }
        return accessIP;
    }

}
