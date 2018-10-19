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
          Hello, ${user.username}
        [#else]
          You aren't logged in. <a href="/login">Login</a>
        [/#if]
      </div>
    </header>
    [#nested]
  </body>
</html>
[/#macro]