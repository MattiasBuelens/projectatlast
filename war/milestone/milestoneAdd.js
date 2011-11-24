function createSentence(){
	
         var sentence = "I want to ";
         sentence += $("select#type option:selected").val();
         sentence += $("select#course option:selected").val();
         sentence += $("select#parser option:selected").val()
         sentence += $("select#parseField option:selected").val();
         
         
     	$(".milestoneString").val(sentence);  
   

	
	
	

}