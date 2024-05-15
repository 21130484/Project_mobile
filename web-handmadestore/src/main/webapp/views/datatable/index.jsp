<%@ page import="model.bean.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="model.service.OrderService" %>
<%@ page import="model.bean.User" %>
<%@ page import="model.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<%
    List<Order> orders = OrderService.getInstance().getAllOrder();
%>
<head>
    <meta charset="UTF-8">
    <title>Datatable?</title>
    <!--https://datatables.net/download/-->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/v/bs4-4.6.0/jq-3.7.0/dt-2.0.6/datatables.min.css" rel="stylesheet">
    <!--https://www.bootstrapcdn.com/-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/v/bs4-4.6.0/jq-3.7.0/dt-2.0.6/datatables.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.min.js"></script>
    <!--https://releases.jquery.com/-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"
            integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
            crossorigin="anonymous"></script>
</head>
<body>
<table id="data" class="table table-striped table-bordered" style="width:100%">
    <thead>
    <tr>
        <th>Mã Đơn Hàng</th>
        <th>Mã Khách Hàng</th>
        <th>Tên Khách Hàng</th>
        <th>Địa Chỉ Giao</th>
        <th>Ngày Đặt Hàng</th>
        <th>Tổng Tiền Hóa Đơn</th>
        <th>Phí Vận Chuyển</th>
        <th>Tổng Hóa Đơn</th>
        <th>Trạng Thái</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Order o : orders) {
            if (o != null) {
                User customer = UserService.getInstance().getUserById(o.getUserId() + "");
    %>
    <tr class="text-center" style=" cursor: pointer;"
        onclick="submit_showOrderDetails(event,this)"
        id="<%=o.getId()%>"
    >
        <td><%=o.getId()%>
        </td>
        <td><%=customer.getId()%>
        </td>
        <td class="text-start"><%=customer.getName()%>
        </td>
        <td class="text-start"><%=o.getAddress()%>
        </td>
        <td><%=o.getOrderDate()%>
        </td>
        <td><%=o.getTotalPrice()%>
        </td>
        <td><%=o.getShippingFee()%>
        </td>
        <td><%=o.getTotalPrice() + o.getShippingFee()%>
        </td>
        <td
                <%!
                    String backgroundColor = "";
                    String sttvalue = "";
                %>
                <%
                    if (o.isDeliveringOrder()) {
                        backgroundColor = "#0171d3";
                        sttvalue = "Đang giao";
                    } else if (o.isWaitConfirmOrder()) {
                        backgroundColor = "#ffcc00";
                        sttvalue = "Cần xác nhận";
                    } else if (o.isCanceledOrder()) {
                        backgroundColor = "#ff0000";
                        sttvalue = "Đã hủy";
                    } else if (o.isSucccessfulOrder()) {
                        backgroundColor = "#4d8a54";
                        sttvalue = "Thành công";
                    }%>
                style="background-color: <%=backgroundColor%>; color: #ffffff"
        ><%=sttvalue%>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
<script>
    $(document).ready(function () {
        // $('#data').dataTable();
        // new DataTable('#data');
        // $('#data').dataTable({
        //     order: [[3, 'desc'], [2, 'asc']]
        // });
        $(document).ready(function() {
            $('#data').DataTable({
                "paging": true, // Phân trang
                "searching": false, // Không có tính năng tìm kiếm
                "lengthChange": false // Không cho phép thay đổi số lượng bản ghi trên mỗi trang
            });
        });

    })
</script>
</html>