package ra.utils;

import ra.constant.RoleName;

public class Utils {
    public static RoleName validateRoleInput(String role) {
        if (role.equals(RoleName.USER.name())) {
            return RoleName.USER;
        } else if (role.equals(RoleName.ADMIN.name())) {
            return RoleName.ADMIN;
        }
        return null;
    }
}
