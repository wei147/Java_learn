<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>办公OA系统</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <link rel="stylesheet" href="./resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">

    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="./resources/bootstrap/bootstrap.min.js"></script>
    <style>
        body {
            background-color: #f2f2f2;
        }

        .oa-container {
            /*background-color: white;*/
            position: absolute;
            width: 400px;
            height: 500px;
            top: 50%;
            left: 50%;
            padding: 20px;
            margin-left: -200px;
            margin-top: -175px;
        }

        #username, #password {
            /*text-align: center;*/
            /*font-size: 24px;*/
        }
    </style>
</head>
<body>
<div class="oa-container">
    <!--    <h1 style="text-align: center;margin-bottom: 20px">慕课网办公OA系统</h1>-->
    <h1 style="text-align: center;margin-bottom: 20px">办公OA系统</h1>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" id="username" lay-verify="required" placeholder="请输入用户名" name="username"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" id="password" lay-verify="required" placeholder="请输入密码" name="password"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <#--验证码所在-->
        <div class="layui-form-item">
            <div class="layui-form-label">
                <img id="imgVerifyCode" src="/verify_code"
                     style="width: 100px;height:36px;bottom:8px;left: 234px; position: relative;cursor: pointer">
                <#--                bottom:50px;left: 230px; position: relative;-->
            </div>
            <div class="layui-input-block">
                <input type="text" id="verifyCode" lay-verify="required" placeholder="请输入验证码" name="verifyCode"
                       autocomplete="off" class="layui-input" style="width: 125px">
            </div>
        </div>
<#--        <div class="layui-form-item">-->
<#--            <div class="layui-form-label">-->
<#--                <img id="imgVerifyCode" src="/verify_code"-->
<#--                     style="width: 100px;height:36px; bottom:10px;right: 30px; position: relative;cursor: pointer">-->
<#--            </div>-->
<#--            <div class="layui-input-block">-->
<#--                <input type="text" id="verifyCode" lay-verify="required" placeholder="请输入验证码" name="verifyCode"-->
<#--                       autocomplete="off" class="layui-input" style="width: 125px">-->
<#--            </div>-->

<#--        </div>-->


        <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登录</button>
        </div>
    </form>
</div>
<script src="/resources/layui/layui.js"></script>
<!--在script块中要对表单数据进行提交处理-->
<script>
    <!--submit(login)与lay-filter="login"的对应-->
    layui.form.on('submit(login)', function (formdata) {  //data参数包含了当前表单的数据
        console.log(formdata);
        //发送ajax请求进行登录校验。作为ajax校验，下方的return 必须是return false。如果是return true就会按照标准的表单形式进行数据的提交并且返回响应，这样的ajax就会失效
        layui.$.ajax({
            url: "/check_login",
            data: formdata.field,   //提交表单数据
            type: "post",
            //dataType 代表了服务器返回的数据类型是什么
            dataType: "json",
            success: function (json) {
                console.log(json);
                if (json.code == "0") {     //登录验证成功
                    //layui内置的弹出层，会有一个小提示
                    // layui.layer.msg("登录成功");
                    //跳转url
                    window.location.href = json.redirect_url;
                } else {
                    layui.layer.msg(json.message);
                }
            }
        })
        return false;   //submit提交事件返回true则表单提交，false则阻止表单提交
    })
</script>
</body>
</html>