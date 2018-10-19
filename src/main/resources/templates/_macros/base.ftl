[#ftl]
[#-- @ftlvariable name="user" type="io.fusionauth.security.FusionAuthUserDetails" --]
[#macro base title="Example App"]
<!doctype html>
<html>
  <head>
    <title>${title}</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
  </head>
  <body>
    <header>
      <div>
        <nav>
          <a href="/">Home</a>
          <a href="/profile">Profile</a>
          <a href="/admin">Admin</a>
        </nav>
      </div>
      <div>
        [#if user??]
          [#if user.authorities?has_content]
            Hello, ${user.username}
          [#else]
            You aren't registered for this site. <a href="/register">Register</a>
          [/#if]
        [#else]
          You aren't logged in. <a href="/login">Login</a> <a href="/register">Sign Up</a>
        [/#if]
      </div>
    </header>
    [#nested]
  </body>
</html>
[/#macro]