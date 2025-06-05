package com.example.expense.expense_tracker.util;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
	public class JwtConfig {

//    @Bean
//    public SecretKey secretKey() {
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    }
	   private static final String SECRET_KEY_BASE64 = "dc79a220099deb3cb52b7b649578b78d90aacaf2f18b904b781961c825da61516abd8408af17964bba564b2eadc66a532f861fe6952ba6b6ef3e3df7408c76a2f6d22498f0db01be9e7827e787c6af71d46350c54de83d7b639148646a30ecbeefac85dfa190b82c2b4593c85958041c2133812038eb84c468ff65b07cfcae63592d2410a1c80a15bc8c173c8dffdc091984d569b4e394a6aeb757793b8b7b67e12194c6224b0fecac6bfd6571f2df4a1b44654b469191f5bbe26c96ba6cc73126632b2049739cc7f2d640195d24e3bbc765ece8fec896509dc36d4cf2531690ff7cbd099abe245f1bd51b98cb4ed4a7209d480092544bfe24a885ec30a99071";

	    @Bean
	    public SecretKey secretKey() {
	        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
	        return Keys.hmacShaKeyFor(decodedKey);
	    }

	}


