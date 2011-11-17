<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="projectatlast.query.*"%>

<%@ page import="java.util.List"%>
<%
	Student student = AuthController.getCurrentStudent();
	List<Activity> activities = ActivityController
			.getAllFromStudent(student);
%>

<div data-role="page">
	<div data-role="header">
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Create Milestone</h1>
	</div>
<script>

$(document).bind('mobileinit',function(){
   $.mobile.selectmenu.prototype.options.nativeMenu = false;
});
</script>

	<div data-role="content">
		<div class="content-primary">
			<form method="get" action="/milestone/add">
			<% Parser[] parsers = Parser.values(); %>
			<label for="select-choice-0" class="select">Comparative Operator:</label> 
			<select	name="parser" id="parser" data-native-menu="false">
			<% 
			for(Parser obj:parsers){ %>
			
				<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
			<%	}	%>

			</select>
			
			
			
			<% ParseField[] parsefields = ParseField.values(); %>
		
			<label for="select-choice-0" class="select">ParseField:</label> 
			<select
				name="parsefield" id="parsefield" data-native-menu="false">
		
			<% 
			for(ParseField obj:parsefields){ %>
			
				<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
			<%	}	%>
			</select>
			
			
			<% ComparativeOperator[] operators = ComparativeOperator.values(); %>
			<label for="select-choice-0" class="select" >Comparative Operator:</label> 
			<select	name="operator" id="operator" data-native-menu="false">
			<% 
			for(ComparativeOperator obj:operators){ %>
			
				<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
			<%	}	%>

			</select>
			
			   <label for="basic">Goal:</label>
    		   <input type="text" name="goal" id="goal" value=""  />

			<button type="submit" data-theme="b" name="submit" value="submit-value">Create milestone</button>
			</form>
		</div>
	</div>


	<%@ include file="/includes/footer.jsp"%>
</div>
</body>
</html>