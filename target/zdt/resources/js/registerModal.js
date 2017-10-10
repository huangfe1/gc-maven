$(function () {
//登录
    $("#loginBtn").click(function () {
        $("#registerForm").submit();
    });
    //如果有编号
$("#hasCode").click(function () {
    $("#noCodeDiv").hide();
    $("#codeDiv").show();
});
$("#noCode").click(function () {
    $("#noCodeDiv").show();
    $("#codeDiv").hide();
});
//ajax登录
    $("#registerForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                beforeSubmit: function () {
                    $("#loginBtn").button("loading");
                },
                success: function (responseText, statusText, xhr, $form) {
                    // alert(xhr.getResponseHeader("Location"));
                    var m = $.parseJSON(xhr.responseText);
                    if(m.flag==0){
                        alert("修改成功");
                        location.reload();
                    }else {
                        $("#loginBtn").button("reset");
                        alert("操作失败"+m.message);
                    }
                },
                error: function (xhr, textStatus, errorThrown) {
                    $("#loginBtn").button("reset");
                    document.write(xhr.responseText);
                    alert("未知错误，请联系管理员"+xhr.responseText);
                }
            });
        },
        rules: {
            accountName: {
                required: true
            },
            password: {
                required: true
            },
            mobile: {
                required: true,
                mobile:true
            },
            realName:{
                required: true
            },
            weixin:{
                required: true
            }

        },
        messages : {
            realName : {
                required : "请输入代理姓名"
            },
            mobile : {
                required : "请输入手机号码",
                mobile: "手机号码格式不正确"
            },
            weixin:{
                required:"请输入微信号"
            }

        },
        errorPlacement : function(error, element) {
            element.attr("placeholder",error.html());
            // error.appendTo(element.closest(
            //     "div.row").children(
            //     "div.text-error"));
        }
    });

})