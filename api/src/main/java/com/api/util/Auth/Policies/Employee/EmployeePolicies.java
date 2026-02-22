package com.api.util.Auth.Policies.Employee;

import java.util.List;
import java.util.Map;

public final class EmployeePolicies {
    private EmployeePolicies() {}

    // Define permission codes
    public static final String VIEW = "1.v";
    public static final String EDIT = "1.e";
    public static final String REMOVE = "1.r";

    // Define policy names
    private static final String CAN_GET_LIST_POLICY = "CAN_GET_LIST";
    private static final String CAN_GET_DETAIL_POLICY = "CAN_GET_DETAIL";
    private static final String CAN_SAVE_POLICY = "CAN_SAVE";
    private static final String CAN_REMOVE_POLICY = "CAN_REMOVE";

    // Map policies to required permissions
    public static final String[] CAN_GET_LIST = { VIEW };
    public static final String[] CAN_GET_DETAIL = { VIEW };
    public static final String[] CAN_SAVE = { VIEW, EDIT };
    public static final String[] CAN_REMOVE = { VIEW, EDIT, REMOVE };

    // Map policy names to their required permissions
    public static final Map<String, String[]> ALL_POLICIES_FOR_AUTH_GUARD = Map.of(
            CAN_GET_LIST_POLICY, CAN_GET_LIST,
            CAN_GET_DETAIL_POLICY, CAN_GET_DETAIL,
            CAN_SAVE_POLICY, CAN_SAVE,
            CAN_REMOVE_POLICY, CAN_REMOVE
    );

    // List of all policy names for easy retrieval
    public static final List<Map<String, String>> ALL_EMP_POLICIES_NAME = List.of(
            Map.of("View", VIEW),
            Map.of("Edit", EDIT),
            Map.of("Remove", REMOVE)
    );
}
