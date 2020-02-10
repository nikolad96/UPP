
package bzb.server.controller;

import bzb.server.model.User;
import bzb.server.security.TokenUtils;
import bzb.server.security.auth.JwtAuthenticationRequest;
import bzb.server.security.auth.JwtResponse;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IdentityService identityService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response, HttpServletRequest hr){
        System.out.println("USAO U LOGIN");

        System.out.println(authenticationRequest.getPassword());
        System.out.println(authenticationRequest.getUsername());
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        User user =  (User) authentication.getPrincipal();

        identityService.setAuthenticatedUserId(authenticationRequest.getUsername());
        System.out.println(identityService.getCurrentAuthentication().getUserId());
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt = tokenUtils.generateToken(authentication);
        int expiresIn = 3600;

        return ResponseEntity.ok(new JwtResponse(jwt,user.getUsername(),user.getAuthorities()));
    }



    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
