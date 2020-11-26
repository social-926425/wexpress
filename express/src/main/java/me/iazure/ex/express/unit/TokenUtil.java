package me.iazure.ex.express.unit;
/**
 * @author clay
 * @version 1.0
 * @mail 20932067@zju.edu.cn
 * @date 2020/11/25 21:51
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import me.iazure.ex.express.entity.PresonInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final long EXPIER_TIME = 1800000;


    public static String getToken(PresonInfo personInfo) {

        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIER_TIME);
//            私钥加密
            Algorithm algorithm = Algorithm.HMAC256("4561245781215");
            Map<String, Object> header = new HashMap<String, Object>(3);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            header.put("studentid", personInfo.getStudentId());
            String token = JWT.create()
                    .withHeader(header)
                    .withClaim("password", personInfo.getPassword())
                    .withClaim("studentid", personInfo.getStudentId())
                    .withExpiresAt(date)
                    .sign(algorithm);

            return token;
        } catch (Exception e) {
            return null;
        }
    }
}
