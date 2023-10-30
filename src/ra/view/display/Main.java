package ra.view.display;

import ra.config.WriteReadFile;
import ra.model.Users;

public class Main {
    public static void main(String[] args) {
        Users userLogin = new WriteReadFile<Users>().readFile(WriteReadFile.PATH_USER_LOGIN);
        if (userLogin != null) {
            new Home().checkRoleLogin(userLogin);
        } else {
            new Home().menuHome();
        }
    }
}
