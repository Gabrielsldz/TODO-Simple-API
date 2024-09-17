async function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    console.log(username, password);

    // Validação para impedir login com campos vazios
    if (!username || !password) {
        alert("Por favor, preencha os campos de usuário e senha.");
        return;
    }

    const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
        }),
        body: JSON.stringify({
            username: username,
            password: password,
        }),
    });

    if (response.ok) {
        let token = response.headers.get("Authorization");
        window.localStorage.setItem("Authorization", token);
        showToast("#okToast");
        window.setTimeout(function () {
            window.location = "/view/index.html";
        }, 2000);
    } else {
        showToast("#errorToast");
    }
}

function showToast(id) {
    var toastElList = [].slice.call(document.querySelectorAll(id));
    var toastList = toastElList.map(function (toastEl) {
        return new bootstrap.Toast(toastEl);
    });
    toastList.forEach((toast) => toast.show());
}