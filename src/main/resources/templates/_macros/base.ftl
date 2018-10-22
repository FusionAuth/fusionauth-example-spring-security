[#ftl]
[#-- @ftlvariable name="logoutUri" type="java.lang.String" --]
[#-- @ftlvariable name="hasRoles" type="boolean" --]
[#-- @ftlvariable name="loggedIn" type="boolean" --]
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
        [#if loggedIn]
          [#if hasRoles]
            Hello[#if user.username?has_content], ${user.username}[/#if]. <a href="${logoutUri}">Logout</a>
          [#else]
            You aren't registered for this site[#if user.username?has_content] (${user.username})[/#if]. <a href="/register">Register</a>
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