package jp.ne.zaq.jcom.book_manager_app;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {
	
	@Test
	public void testBCrypt() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

		String rawPassword1 = "read";
        String encodedPassword1 = encoder.encode(rawPassword1);
        System.out.println("元の文字列: " + rawPassword1);
        System.out.println("エンコード結果: " + encodedPassword1);

        String rawPassword2 = "write";
        String encodedPassword2 = encoder.encode(rawPassword2);
        System.out.println("元の文字列: " + rawPassword2);
        System.out.println("エンコード結果: " + encodedPassword2);
	}

}
