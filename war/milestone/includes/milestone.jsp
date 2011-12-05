
	<%
	long currentValue = MilestoneController.calculateProgress(milestone);
	long startValue   = milestone.getStartValue();
	long goalValue    = milestone.getGoal();
	
	ArrayList<Long> values = new ArrayList<Long>();
	values.add(currentValue );
	values.add(startValue   );
	values.add(goalValue    );
	
	long maxValue = values.get(0);
	
	for(int i=1;i<values.size(); i++){
		if(values.get(i)>maxValue){
			maxValue=values.get(i);
		}
	}
	
	long max = maxValue + maxValue/5;
	
	long currentPercentage 	= 100*currentValue/max;
	long startPercentage	= 100*startValue/max;
	long goalPercentage		= 100-100*goalValue/max;
	
	
	
	%>
	<div class="ui-body ui-body-c">
	<p><%=milestone.getSentence() %></p>
		<div class="milestone ui-btn-down-c ui-btn-corner-all">
			<div class="milestone-marker ui-btn-down-e" style="left: <%= startPercentage + "%" %>">
				<strong>Start</strong> <span><%= startValue%></span>
				<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
			</div>
				
			<div class="milestone-marker ui-btn-down-e" style="left: <%= currentPercentage + "%"%>">
				<div class="milestone-handle ui-bar-b ui-btn-corner-all">You</div>
				<span><%=currentValue %></span>
			</div>
				
			<div class="milestone-bar milestone-bar-right ui-btn-down-e"
					style="width: <%= goalPercentage + "%"%>">
				<strong>Goal</strong> <span><%= goalValue %></span>
			</div>
		</div>
		</div>
