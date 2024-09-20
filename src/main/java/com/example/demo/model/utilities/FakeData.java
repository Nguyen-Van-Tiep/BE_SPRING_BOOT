package com.example.demo.model.utilities;

import com.example.demo.entity.ShippingHistory;
import com.example.demo.enums.OrderEnum;

import java.time.LocalDateTime;
import java.util.List;

public class FakeData {

    public static List<ShippingHistory> getParentShippingHistoryTC(String username, Integer billId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(1, billId,1, OrderEnum.DONE.getValue(), username, username, "Đặt hàng", "Đơn hàng đã được đặt thành công", null, dateTime, dateTime),
                new ShippingHistory(2, billId,2, OrderEnum.PENDING.getValue(), username, username, "Đang xử lý", "Đơn hàng đang chờ xác nhận", null, dateTime, dateTime),
                new ShippingHistory(3, billId,3, OrderEnum.PENDING.getValue(), username, username, "Đang giao hàng", "Đơn hàng đã được chuyển đến đơn vị vận chuyển và đang trên đường tới khách hàng", null, dateTime, dateTime),
                new ShippingHistory(4, billId,4, OrderEnum.PENDING.getValue(), username, username, "Đã nhận hàng", "Khách hàng đã nhận được hàng", null, dateTime.plusDays(3).plusHours(5), dateTime));
                //new ShippingHistory(5, billId,5, OrderEnum.PENDING.getValue(), username, username, "Hoàn thành", "Đơn hàng đã được thanh toán, giao hàng thành công và hoàn tất", null, dateTime, dateTime)
//        );
    }

    public static List<ShippingHistory> getParentShippingHistory(String username, Integer billId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đặt hàng", "Đơn hàng đã được đặt thành công", null, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.PENDING.getValue(), username, username, "Đang xử lý", "Đơn hàng đang chờ xác nhận", null, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.PENDING.getValue(), username, username, "Đang giao hàng", "Đơn hàng đã được chuyển đến đơn vị vận chuyển và đang trên đường tới khách hàng", null, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.PENDING.getValue(), username, username, "Đã nhận hàng", "Khách hàng đã nhận được hàng", null, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.PENDING.getValue(), username, username, "Hoàn thành", "Đơn hàng đã được thanh toán, giao hàng thành công và hoàn tất", null, dateTime, dateTime)
        );
    }

    public static List<ShippingHistory> getChildPENDINGTC(String username, Integer billId, int parentId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "kiểm tra tồn kho", "Sản phẩm còn hàng trong kho", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang đóng gói", "Sản phẩm đang được đóng gói để chuẩn bị cho vận chuyển", parentId, dateTime, dateTime),
                //new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Chờ xác nhận thanh toán", "Đơn hàng đang chờ xác nhận thanh toán từ cổng thanh toán hoặc ngân hàng", parentId, dateTime.plusHours(2), dateTime.plusHours(2)),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang xác nhận đơn hàng", "Đơn hàng đang được xác nhận bởi nhân viên", parentId, dateTime, dateTime)
        );
    }

    public static List<ShippingHistory> getChildPAYMENTTC(String username, Integer billId, int parentId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang xử lý", "Đơn hàng đang được xử lý và chuẩn bị để đóng gói", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang đóng gói", " Sản phẩm đang được đóng gói để chuẩn bị cho quá trình vận chuyển", parentId, dateTime, dateTime)
        );
    }

    public static List<ShippingHistory> getChildPENDING(String username, Integer billId, int parentId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "kiểm tra tồn kho", "Sản phẩm còn hàng trong kho", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang đóng gói", "Sản phẩm đang được đóng gói để chuẩn bị cho vận chuyển", parentId, dateTime, dateTime),
                //new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Chờ xác nhận thanh toán", "Đơn hàng đang chờ xác nhận thanh toán từ cổng thanh toán hoặc ngân hàng", parentId, dateTime.plusDays(2).plusHours(2), dateTime.plusDays(2).plusHours(2)),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang xác nhận đơn hàng", "Đơn hàng đang được xác nhận bởi nhân viên", parentId, dateTime, dateTime)
        );
    }

    public static List<ShippingHistory> getChildSHIPPING(String username, Integer billId, int parentId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đã chuyển cho bên vận chuyển", "Đơn hàng đã được chuyển cho đơn vị vận chuyển", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đang giao hàng", "Đơn hàng đã được chuyển đến đơn vị vận chuyển và đang trên đường tới khách hàng", parentId, dateTime, dateTime)
        );
    }

    public static List<ShippingHistory> getChildRECEIVED(String username, Integer billId, int parentId) {
        LocalDateTime dateTime = LocalDateTime.now();
        username = SercurityUtils.getCurrentUser();
        return List.of(
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Đã nhận hàng", "Khách hàng đã nhận được hàng", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Kiểm tra hàng hóa", "Khách hàng đang kiểm tra hàng hóa", parentId, dateTime, dateTime),
                new ShippingHistory(null, billId,null, OrderEnum.DONE.getValue(), username, username, "Hoàn thành", "Đơn hàng đã được thanh toán, giao hàng thành công và không có yêu cầu trả hàng hoặc hoàn tiền", parentId, dateTime, dateTime)
        );
    }

}
