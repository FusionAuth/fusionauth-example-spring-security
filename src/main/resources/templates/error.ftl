[#ftl/]
[#include "_macros/base.ftl"/]
[@base title="Error"]
  <div class="alert alert-danger">
    <h4 class="alert-heading">Something went wrong<h4>
    <var>${status}</var>
    <samp>${error}</samp>
  </div>
[/@base]

