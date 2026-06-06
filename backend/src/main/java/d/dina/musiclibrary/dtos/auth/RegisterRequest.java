package d.dina.musiclibrary.dtos.auth;

import d.dina.musiclibrary.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String username;

    private String password;
}