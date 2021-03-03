<%@page import="com.Item"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	if(request.getParameter("itemCode") != null){
		Item item = new Item();
		String messsage;
		
		if(request.getParameter("Id")!= null){
			 messsage = item.updateItem(Integer.parseInt(request.getParameter("Id")),request.getParameter("itemCode"), request.getParameter("itemName"), 
					Double.parseDouble(request.getParameter("itemPrice")), request.getParameter("itemDesc"));
			
		}else{
			
			 messsage = item.insertItem(request.getParameter("itemCode"), request.getParameter("itemName"), 
					request.getParameter("itemPrice"), request.getParameter("itemDesc"));
			
			
		}
		
		session.setAttribute("statusMsg", messsage);
		
	}

//check for remove button Clicked
	if(request.getParameter("itemId") != null){
			
		String id = request.getParameter("itemId");
		
		Item item = new Item();
		String status = item.removeItem(id);
		
		session.setAttribute("removeStatus", status);
	}

//check for update buttn clicked
	if(request.getParameter("Id") != null){
		
		
		String id = request.getParameter("Id");		
		Item item = new Item();
		Item retrievedItem = null;
		retrievedItem = item.readOneItem(id);	
		
		//setting the values to the session object
		session.setAttribute("itemID",retrievedItem.getItemID());
		session.setAttribute("itemCode",retrievedItem.getItemCode());
		session.setAttribute("itemName",retrievedItem.getItemName());
		session.setAttribute("itemPrice",retrievedItem.getItemPrice());
		session.setAttribute("itemDesc",retrievedItem.getItemDescription());
		
		System.out.println(""+retrievedItem.toString());
	}else{
		session.setAttribute("itemCode","");
		session.setAttribute("itemName","");
		session.setAttribute("itemPrice","");
		session.setAttribute("itemDesc","");
	
	}
	

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
</head>
<body>

	<h1>Items Management</h1>
	<form method="post" action="items.jsp">
		Item code: <input name="itemCode" type="text"  value='<%= session.getAttribute("itemCode") %>'><br> 
		Item name: <input name="itemName" type="text"  value='<%= session.getAttribute("itemName") %>'><br> 
		Item price:<input name="itemPrice" type="text" value='<%= session.getAttribute("itemPrice") %>'><br>
		Item description: <input name="itemDesc" type="text" value='<%= session.getAttribute("itemDesc") %>'><br>
		
		<input name='Id' type='hidden' value='<%= session.getAttribute("itemID") %>'>
		<input name="btnSubmit" type="submit" value="Save">
	</form>

	<%
	out.print(session.getAttribute("statusMsg"));		
	%>

	<br>

	<%
		Item itemObj = new Item();
	    out.print(itemObj.readItems());
	%>
	
	<br>
	
	<%
	out.print(session.getAttribute("removeStatus"));	
	%>
	


</body>
</html>