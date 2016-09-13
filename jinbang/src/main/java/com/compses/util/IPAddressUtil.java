package com.compses.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip地址工具类
 * @author jianghw
 *
 */
public class IPAddressUtil {
	/**
	 * 得到2个Ip地址之间包含了多少个Ip地址,以及具体Ip地址的数组，首尾包含
	 * @param ipAddress1
	 * @param ipAddress2
	 * @return数组第一个是ip个数，数组第二个是具体Ip
	 */
	public static Object[] ipCountBetweenIpAddreses(String ipAddress1,String ipAddress2) {
		Object[] result = new Object[2];
		if(isValid(ipAddress1)&&isValid(ipAddress2)) {
			int[] ipAddress1IntArray = toIntFormat(ipAddress1);
			int[] ipAddress2IntArray = toIntFormat(ipAddress2);

			long count = ipAddress2IntArray[0]*256*256*256+ipAddress2IntArray[1]*256*256+ipAddress2IntArray[2]*256+ipAddress2IntArray[3]-
			(ipAddress1IntArray[0]*256*256*256+ipAddress1IntArray[1]*256*256+ipAddress1IntArray[2]*256+ipAddress1IntArray[3]);
			result[0] = Math.abs(count)+1;
			//较小的Ip地址设为起始点
			String samllIp = ipAddress1;
			String[] betweenIps = new String[Integer.parseInt(result[0].toString())];
			if(count<0) {
				samllIp = ipAddress2;
			}
			betweenIps[0] = samllIp;
			for(int i=1;i<betweenIps.length;i++) {
				betweenIps[i] = getNextIpAddress(betweenIps[i-1]);
			}
			result[1] = betweenIps;
		}
		return result;
	}
	/**
	 * 根据ip地址，子网掩码，得到ip范围
	 * @param ipAddress
	 * @param subMask
	 * @returnip个数，数组第二个是具体Ip
	 */
	public static Object[] ipCountBetweenSubMask(String ipAddress,String subMask) {
		//ip地址轉換為2進制格式
		//192.168.1.1
		//11000000.10101000.00000001.00000001
		String ipAddressBinaryString = toBinaryString(ipAddress);
		//子網掩碼轉為2進制格式
		//255.255.252.0 
		//11111111.11111111.11111100.00000000
		String subMaskBinaryString = toBinaryString(subMask);
		subMaskBinaryString.lastIndexOf("1");
		//算出子网掩码0的位数
		int varlength = subMaskBinaryString.substring(subMaskBinaryString.lastIndexOf("1")+1).length();
		//将ip可以变化的地址最小，最大计算出
		StringBuffer varLengthZero = new StringBuffer("");
		StringBuffer varLengthOne = new StringBuffer("");
		for(int i = 1;i<=varlength ;i++) {
			varLengthZero.append("0");
			varLengthOne.append("1");
		}
		String ipAddressBinaryStringMix = ipAddressBinaryString.substring(0,32-varlength)+varLengthZero.toString();
		String ipAddressBinaryStringMax = ipAddressBinaryString.substring(0,32-varlength)+varLengthOne.toString();
		String ipAddressStringMix = convertBinaryTOString(ipAddressBinaryStringMix);
		String ipAddressStringMax = convertBinaryTOString(ipAddressBinaryStringMax);
		//String[] result = {ipAddressStringMix,ipAddressStringMax};
		return ipCountBetweenIpAddreses(ipAddressStringMix,ipAddressStringMax);
	}
	/**
	 * 校验是否合法
	 * @param ipAddress
	 * @return
	 */
	public static boolean isValid(String ipAddress) {
		String[] ipAddresses = ipAddress.split("\\.");
		for(String ip:ipAddresses) {
			int ipInt = Integer.parseInt(ip.trim());
			return (ipInt>=0&&ipInt<=255)?true:false;
		}
		return true;
	}
	/**
	 * 将ip地址转为32位的二进制表示
	 * @param ipAddress
	 * @return
	 */
	public static String toBinaryString(String ipAddress) {
		StringBuffer ipString = new StringBuffer("");
		String[] ipAddressArray = ipAddress.split("\\.");
		StringBuffer ipBinarys = new StringBuffer();
		for(String ip:ipAddressArray) {
			StringBuffer ipBinary = new StringBuffer(Integer.toBinaryString(Integer.parseInt(ip.trim())));
			switch(ipBinary.length()) {
			case 1:ipBinary.insert(0, "0000000");break;
			case 2:ipBinary.insert(0,"000000");break;
			case 3:ipBinary.insert(0,"00000");break;
			case 4:ipBinary.insert(0,"0000");break;
			case 5:ipBinary.insert(0,"000");break;
			case 6:ipBinary.insert(0,"00");break;
			case 7:ipBinary.insert(0,"0");break;
			}
			ipBinarys.append(ipBinary);
			
		}
		return ipBinarys.toString();
	}
	/**
	 * 将32位二进制转换为正确Ip地址
	 * @param binaryString
	 * @return
	 */
	public static String convertBinaryTOString(String binaryString) {
		String first = binaryString.substring(0, 8);
		String second =binaryString.substring(8, 16);
		String third =binaryString.substring(16, 24);
		String forth =binaryString.substring(24, 32);
		int firstIP = Integer.parseInt(first, 2);
		int secondIP =Integer.parseInt(second, 2);
		int thirdIP =Integer.parseInt(third, 2);
		int forthIP =Integer.parseInt(forth, 2);
		StringBuffer ipAddress = new StringBuffer();
		ipAddress.append(firstIP+".");
		ipAddress.append(secondIP+".");
		ipAddress.append(thirdIP+".");
		ipAddress.append(forthIP);
		return ipAddress.toString();
	}
	/**
	 * 将字符串的ip地址转换为整数的数组格式
	 * @param ipAddress
	 * @return
	 */
	public static int[] toIntFormat(String ipAddress) {
		String[] ipAddress1Array = ipAddress.split("\\.");
		int[] ipAddress1IntArray = new int[4];
		ipAddress1IntArray[0] = Integer.parseInt(ipAddress1Array[0].trim());
		ipAddress1IntArray[1] = Integer.parseInt(ipAddress1Array[1].trim());
		ipAddress1IntArray[2] = Integer.parseInt(ipAddress1Array[2].trim());
		ipAddress1IntArray[3] = Integer.parseInt(ipAddress1Array[3].trim());
		return ipAddress1IntArray;
	}
	/**
	 * 根据给定的Ip地址获得下一个Ip地址
	 * @param ipAddress
	 * @return
	 */
	public static String getNextIpAddress(String ipAddress) {
		int [] ipAddressInt = toIntFormat(ipAddress);
		int first = ipAddressInt[0];
		int second = ipAddressInt[1];
		int third = ipAddressInt[2];
		int forth = ipAddressInt[3];
		if(forth<255) {
			 forth++;
		}else {
			forth = 0;
			if(third<255) {
				third ++;
			}
			else {
				third = 0;
				if(second<255) {
					second ++;
				}
				else {
					second = 0;
					if(first<255) {
						first++;
					}
					else {
						first = 0;
					}
				}
			}
			
		}
		
		StringBuffer newIpAddress = new StringBuffer("");
		newIpAddress.append(first+".").append(second+".").append(third+".").append(forth);
		return newIpAddress.toString();
	}
	/**
	 * 得到本机的Ip地址
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIpAddress() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println(addr.getHostName());
		String ip = addr.getHostAddress();
		return ip;
	}

	/**
	 * 得到本机的子网掩码
	 * 
	 * @return
	 */
	public static String getSubMask() {
		//默认写死
		return "255.255.255.0";
	}
	public static void main(String[] args) {
		String ip1 = "192.168.255.255";
		String ip2 = "192.168.3.255";
		//System.out.println(IPAddressUtil.ipCountBetweenIpAddreses(ip1, ip2)[0]);
	IPAddressUtil.ipCountBetweenSubMask("192.168.1.1", "255.255.252.0");
		//System.out.print(ip[0]+"   "+ip[1]);
		//System.out.println(IPAddressUtil.getNextIpAddress(ip1));
		
		
	}

}
