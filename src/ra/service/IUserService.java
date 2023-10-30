package ra.service;

import ra.model.Users;

import java.util.List;

public interface IUserService extends IService<Users> {
    boolean existUsername(String username);

    boolean existEmail(String username);

    boolean existPhone(String username);

    Users checkLogin(String username, String password);

    List<Users> findByName(String name);
}
