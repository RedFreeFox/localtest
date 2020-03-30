<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <title>WebChat | 登陆</title>
    <link href="<%=path%>/static/source/css/login.css" rel='stylesheet' type='text/css'/>
    <script src="<%=path%>/static/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/static/plugins/layer/layer.js"></script>
</head>
<body>

<h1>WebChat</h1>
<div class="login-form">
    <div class="close"></div>
    <div class="head-info">
        <label class="lbl-1"></label>
        <label class="lbl-2"></label>
        <label class="lbl-3"></label>
    </div>
    <div class="clear"></div>
    <div class="avtar"><img src="<%=path%>/static/source/img/avtar.png"/></div>
    <form id="login-form" action="<%=path%>/user/login" method="post">
        <div class="key">
            <input type="text" id="username" name="userid" placeholder="请输入账号">
        </div>

        <div class="key">
            <input type="password" id="password" name="password" placeholder="请输入密码">
        </div>
        <div class="signin">
            <div class="aaa" style="width: 213px;float: left;margin-right: 1px">
                <input type="submit" id="submit" value="Login"
                       style="-webkit-border-bottom-left-radius:15px;-webkit-border-bottom-right-radius:0px;-webkit-border-top-left-radius: 15px"
                       onclick="return checkLoginForm()">
            </div>
            <div class="bbb" style="width: 213px;float: right;margin-right: 1px;margin-top: -70px">
                <input type="submit" id="register"
                       style="-webkit-border-bottom-left-radius:0px;-webkit-border-bottom-right-radius:15px;-webkit-border-top-right-radius: 15px"
                       onclick="changeAction()" value="Register"></div>
        </div>
    </form>
</div>

<script>
    $(function () {
        <c:if test="${not empty param.timeout}">
        layer.msg('连接超时,请重新登陆!', {
            offset: 0,
            shift: 6
        });
        </c:if>

        if ("${error}") {
            $('#submit').attr('value', "${error}").css('background', 'red');
        }

        if ("${message}") {
            layer.msg('${message}', {
                offset: 0,
            });
        }

        $('.close').on('click', function (c) {
            $('.login-form').fadeOut('slow', function (c) {
                $('.login-form').remove();
            });
        });

        $('#username,#password').change(function () {
            $('#submit').attr('value', 'Login').css('background', '#3ea751');
        });
    });

    /**
     * check the login form before user login.
     * @returns {boolean}
     */
    function checkLoginForm() {
        var username = $('#username').val();
        var password = $('#password').val();
        if (isNull(username) && isNull(password)) {
            $('#submit').attr('value', '请输入账号和密码!!!').css('background', 'red');
            return false;
        }
        if (isNull(username)) {
            $('#submit').attr('value', '请输入账号!!!').css('background', 'red');
            return false;
        }
        if (isNull(password)) {
            $('#submit').attr('value', '请输入密码!!!').css('background', 'red');
            return false;
        }

        $('#submit').attr('value', 'Logining~');
        return true;
    }

    /**
     * check the param if it's null or '' or undefined
     * @param input
     * @returns {boolean}
     */
    function isNull(input) {
        if (input == null || input == '' || input == undefined) {
            return true;
        }
        else {
            return false;
        }
    }
    function changeAction() {
        $("#login-form").attr('action','<%=path%>/user/redirectRegister');
        $("#login-form").submit();
    }
</script>
</body>
</html>