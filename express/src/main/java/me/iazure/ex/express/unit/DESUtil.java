package me.iazure.ex.express.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。
 * 
 * @author xiangze
 *
 */
public class DESUtil {

	private static Key key;
	// 设置密钥key
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			// 生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			// 运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// 设置上密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			// 初始化基于SHA1的算法对象
			generator.init(secureRandom);
			// 生成密钥对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取加密后的信息
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		// 基于BASE64编码，接收byte[]并转换成String
		//BASE64Encoder base64encoder = new BASE64Encoder();
		Encoder base64encoder = Base64.getEncoder();
		try {
			// 按UTF8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			// 获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 加密
			byte[] doFinal = cipher.doFinal(bytes);
			// byte[]to encode好的String并返回
//			return base64encoder.encode(doFinal);
			return base64encoder.encodeToString(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取解密之后的信息
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		// 基于BASE64编码，接收byte[]并转换成String
//		BASE64Decoder base64decoder = new BASE64Decoder();
		Decoder base64decoder = Base64.getDecoder();
		try {
			// 将字符串decode成byte[]
//			byte[] bytes = base64decoder.decodeBuffer(str);
			byte[] bytes = base64decoder.decode(str);
			// 获取解密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初始化解密信息
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			byte[] doFinal = cipher.doFinal(bytes);
			// 返回解密之后的信息
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(getEncryptString("jdbc:mysql://34.92.103.14:3306/wexpress?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"));
//		System.out.println(getDecryptString("xW6OiZLo8/ly0RW0vIm4nLovV+Cp9HuqfGXYUO1QtqN9nov1i1mGCu8wfdlb0IvGQHy33l1ok5iSMx9lhN6SddVTN8vsBv7h4gCCwYDigan+8xhhH6uqF/HKdged+cE/06RLny3n8Yrcdip9/pz8lH3HZ+yT8kTf9VR6z1T2+x7Kdbs8O5h4aUZN0O7ExVaX"));




//		DecodedJWT jwt = null;
//		JWTVerifier verifier = JWT.require(Algorithm.HMAC256("root")).build();
//		jwt  = verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJyb290In0.nMqswrNZRESPR3tx8YgRN-jZ2y80XgvuLM5gFFKIRm0");
//
//
//		System.out.println(JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJyb290In0.nMqswrNZRESPR3tx8YgRN-jZ2y80XgvuLM5gFFKIRm0"));
	}

}