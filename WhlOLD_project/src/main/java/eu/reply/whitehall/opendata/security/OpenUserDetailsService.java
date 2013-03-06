package eu.reply.whitehall.opendata.security;


import org.krams.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


public interface OpenUserDetailsService extends UserDetailsService {
    @Override
    OpenUserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException;

    User getUserFromSession();

    @Transactional
    User register(String login, String name, String password);


}

