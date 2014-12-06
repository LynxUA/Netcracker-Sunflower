package com.sunflower;

import com.sunflower.exceptions.UnknownStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denysburlakov on 05.12.14.
 */
public enum UserGroup {
    GUEST(0),
    CUSTOMER(1),
    ADMIN(2),
    /** Customer support engineer*/
    CSE(3),
    /** Provision engineer*/
    PE(4),
    /** Installation engineer*/
    IE(5);
    int status;
    private UserGroup(int i) {
        this.status = i;
    }
    private static final Map<Integer, UserGroup> intToTypeMap = new HashMap<Integer, UserGroup>();
    static {
        for (UserGroup type : UserGroup.values()) {
            intToTypeMap.put(type.status, type);
        }
    }

    public static UserGroup fromInt(int i) throws UnknownStatusException {
        UserGroup type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            throw new UnknownStatusException();
        return type;
    }

}
