package md.donesk.trendshop.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private final List<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, Long id, String email, List<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }


}