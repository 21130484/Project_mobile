<%@ page import="org.w3c.dom.stylesheets.LinkStyle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.service.ImageService" %>
<%@ page import="model.bean.*" %>
<%@ page import="model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: Kien Nguyen
  Date: 12/11/2023
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>

<% Product product = (Product) request.getAttribute("productById");%>
<% List<Image> imageList = (List<Image>) request.getAttribute("listImage");%>
<% Category categoryByProduct = (Category) request.getAttribute("categoryByProduct");%>
<% String description = product.getDescription();%>
<% List<Product> relatedProduct = (List<Product>) request.getAttribute("productRelated");%>
<%List<Rate> rateList = (List<Rate>) request.getAttribute("listRate");%>



<html>
<head>
    <title>ProductDetails</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,500&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Inline+One&display=swap" rel="stylesheet">
    <style>

        <%@include file="../css/product.css"%>

    </style>

</head>
<body>
<%--Thanh điều hướng - header--%>
<%@include file="/views/MenuBar/menu.jsp" %>


<!-- Nội dung Product-details-->
<!-- Thẻ navigation : thanh chuyển hướng -->
<section class="product-details container  pt-2">

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb ">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/views/MainPage/view_mainpage/mainpage.jsp">Trang Chủ</a></li>
            <li class="breadcrumb-item"><a
                    href="product?category=<%=categoryByProduct.getId()%>"><%=categoryByProduct.getName()%>
            </a></li>
            <li class="breadcrumb-item active" aria-current="page" style="color: #e32124"><%=product.getName()%>
            </li>
        </ol>
    </nav>
    <div class="row mt-3 ">
        <%--    Phần hiển thị ảnh sản phẩm. (trái)--%>
        <!--  zoom container start-->
        <div class="xzoom-container col-lg-5 col-md-12 col-12">
            <%
                if (imageList != null && !imageList.isEmpty()) {
                    Image mainImage = imageList.get(0);
            %>
            <img class="img-fluid w-100 pb-1 xzoom large-img" id="MainImg" src="<%=mainImage.getPath()%>" alt="">
            <div class="small-img-group">
                <%
                    for (int i = 1; i < Math.min(imageList.size(), 4); i++) {
                        Image secondImage = imageList.get(i);
                %>
                <div class="small-img-col">
                    <img src="<%= secondImage.getPath()%>" width="100%" class="small-img" alt="">
                </div>
                <%
                        }
                    }
                %>

            </div>

        </div>
        <%--     Phần hiển thị thông tin chi tiết của sản phẩm (phải)--%>
        <div class="col-lg-7 col-md-12 col-12 right-pd">

            <h3 class=""><%=product.getName() %>
            </h3>
            <div class="d-flex rate-content">
                <div class="icon-rate me-3">
                    <i class="bi bi-star "></i>
                    <i class="bi bi-star"></i>
                    <i class="bi bi-star"></i>
                    <i class="bi bi-star"></i>
                    <i class="bi bi-star"></i></div>
                <a href="#rating-comment">Đánh giá của bạn</a>

            </div>
            <div class="state-pd my-2">
                <label class="me-2 gray-content">Trạng thái: </label>
                <%--                Xử lý trạng thái còn hàng , hết hàng --%>
                <%int countProduct = product.getQuantity() - product.getSoldout(); %>

                <%if (countProduct > 1) {%>
                <span>Còn hàng</span>

                <% } else { %>

                <span style="color:#e32124 ">Tạm hết hàng</span>

                <%}%>


            </div>
            <h2 class="price-pd mb-4">
                    <%=numberFormat.format(product.getSellingPrice())%>

                <div class="row mt-3">
                    <div class="quantity-pd mb-4 col-4">
                        <label class="me-2" style="font-size: 14px">Số lượng: </label>
                        <div class="qu-value">
                            <button class="pd-des m-0">-</button>
                            <input type="text" class="quantity-input p-0" value="1">
                            <button class="pd-inc m-0">+</button>
                        </div>
                    </div>


                    <button class="buy-btn col-4" style="font-size: 16px" <%=request.getAttribute("disable")%>>

                        Thêm vào
                        giỏ hàng


                    </button>


                </div>


                <hr class="mx-auto">
                <h4 class=" mt-4 mb-4 ">Chi tiết sản phẩm</h4>

                <%--          Xử lý hiển thị chi tiết sản phẩm theo từng dòng văn bản--%>
                    <%String[] lines = description.split("\\r?\\n");
                for(String line : lines) {

%>

                <p class="gray-content"><%=line%>
                </p>
                    <%}%>
        </div>
    </div>


</section>

