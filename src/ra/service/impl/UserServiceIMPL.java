package ra.service.impl;

import ra.config.Config;
import ra.config.WriteReadFile;
import ra.constant.RoleName;
import ra.model.Users;
import ra.service.IUserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService {

    static WriteReadFile<List<Users>> config = new WriteReadFile<>();
    public static List<Users> usersList;

    static {
        usersList = config.readFile(Config.PATH_USER);
        if (usersList == null) {
            usersList = new ArrayList<>();
            usersList.add(new Users(1, "ADMIN", "admin", "11111111", "admin@gmail.com", true, RoleName.ADMIN));
            new UserServiceIMPL().updateData();
        }
    }

    @Override
    public List<Users> findAll() {
        return usersList;
    }

    @Override
    public void save(Users users) {
        // kiem tra users co ton tai trong usersList ko
        if (findByID(users.getId()) == null) {  // neu chua ton tai trong danh sach
            usersList.add(users); // thi them moi
            updateData();
        } else {
            usersList.set(usersList.indexOf(users), users);  // neu da ton tai thi set lai users (update)
            updateData();
        }
    }

    @Override
    public void update(Users users) {
        updateData();
    }

    @Override
    public void delete(int id) {
        usersList.remove(findByID(id));
        updateData();
    }

    @Override
    public Users findByID(int id) {
        for (Users users : usersList) {
            if (users.getId() == id) {
                return users;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Users users : usersList) {
            if (users.getId() > idMax) {
                idMax = users.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.PATH_USER, usersList);
    }

    @Override
    public boolean existUsername(String username) {
        for (Users users : usersList) {
            if (users.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String username) {
        for (Users users : usersList) {
            if (users.getEmail().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Users checkLogin(String username, String password) {
        for (Users users : usersList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                return users;
            }
        }
        return null;
    }

    @Override
    public List<Users> findByName(String name) {
        return null;
    }
}
