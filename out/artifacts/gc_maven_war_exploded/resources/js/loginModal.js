$(function () {
    //忘记密码
    $("#reme").click(function () {
        alert("请尝试身份证后六位，或者联系管理员！");
    });
//登录
    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    var  btn =  $("#loginBtn");

//ajax登录
    $("#loginForm").validate({
        submitHandler:function (form) {
            $(form).ajaxSubmit({
                beforeSubmit:function () {
                    btn.button("loading");
                },
                success: function (responseText, statusText, xhr, $form) {
                    // alert(xhr.getResponseHeader("Location"));
                    var m = $.parseJSON(xhr.responseText);
                    if(m.data==0){
                        alert("修改成功！");
                        location.reload();
                    }else {
                        btn.button("reset");
                        alert("操作失败"+m.message);
                    }
                },
                error: function (xhr, textStatus, errorThrown) {
                    alert("未知错误，请联系管理员"+xhr.responseText);
                    $("#loginBtn").button("reset");
                }
            });
        },
        rules:{
            accountName : {
                required : true
            },
            password : {
                required : true
            }
        },
        messages:{
            accountName : {
                required : "帐户名必须填写"
            },
            password : {
                required : "登录密码必须填写"
            }
        },     errorPlacement : function(error, element) {
            element.attr("placeholder",error.html());
            // error.appendTo(element.closest(
            //     "div.row").children(
            //     "div.text-error"));
        }
    });

})