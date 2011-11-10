<!DOCTYPE html>

<html>
<%@ include file="/parts/header.jsp"%>
<body>
<div data-role="header">
	<h1>Start Freetime</h1>
</div>

<div data-role="content">
	<ul data-role="listview" data-filter="true">
		<li data-icon="false" class="listItem"><a>Gaming</a></li>
		<li data-icon="check" id="2"><a href="">Sports</a></li>
		<li data-icon="false" id="3"><a href="">Bar</a>
	</ul>
</div>

<script type="text/javascript">
$(document).ready(function(){
	  $(".listItem").click(function(){
	    $(this).attr('data-icon','check');
	  });
	});
</script>

<%@ include file="/parts/footer.jsp" %>

</body>
</html>