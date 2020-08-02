package com.zy.rpc.netty.demo01.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;

public final class IPTools {

    private IPTools() {
        throw new RuntimeException("IPTools can not instantiated.");
    }

    public static final String LOCALHOST_V4 = "127.0.0.1";
    public static final String LOCALHOST_V6 = "0:0:0:0:0:0:0:1";
    public static final String UNKNOWN = "known";

    /**
     * 获取 本机 IP 方法一
     * @return
     */
    public static String getLocalIP() {
        String ip;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nif = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = nif.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    ip = inetAddresses.nextElement().getHostAddress();
                    if (isLocalIP(ip)) {
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            // e.printStackTrace();
        }
        return LOCALHOST_V4;
    }

    /**
     * 内网 ip 网段:
     * 10.xxx.xxx.xxx
     * 192.168.xxx.xxx
     * 172.16.xxx.xxx - 172.31.xxx.xxx
     * @param ip
     * @return
     */
    private static boolean isLocalIP(String ip) {
        if (Objects.isNull(ip)) {
            return false;
        }
        if (!ip.startsWith("192.168") && !ip.startsWith("10.")) {
            if (ip.startsWith("172.")) {
                int second = Integer.parseInt(ip.split("\\.")[1]);
                return second >= 16 && second <= 31;
            }
            return false;
        }
        return true;
    }

    /**
     * 获取 本机 IP 方法一
     * 通过InetAddress的实例对象包含以数字形式保存的IP地址，同时还可能包含主机名
     * （如果使用主机名来获取InetAddress的实例，或者使用数字来构造，并且启用了反向主机名解析的功能）。
     * InetAddress类提供了将主机名解析为IP地址（或反之）的方法。
     * @return
     */
    public static String findLocalIP() {
        try {
            // 获取计算机名称和ip地址
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 获取ip地址
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return LOCALHOST_V4;
        }
    }

    /**
     * long->ip
     * 1.采用StringBuffer方便字符串拼接。
     * 2.ip第一位：整数直接右移24位。
     *   ip第二位：整数先高8位置0.再右移16位。
     *   ip第三位：整数先高16位置0.再右移8位。
     *   ip第四位：整数高24位置0.
     * 3.将他们用分隔符拼接即可。
     * @param longIP
     * @return
     */
    public static String longToIpv4(long longIP) {
        return String.valueOf(longIP >>> 24) + "." +
                ((longIP & 16777215L) >>> 16) + "." +
                ((longIP & 65535L) >>> 8) + "." +
                (longIP & 255L);
    }

}
