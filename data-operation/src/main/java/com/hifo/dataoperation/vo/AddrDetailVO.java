package com.hifo.dataoperation.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 地址详情vo
 *
 * @Author: xmw
 * @Date: 2019/5/30 10:59
 */
@Data
public class AddrDetailVO {
    private String road;
    private String number;

    public AddrDetailVO(String addrDetail) {
        String[] addr = StringUtils.split(addrDetail, "-");
        if (addr.length > 1) {
            this.road = addr[0];
            if (addr.length == 2) {
                this.number = addr[1];
            } else {
                StringBuffer num = new StringBuffer(addr[1]);
                for (int i = 2; i < addr.length; i++) {
                    num.append("-").append(addr[i]);
                }
                this.number = num.toString();
                if (this.number.endsWith("号")) {
                    this.number = this.number.substring(0, this.number.length() - 1);
                }
            }
        }

    }
}
