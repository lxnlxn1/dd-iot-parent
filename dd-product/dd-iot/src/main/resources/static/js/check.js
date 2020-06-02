$(function () {
    var data = JSON.parse(localStorage.getItem('tocken'));
    if (data !== null) {
        if (data.expirse != null && data.expirse < new Date().getTime()) {
            localStorage.removeItem(key);
            alert("未登录");
            setTimeout(location.href = "login.html", "2000");
        }
    }else{
        alert("未登录");
        setTimeout(location.href = "login.html", "2000");
    }
})