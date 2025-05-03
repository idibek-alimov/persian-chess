package tj.alimov.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.alimov.userservice.config.JwtService;
import tj.alimov.userservice.dto.AuthenticationResponseDto;
import tj.alimov.userservice.exception.DuplicateUsernameException;
import tj.alimov.userservice.model.User;
import tj.alimov.userservice.repository.UserRepository;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;

    // CREATE
    @Transactional
    public AuthenticationResponseDto registerUser(User user){

        user = saveUser(user);

        String access_token = jwtService.generateToken(user.getUsername(),30);
        String refresh_token = jwtService.generateToken(user.getUsername(),90);

        return AuthenticationResponseDto
                .builder()
                .access_token(access_token)
                .refresh_token(refresh_token)
                .build();
    }
    private User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            return  userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception){
            throw new DuplicateUsernameException("Username already taken");
        }
    }

    // READ
    public void authenticateUser(User user){

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(),user.getPassword());
        System.out.println("authentication");
        System.out.println(authentication);
        Authentication authenticationToken = authenticationManager.authenticate(authentication);
        System.out.println("authentication manager response");
        System.out.println(authenticationToken);

        Authentication authenticationToken1 = authenticationProvider.authenticate(authentication);
        System.out.println("authentication provider response");
        System.out.println(authenticationToken1);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User details ");
        System.out.println(userDetails);
    }

    // UPDATE

    // DELETE
}
