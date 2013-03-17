package eu.reply.whitehall.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.reply.whitehall.domain.nodes.User;
import eu.reply.whitehall.opendata.security.OpenUserDetails;
import eu.reply.whitehall.opendata.security.OpenUserDetailsService;
import eu.reply.whitehall.repository.OpenNodeRepository;
import eu.reply.whitehall.repository.RoleRepository;
import eu.reply.whitehall.repository.UserRepository;

@Service
public class UserService implements OpenUserDetailsService{

	@Autowired
    private Neo4jOperations template;
	
	@Autowired
	private UserRepository userRepository;

    @Override
    public OpenUserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        final User user = findByLogin(login);
        if (user==null) throw new UsernameNotFoundException("Username not found: "+login);
        return new OpenUserDetails(user);
    }

    private User findByLogin(String login) {
        return template.lookup(User.class,"login",login).to(User.class).single();
    }

    @Override
    public User getUserFromSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof OpenUserDetails) {
            OpenUserDetails userDetails = (OpenUserDetails) principal;
            return userDetails.getUser();
        }
        return null;
    }


    @Override
    @Transactional
    public User register(String login, String name, String password) {
    	User found = null;
    	try{
        	found = findByLogin(login);
        }catch(Exception e){ }
        if (found!=null) throw new RuntimeException("Login already taken: "+login);
        if (name==null || name.isEmpty()) throw new RuntimeException("No name provided.");
        if (password==null || password.isEmpty()) throw new RuntimeException("No password provided.");
        User user=template.save(new User(login,name,password,User.Roles.ROLE_USER));
        setUserInSession(user);
        return user;
    }

    void setUserInSession(User user) {
        SecurityContext context = SecurityContextHolder.getContext();
        OpenUserDetails userDetails = new OpenUserDetails(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),userDetails.getAuthorities());
        context.setAuthentication(authentication);
    }

	public Neo4jOperations getTemplate() {
		return template;
	}

	public void setTemplate(Neo4jOperations template) {
		this.template = template;
	}

	
	/**
	 * quit all the nodes Users
	 */
	public boolean quit() {	
		userRepository.deleteAll();
		return true;
	}
}
