package eu.reply.whitehall.opendata.security;




import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import eu.reply.whitehall.domain.nodes.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
* @author mh
* @since 07.03.11
*/
public class OpenUserDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final User user;

    public OpenUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
    	User.Roles[] roles = user.getRole();
        if (roles ==null) return Collections.emptyList();
        return Arrays.<GrantedAuthority>asList(roles);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}