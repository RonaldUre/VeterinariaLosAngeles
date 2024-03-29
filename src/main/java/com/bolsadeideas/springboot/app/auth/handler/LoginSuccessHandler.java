package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

//parte de la configuracion de seguridad de spring
//se usa para dar un mensaje de exito de inicio de sesion
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashMapManagger = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();

		flashMap.put("success", "Hola, " + authentication.getName() + " has iniciado sesion con exito");

		flashMapManagger.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info("El usuario '"+authentication.getName()+"' ha iniciadao sesion con exito");
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
