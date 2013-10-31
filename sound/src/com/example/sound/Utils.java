package com.example.sound;

public class Utils {
	
	// 将short数组转换成byte数组
	public static byte[] shortToByteSmall(short[] buf){
		
		byte[] bytes = new byte[buf.length * 2];
		for(int i = 0, j = 0; i < buf.length; i++, j+=2){
			short s = buf[i];
			
			byte b1 = (byte) (s & 0xff);
			byte b0 = (byte) ((s >> 8) & 0xff);
			
			bytes[j] = b1;
			bytes[j+1] = b0;
		}
		return bytes;
		
	}
	
	// 将byte数组转换成short数组
	public static short[] byteToShortSmall(byte[] buf) {
		
		int size = buf.length;
		if (size % 2 != 0) {
			return null;
		}
		
		short[] shorts = new short[size/2];
		for(int i = 0; i < size; i+=2) {
			int index = i/2;
			shorts[index] =  (short) ((short) buf[i] + (short) (buf[i+1] << 8));
		}
		return shorts;
	}
}
