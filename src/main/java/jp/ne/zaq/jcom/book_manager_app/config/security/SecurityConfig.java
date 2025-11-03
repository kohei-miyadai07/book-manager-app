package jp.ne.zaq.jcom.book_manager_app.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jp.ne.zaq.jcom.book_manager_app.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        // CSRF はデフォルトで有効（Thymeleaf 側でトークンを埋める）
        .csrf(Customizer.withDefaults())

        // セッション固定攻撃対策: migrateSession
        .sessionManagement(session -> session
            .sessionFixation().migrateSession()
        )

        // 認可設定
        .authorizeHttpRequests(auth -> auth
            // 静的リソース・ログインページは公開
            .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/login").permitAll()

            // ホーム・一覧・詳細は READ または WRITE
            .requestMatchers("/", "/books", "/books/{bookId}/details").hasAnyAuthority("READ", "WRITE")

            // 作成/編集/更新/削除は WRITE のみ
            .requestMatchers("/books/new", "/books/new/register-book").hasAuthority("WRITE")
            .requestMatchers(
            		"/books/{bookId}/edit",
            		"/books/{bookId}/edit/update-book",
            		"/books/{bookId}/edit/delete-book")
            .hasAuthority("WRITE")

            // その他は認証済み
            .anyRequest().authenticated()
        )

        // フォームログイン設定
        .formLogin(form -> form
            .loginPage("/login")
            .usernameParameter("userName")
            .passwordParameter("password")
            .defaultSuccessUrl("/", true)
            .permitAll()
        )

        // ログアウトはデフォルトで有効
        .logout(logout -> logout.permitAll());

    return http.build();
	}

	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
