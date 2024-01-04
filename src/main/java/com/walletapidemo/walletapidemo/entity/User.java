package com.walletapidemo.walletapidemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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

@Table(name="customers_tbl",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "email")
       })

public class User implements UserDetails{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false,name = "user_firstname", length = 100)
  @NotBlank(message = "firstname cannot be blank")
  @NotEmpty
  private String firstname;

  @Column(nullable=false,name = "user_lastname", length = 100)
  @NotBlank(message = "lastname cannot be blank")
  @NotEmpty
  private String lastname;

  @Column(nullable=false, name = "user_customernumber", length=10)
  @NotBlank(message = "customer id cannot be blank")
  @NotEmpty
  @Size(min = 10, max = 10, message = "customer id must be 10 characters")
  private String customernumber;
  
  @Column(nullable = false, name = "user_email", length = 250)
  @NotBlank(message = "email cannot be blank")
  @NotEmpty
  @Email
  private String email;

  @Column(name = "user_pin", length = 300)
  @NotEmpty
  private String pin;

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
  return pin;
}

@Override
public String getUsername() {
return customernumber;
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
