<%--
  Created by IntelliJ IDEA.
  User: Pblues
  Date: 5/4/2024
  Time: 9:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Adduser</title>
    <style>
        .container {
            display: flex;
            min-height: 100vh;
            align-items: center;
            justify-content: center;
        }

        .field {
            margin-bottom: 10px;
        }

        label {
            display: block;
        }

        .more {
            display: flex;
        }

        .gender-item {
            display: flex;
            margin: 0 5px;
        }

        .valid {
            color: red;
            width: 200px;
        }
    </style>
</head>
<body>
<div class="container">
    <form action="<%=request.getContextPath()%>/adduser" method="post">
        <div class="field">
            <label for="fullName">Nhập tên người dùng</label>
            <input type="text" name="fullName" id="fullName" oninput="checkName()">
            <div class="valid" id="resultValidName"></div>
        </div>
        <div class="field">
            <label for="phoneNumber">Nhập số điện thoại</label>
            <input type="text" name="phoneNumber" id="phoneNumber" oninput="checkPhoneNumber()">
            <div class="valid" id="resultValidPhoneNumber"></div>
        </div>
        <div class="field">
            <label for="email">Nhập email</label>
            <input type="email" name="email" id="email" oninput="checkEmail()">
            <div class="valid" id="resultValidEmail"></div>
        </div>
        <div class="field">
            <label for="password">Nhập mật khẩu</label>
            <input type="password" name="password" id="password" oninput="checkPassword()">
            <div class="valid" id="resultValidPassword"></div>
        </div>
        <div class="field">
            <label for="rePassword">Nhập lại mật khẩu</label>
            <input type="password" name="rePassword" id="rePassword" oninput="checkRePassword()">
            <div class="valid" id="resultValidRePassword"></div>
        </div>
        <div class="field more">
            <div class="gender-item">
                <label for="male">Nam</label>
                <input type="radio" name="gender" value="1" id="male">
            </div>
            <div class="gender-item">
                <label for="female">Nữ</label>
                <input type="radio" name="gender" value="0" id="female">
            </div>
            <div class="field">
                <select name="role" id="role">
                    <option value="admin">Admin</option>
                    <option value="normalUser">Người dùng</option>
                </select>
            </div>
        </div>
        <div class="field">
            <button type="submit">Thêm người dùng</button>
        </div>
        <div><%=request.getAttribute("result")!=null?request.getAttribute("result"):""%></div>
    </form>
    <script>
        function checkName() {
            let name = document.getElementById("fullName").value;
            let regex = /^.+$/;
            if (!name.match(regex)) {
                document.getElementById("resultValidName").textContent = "Tên không được bỏ trống"
            } else {
                document.getElementById("resultValidName").textContent = " "
            }
        }

        function checkPhoneNumber() {
            let phoneNumber = document.getElementById("phoneNumber").value;
            let regex = /^0\d{2}\d{3}\d{4}$/;
            if (!phoneNumber.match(regex)) {
                document.getElementById("resultValidPhoneNumber").textContent = "Số điện thoại không hợp lệ"
            } else {
                document.getElementById("resultValidPhoneNumber").textContent = " "
            }
        }

        function checkEmail() {
            let email = document.getElementById("email").value;
            let regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!email.match(regex)) {
                document.getElementById("resultValidEmail").textContent = "Email không hợp lệ"
            } else {
                document.getElementById("resultValidEmail").textContent = " "
            }
        }

        function checkPassword() {
            let pw = document.getElementById("password").value;
            // >8 ký tự, ít nhất 1 Hoa, 1 ký tự đặc biệt
            let regex = /^(?=.*[A-Z])(?=.*[\W_])(?=.*[a-z]).{8,}$/;
            if (!pw.match(regex)) {
                document.getElementById("resultValidPassword").textContent = "Mật khẩu bao gồm ít nhất 8 ký tự, có ít nhất 1 ký tự in hoa và 1 ký tự đặc biệt"
            } else {
                document.getElementById("resultValidPassword").textContent = " "
            }
        }

        function checkRePassword() {
            let pw = document.getElementById("password").value;
            let rePw = document.getElementById("rePassword").value;
            if (pw !== rePw) {
                document.getElementById("resultValidRePassword").textContent = "Mật khẩu không khớp"
            } else {
                document.getElementById("resultValidRePassword").textContent = ""
            }
        }
    </script>
</div>
</body>
