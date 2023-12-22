package com.walletapidemo.walletapidemo.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// import org.apache.el.parser.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Setter
@Getter
@ToString

@Table(name="users_tbl",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })

public class User implements UserDetails{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_firstname", length = 250)
  private String firstname;

  @Column(name = "user_lastname", length = 250)
  private String lastname;

  @Column(name = "user_email", length = 250)
  private String email;

  @Column(name = "user_password", length = 250)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
 
@Override
public String getPassword(){
  return password;
}

@Override
public String getUsername() {
return email;
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

}
