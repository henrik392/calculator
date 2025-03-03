package edu.ntnu.idi.idatt.server

import edu.ntnu.idi.idatt.server.service.UserContextService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().permitAll()
            }

        return http.build()
    }
}

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Allow requests from your frontend origin
        config.allowedOrigins = listOf("http://localhost:5173")

        // Allow common HTTP methods
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")

        // Allow necessary headers
        config.allowedHeaders = listOf("Origin", "Content-Type", "Accept", "Authorization")

        // Allow credentials if needed
        config.allowCredentials = true

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}

@Component
class UserContextInterceptor(private val userContextService: UserContextService) :
    HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(UserContextInterceptor::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        // Uses a simple header for now, to be replaced with token parsing
        // Default to 1 for development
        val userId = request.getHeader("X-User-Id")?.toLongOrNull() ?: 1L
        userContextService.setCurrentUserId(userId)
        logger.info("Set user id to $userId")
        return true
    }
}

@Configuration
class WebConfig(private val userContextInterceptor: UserContextInterceptor) :
    WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userContextInterceptor)
            .addPathPatterns("/api/**")
    }
}