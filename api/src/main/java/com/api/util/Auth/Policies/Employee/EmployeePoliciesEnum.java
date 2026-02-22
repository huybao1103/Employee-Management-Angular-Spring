package com.api.util.Auth.Policies.Employee;

import com.api.util.Auth.Policies.IPermissionPolicy;

import static com.api.util.Auth.Policies.Employee.EmployeePolicies.*;

public enum EmployeePoliciesEnum implements IPermissionPolicy {
    CAN_GET_LIST(VIEW),
    CAN_GET_DETAIL(VIEW),
    CAN_SAVE(VIEW, EDIT),
    CAN_REMOVE(VIEW, EDIT, REMOVE);

    private final String[] claims;

    EmployeePoliciesEnum(String... claims) {
        this.claims = claims;
    }

    public String[] getClaims() {
        return claims;
    }
}
