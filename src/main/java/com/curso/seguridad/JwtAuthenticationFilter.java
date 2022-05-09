package com.curso.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//Obtenemos el token de la solicitud Http
		String token = obtenerJWTdeLaSolicitud(request);
		
		//Validamos el token
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			// Obtenemos el username del token
			String username = jwtTokenProvider.obtenerUsernameDelJWT(token);
			
			//Cargamos el usuario asociado al token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//Establecemos la seguridad
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		// Valida el filtro
		filterChain.doFilter(request, response);
	}
	
	//Bearer (formato que permite la autorización de un usuario) token de acceso
	private String obtenerJWTdeLaSolicitud(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
			return bearerToken.substring(7,bearerToken.length());
		
		return null;
	}

}
