package com.example.demo.model.utilities;

public class Constant {
    private Constant() {
    }
    public static final String ADMIN_ROLE_ID = "2";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String SAVE_SUCCESS = "lưu thành công";
    public static final String DELETE_SUCCESS = "xóa thành công";
    public static final String UPDATE_SUCCESS = "cập nhật thành công";
    public static final String REFRESH_SUCCESS = "làm mới thành công";
    public static final String ADD_FAVORITE_SUCCESS = "Đã thêm vào danh mục yêu thích";
    public static final String DELETE_FAVORITE_SUCCESS = "Đã xóa khỏi danh mục yêu thích";
    public static class STATUS_STYLES {
        public static final String ACTIVE = "A";
        public static final String INACTIVE = "I";
        public static final String DELETED = "D";
    }

    public static class STATUS_PAYMENT {
        public static final String WAITING = "W";
        public static final String PENDING = "P";
        public static final String SUCCESS = "S";
        public static final String CANCEL = "C";
        public static final String REJECT = "R";
        public static final String FINISH = "F";
        public static final String TRANSPORTING = "T";
        public static final String ROLLBACK = "RB";

    }

    public static class STATUS_HISTORY {
        public static final String WAITING = "waiting";
        public static final String COMPLETED = "completed";
    }

    public static class ICON {
        public static final String SHIPPING = "fa-truck";
        public static final String PAYMENT = "fa-credit-card";
        public static final String DONE = "fa-check";
        public static final String PENDING = "fa-clock";
        public static final String CANCEL = "fa-times";
        public static final String REJECT = "fa-ban";
        public static final String SUCCESS = "fa-check-circle";
        public static final String TRANSPORTING = "fa-truck";
        public static final String ORDER = "fa-shopping-cart";
        public static final String TASK = "fa-circle";
    }
    public static class PAYMENT_TYPE {
        public static final String CART = "C";
        public static final String PRODUCT = "P";
    }
    public static class PAYMENT_STATUS {
            public static final String NOT_PAID = "NP";
            public static final String PAID = "P";
            public static final String SUCCESS = "S";
    }

    public static class CUSTOMER_STATUS {
        public static final String ACTIVE = "A";
        public static final String INACTIVE = "I";
        public static final String LOCKED = "L";
    }
}
