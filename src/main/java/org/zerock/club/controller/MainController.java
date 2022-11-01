package org.zerock.club.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.club.security.dto.ClubAuthMemberDTO;
import org.zerock.club.security.util.JWTUtil;

import javax.servlet.ServletException;
import java.io.IOException;

@RestController
@Log4j2
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final JWTUtil jwtUtil;

    @GetMapping(value = "/socialLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> socialLogin(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) throws Exception {

        System.out.println(clubAuthMember.getEmail());
        String accessToken = jwtUtil.generateToken(clubAuthMember.getEmail());
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

}
