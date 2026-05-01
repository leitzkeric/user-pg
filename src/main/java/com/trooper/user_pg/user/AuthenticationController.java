package com.trooper.user_pg.user;

import jakarta.validation.Valid;
import com.trooper.user_pg.infra.security.DadosTokenJwt;
import com.trooper.user_pg.infra.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity efetuarLogin (@RequestBody @Valid LoginDTO loginDTO) {
        var token = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var authentication = manager.authenticate(token);
        var tokenJwt = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJwt(tokenJwt));
    }
}
