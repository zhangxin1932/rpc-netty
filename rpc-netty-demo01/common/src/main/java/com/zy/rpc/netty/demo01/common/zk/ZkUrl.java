package com.zy.rpc.netty.demo01.common.zk;

import com.zy.rpc.netty.demo01.common.demo.Constants;
import com.zy.rpc.netty.demo01.common.utils.ArrayUtils;
import com.zy.rpc.netty.demo01.common.utils.StringTools;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZkUrl {
    private String[] connectionStrings;
    private int connectionTimeouts;
    private String authority;

    public String getConnection2String() {
        return StringTools.join(connectionStrings, Constants.SEPARATOR_01);
    }
}
