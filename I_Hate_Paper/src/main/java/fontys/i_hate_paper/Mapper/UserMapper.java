package fontys.i_hate_paper.Mapper;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.User;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(

        );
    }

    public static User toUser(UserDTO userDTO) {
        return new User(

        );
    }
}