<section id="relate" class="mt-5 ">
    <div class="container pb-4">
        <h4>Sản phẩm liên quan</h4>
        <hr class="mx-auto">

        <div class="row my-5 ">

            <%for (Product pr : relatedProduct) {%>
            <%String pathImage = ImageService.getInstance().pathImageOnly(pr.getId());%>

            <div class="col info-item mx-3">
                <div class="info-img ">
                    <img src="<%=request.getContextPath()%>/<%=pathImage%>" alt=""
                    class="img-fluid d-block mx-auto mt-2">


                    <div class="row btns w-100 mx-auto ">
                        <button type="button" class="col-6 py-2">
                            <a href="<%=request.getContextPath()%>/add-cart?actionCart=post&num=1&id=<%=pr.getId()%>">     <i class="bi bi-cart-plus"></i>
                            </a>
                        </button>
                        <button type="button" class="col-6 py-2">
                            <a href="product-detail?id=<%=pr.getId()%>"> <i class="bi bi-eye"></i> </a>
                        </button>
                    </div>
                </div>
                <div class="info-product p-3">
                    <a href="product-detail?id=<%=pr.getId()%>"
                    class="d-block text-dark text-decoration-none py-2 info-name">
                        <%=pr.getName()%>
                    </a>
                    <span class="info-price fw-bold"><%=numberFormat.format( pr.getSellingPrice()) %></span>
                    <div class="info-rating d-flex mt-1">

                        <span>(<%=rateList.size()%> đánh giá)</span>
                    </div>
                </div>

            </div>
            <%}%>


        </div>


    </div>


</section>
<%--</section>--%>

<section id="rating-comment" class="rating container mb-5">
    <h4 pb-1>Đánh giá - Bình luận </h4>
    <hr class="mx-auto">
    <!-- Các bình luận sẽ được hiển thị ở đây -->
<% for(Rate r : rateList) {%>
    <div class="d-flex flex-column mx-2 comments">
     <div class="p-1 d-flex flex-row">
         <h6 class="pe-5 m-0 "><%=UserDAO.getUserNameById(r.getUserId())%></h6>
         <p style="font-style: italic ; color: #898989 ; font-family: Arial" class="m-0"> Đã đăng vào lúc   <%=" " + r.getCreateDate()%> </p>
     </div>

        <div class=" p-1 ratings ">
            <%  int star = r.getStarRatings();
                for(int i =1 ; i <= 5; i++) {   %>
            <%if(i <= star) {%>
            <i class="bi bi-star-fill " style="color: #ffcc00"></i>
            <%}
            else {%>
            <i class="bi bi-star-fill "></i>
          <%}
                }%>
              </div>
        <p class="p-1"><%=r.getComment()%></p>
    </div>
    <%}%>



    <div class="ratings mb-3">

        <i class="bi bi-star-fill "></i>
        <i class="bi bi-star-fill "></i>
        <i class="bi bi-star-fill"></i>
        <i class="bi bi-star-fill"></i>
        <i class="bi bi-star-fill"></i>
    </div>
    <div class="d-flex comment-pd">
    <textarea class="comment-input p-2" rows="3" placeholder="Viết bình luận" maxlength="350">

    </textarea>
        <button type="button" class="submit-comment btn  btn-outline-success btn-sm shadow-sm ">Gửi</button>
    </div>





</section>

<!--    Footer-->
<%@include file="/views/Footer/footer.jsp" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>

<script>
    var MainImg = document.getElementById('MainImg');
    var smallimg = document.getElementsByClassName('small-img');
    smallimg[0].onclick = function () {
        MainImg.src = smallimg[0].src;
    }
    smallimg[1].onclick = function () {
        MainImg.src = smallimg[1].src;
    }
    smallimg[2].onclick = function () {
        MainImg.src = smallimg[2].src;
    }
    smallimg[3].onclick = function () {
        MainImg.src = smallimg[3].src;
    }


</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const incrementButton = document.querySelector(".pd-inc");
        const decrementButton = document.querySelector(".pd-des");
        const quantityInput = document.querySelector(".quantity-input");
        const addToCartLink = document.querySelector(".buy-btn");

        incrementButton.addEventListener("click", function () {
            console.log("Button + clicked");
            quantityInput.value = parseInt(quantityInput.value) + 1;
        });

        decrementButton.addEventListener("click", function () {
            console.log("Button - clicked");
            const currentValue = parseInt(quantityInput.value);

            if (currentValue > 1) {
                quantityInput.value = currentValue - 1;
            }
        });
        addToCartLink.addEventListener("click", function (event) {
            event.preventDefault();
            const productId = <%=product.getId()%>;
            const quantity = parseInt(quantityInput.value);
            const URL = `<%=request.getContextPath()%>/add-cart?actionCart=post&num=${quantity}&id=${productId}`;
            window.location.href =URL;
            quantityInput.value = 1;
        });
    });
</script>
</body>
</html>
