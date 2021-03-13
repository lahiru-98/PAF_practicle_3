<%@page import="com.Item"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	session.setAttribute("statusMsg", "");

if (request.getParameter("itemCode") != null) {

	if (request.getParameter("itemCode").length() != 0) {

		Item item = new Item();
		String messsage = "";

		if (!("null").equals(request.getParameter("Id"))) {
	int iid = Integer.parseInt(request.getParameter("Id"));

	messsage = item.updateItem(iid, request.getParameter("itemCode"), request.getParameter("itemName"),
			Double.parseDouble(request.getParameter("itemPrice")), request.getParameter("itemDesc"));

		} else {

	messsage = item.insertItem(request.getParameter("itemCode"), request.getParameter("itemName"),
			request.getParameter("itemPrice"), request.getParameter("itemDesc"));

		}

		session.setAttribute("statusMsg", messsage);
	}

}

//check for remove button Clicked
if (request.getParameter("itemId") != null) {

	String id = request.getParameter("itemId");
	String status = "";

	Item item = new Item();
	status = item.removeItem(id);

	session.setAttribute("statusMsg", status);
}

//check for update buttn clicked
if (request.getParameter("updateId") != null) {

	String id = request.getParameter("updateId");
	Item item = new Item();
	Item retrievedItem = item.readOneItem(id);

	//setting the values to the session object
	session.setAttribute("itemID", retrievedItem.getItemID());
	session.setAttribute("itemCode", retrievedItem.getItemCode());
	session.setAttribute("itemName", retrievedItem.getItemName());
	session.setAttribute("itemPrice", retrievedItem.getItemPrice());
	session.setAttribute("itemDesc", retrievedItem.getItemDescription());

	System.out.println("" + retrievedItem.toString());
} else {
	session.setAttribute("itemID", "null");
	session.setAttribute("itemCode", "");
	session.setAttribute("itemName", "");
	session.setAttribute("itemPrice", "");
	session.setAttribute("itemDesc", "");

}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>


	<div class="container">
		<div class="row">
			<div class="col">


				<h1>Items Management</h1>

				<div class="col-md-6">
					<div class="form-group">
						<form method="post" action="items.jsp">
							Item code: <input name="itemCode" type="text"
								value='<%=session.getAttribute("itemCode")%>'
								class="form-control"><br> Item name: <input
								name="itemName" type="text"
								value='<%=session.getAttribute("itemName")%>'
								class="form-control"><br> Item price:<input
								name="itemPrice" type="text"
								value='<%=session.getAttribute("itemPrice")%>'
								class="form-control"><br> Item description: <input
								name="itemDesc" type="text"
								value='<%=session.getAttribute("itemDesc")%>'
								class="form-control"><br> <input name='Id'
								type='hidden' value='<%=session.getAttribute("itemID")%>'>
							<input name="btnSubmit" type="submit" value="Save">
						</form>

					</div>
				</div>

				<div class="alert alert-success">
					<%
					out.print(session.getAttribute("statusMsg"));
				%>

				</div>


				<br>

				<%
					Item itemObj = new Item();
				out.print(itemObj.readItems());
				%>

				<br>


			</div>
		</div>
	</div>







</body>
</html>