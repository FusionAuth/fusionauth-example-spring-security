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
      [#if user??]
        Hello, ${user.username}
      [#else]
        You aren't logged in. <a href="/login">Login</a>
      [/#if]
    </header>
    [#nested]
  </body>
</html>
[/#macro]