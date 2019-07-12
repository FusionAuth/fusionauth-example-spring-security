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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
  </head>
  <body class="container-fluid p-0">
    <nav class="navbar navbar-dark navbar-expand-lg bg-dark">
      <div class="navbar-brand">
        <a href="/"><i class="fas fa-leaf"></i></a>
      </div>
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a href="/" class="nav-link">Home</a>
        </li>
        <li class="nav-item">
          <a href="/profile" class="nav-link">Profile</a>
        </li>
        <li class="nav-item">
          <a href="/admin" class="nav-link">Admin</a>
        </li>
      </ul>
      <div>
        [#if loggedIn?? && loggedIn]
          [#if hasRoles]
            <span class="navbar-text">Hello[#if user.username?has_content], ${user.username}[/#if]</span> <a href="${logoutUri}">Logout</a>
          [#else]
            <span class="navbar-text">You aren't registered for this site[#if user.username?has_content] (${user.username})[/#if]</span> <a href="/register">Register</a>
          [/#if]
        [#else]
          <span class="navbar-text">You aren't logged in</span> <a href="/oauth2/authorization/fusionAuth" class="btn">Login</a> <a
            href="/register">Sign Up</a>
        [/#if]
      </div>
    </nav>
    [#nested]
  </body>
</html>
[/#macro]

[#macro textControl name displayName type="text"]
  <div class="form-group">
    <label for="${name}">${displayName}</label>
    <input type="${type}" id="${name}" class="form-control" name="${name}">
  </div>
[/#macro]
