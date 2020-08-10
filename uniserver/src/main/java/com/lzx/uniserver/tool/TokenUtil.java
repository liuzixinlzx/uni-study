package com.lzx.uniserver.tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtil {
	private static final String SECRET = "admin";

	private static Logger logger = LoggerFactory.getLogger(TokenUtil.class.getClass());

	/**
	 * 
	 * @Title: createJwtToken @Description: TODO(生成Token) @param @param id
	 *         编号 @param @return 参数 @return String 返回类型 @throws
	 */
	public static String createJwtToken(String id) {
		String issuer = "www.courier.com";
		String subject = "";
		long ttlMillis = 3600000 * 24 * 30; // 30天
		//long ttlMillis = 60000 * 10; // 10分钟
		return createJwtToken(id, issuer, subject, ttlMillis);
	}

	/**
	 * 
	 * @Title: createJwtToken @Description: TODO(生成Token) @param @param id
	 *         编号 @param @param issuer 该JWT的签发者，是否使用是可选的 @param @param subject
	 *         该JWT所面向的用户，是否使用是可选的 @param @param ttlMillis 签发时间
	 *         （有效时间，过期会报错） @param @return 参数 @return String 返回类型 @throws
	 */
	public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {
		// id，issuer，subject，ttlMillis都是放在payload中的，可根据自己的需要修改
		// 签名的算法
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 当前的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 签名算法的秘钥，解析token时的秘钥需要和此时的一样
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// 构造
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		logger.info("---token生成---");
		// 给token设置过期时间
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			logger.info("过期时间：" + exp);
			builder.setExpiration(exp);
		}

		// 压缩
		return builder.compact();
	}

	/**
	 * 
	 * @Title: parseJWT @Description: TODO(解析) @param @param jwt @param @return
	 *         参数 @return Claims 返回类型 @throws
	 */
	public static Claims parseJWT(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(jwt)
				.getBody();
		return claims;
	}
}
