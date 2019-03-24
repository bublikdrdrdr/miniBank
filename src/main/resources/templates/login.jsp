<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="/login.css">
</head>
<body>
<div th:replace="fragments/layout :: header">header</div>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <p th:if="${loginError}" class="bg-danger">Wrong login or password</p>
            <p th:if="${disabledError}" class="bg-danger">Account is disabled</p>
            <p th:if="${blockerError}" class="bg-danger">Account is blocked</p>
            <p th:if="${loggedOut}" class="bg-info">You have been logged out</p>
            <form id="login-form" th:action="@{/login}" method="post" role="form" style="display: block;">
                <div class="form-group">
                    <input type="text" name="username" id="username" class="form-control"
                           placeholder="Username" value="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" class="form-control"
                           placeholder="Password">
                </div>
                <div class="form-group text-center">
                    <input type="checkbox" class="" name="remember" id="remember">
                    <label for="remember"> Remember Me</label>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <input type="submit" name="login-submit" id="login-submit" class="form-control btn btn-login" value="Log In">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="text-center">
                                <a href="/registration" class="under-links">Registration</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="text-center">
                                <a href="/forgot" class="under-links">Forgot Password?</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div th:replace="fragments/layout :: footer">footer</div>
<th:block th:replace="fragments/scripts :: after"/>
</body>
</html>