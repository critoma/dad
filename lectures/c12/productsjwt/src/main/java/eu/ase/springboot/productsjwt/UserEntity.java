package eu.ase.springboot.productsjwt;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class UserEntity {
   private String username;
   private String password;
   private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
   
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
      return grantedAuthoritiesList;
   }
   public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
      this.grantedAuthoritiesList = grantedAuthoritiesList;
   }
   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
}
