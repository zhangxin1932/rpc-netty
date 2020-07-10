package com.zy.rpc.netty.demo01.common.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuDTO implements Serializable {
    private Integer id;
    private Map<Long, Object> map;
}
