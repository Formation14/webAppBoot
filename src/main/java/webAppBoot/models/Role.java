package webAppBoot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;

    private String role;

    @Override
    public String toString() {
        return role.substring(5);
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}