package com.epam.service;

import com.epam.model.User;
import com.epam.model.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author asd
 * Service handling user authorization.
 */
@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImp.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String userEmail)
            throws UsernameNotFoundException {
        logger.info(new StringBuilder("Entering name=").
                append(userEmail));
        User user = userService.initByEmail(userEmail);
        List<GrantedAuthority> authorities = buildActorAuthority(user.getUserRoles());
        logger.info(new StringBuilder("Leaving actorName=").
                append(user.getEmail().charAt(0)).
                append("*** ActorRoleCount=").append(authorities.size()));
        return buildActorForAuthentication(user, authorities);
    }

    /**
     * Converts com.epam.User to
     * org.springframework.security.core.userdetails.User
     *
     * @param user        converting object
     * @param authorities list of user authority in system
     * @return converted object
     */
    private org.springframework.security.core.userdetails.User
    buildActorForAuthentication(final User user, final List<GrantedAuthority> authorities) {
        logger.info("Convertion com.homework.User to" +
                "org.springframework.security.core.userdetails.User");
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), true, true, true, true, authorities);
    }

    /**
     * Builds actor authority
     *
     * @param userRoles - list of actor roles in system
     * @return list of actor authority in system
     */
    private List<GrantedAuthority> buildActorAuthority(final Set<UserRole> userRoles) {
        logger.info(new StringBuilder("Entering userRolesCount=").
                append(userRoles.size()));
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        // Build actor's authorities
        setAuths.addAll(userRoles.stream().map(actorRole ->
                new SimpleGrantedAuthority(actorRole.getRole())).collect(Collectors.toList()));
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        logger.info(new StringBuilder("Leaving ResultCount=").
                append(result.size()));
        return result;
    }
}