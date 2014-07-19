<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/jquery/css/swanky-purse/jquery-ui-1.10.2.custom.css" rel="stylesheet">
<script src="resources/jquery/js/jquery-1.9.1.js"></script>
<script src="resources/jquery/js/jquery-ui-1.10.2.custom.js"></script>
<script>
a.link{
position:absolute;
};
</script>
<script>
	$(function() {
	var index='${index}';
	var bid='${book.bid}';
	   $('a.current').click(function(){return false;});
		$( "#tabs" ).tabs({active: index});
		 $( "#previous" ).button();
		$( "#next" ).button();
		$( "input[type=submit],a.buttonlink" ).button();
		
		$( "#spinnerprice" ).spinner({
	
			step: 0.01,
			numberFormat: "n",
			spin: function( event, ui ) {
				if ( ui.value < 0  ) {
					$( this ).spinner( "price", 0 );
					
				}
				}
		});
		
		$("#button").button();
		
		$( document ).tooltip();
		
		$( "#spinner" ).spinner({
			spin: function( event, ui ) {
				if ( ui.value < 0  ) {
					$( this ).spinner( "quantity", 0 );
					
				}
			}
		});
		
		$( "#dialog" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						window.location.href="delete?bid="+bid;
					}
				},
				{
					text: "Cancel",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});
		$( "#dialog-modal" ).dialog({
		   autoOpen: false,
			height: 800,
			width:600,
			modal: true,
			show: {
				effect: "blind",
				duration: 1000
			},
			hide: {
				effect: "explode",
				duration: 1000
			}
		});

		// Link to open the dialog
		$( "#dialog-link" ).click(function( event ) {
			$( "#dialog" ).dialog( "open" );
			event.preventDefault();
		});
		$( "#dialog-link-modal" ).click(function( event ) {
			$( "#dialog-modal" ).dialog( "open" );
			event.preventDefault();
		});
		
		$( "#accordion" ).accordion({
        heightStyle: "content"
    });
    
    
	});
</script>
<script type="text/javascript">
		$(function() {
		$.fn.left = function( using ) {
			return this.position({
				my: "right bottom",
				at: "left+110 bottom",
				of: "#relatedbooks",
				collision: "none",
				using: using
			});
		};
		$.fn.right = function( using ) {
			return this.position({
				my: "left bottom",
				at: "right-110 bottom",
				of: "#relatedbooks",
				collision: "none",
				using: using
			});
		};
		
		
		$.fn.center = function( using ) {
			return this.position({
				my: "center bottom",
				at: "center bottom",
				of: "#relatedbooks",
				collision: "none",
				using: using
			});
		};

		
		$( "a.book:eq(0)" ).left();
		$( "a.book:eq(1)" ).center();
		$( "a.book:eq(2)" ).right();
	    $( "a.book:eq(3)" ).hide().right();
	    
        function animate( to ) {
			$( this ).stop( true, false ).animate( to );
		}
		
		var m=0;
		function next( event ) {
			event.preventDefault();
			if(m>0){
			$( "a.book:eq("+((m+2)%4)+")" ).center(animate);
			$( "a.book:eq("+((m+1)%4)+")" ).left(animate);
		    $( "a.book:eq("+((m+3)%4)+")" ).show('slow').right();
		    $( "a.book:eq("+(m%4)+")" ).hide().right();
		   m=(m+1)%4;
		   }
			
		}
		function previous( event ) {
			event.preventDefault();
			if(m!=1){
			$( "a.book:eq("+(m%4)+")" ).center(animate);
			$( "a.book:eq("+((m+1)%4)+")" ).right(animate);
			$( "a.book:eq("+((m+2)%4)+")" ).hide().right();
			$( "a.book:eq("+((m+3)%4)+")" ).show('slow').left();
			
			m=(m+3)%4;
			}
		}
		
		$( "#previous" ).click( previous );
		$( "#next" ).click( next );
			
	});
</script>
<style type="text/css">
#dialog-link {
		padding: .4em 1em .4em 20px;
		text-decoration: none;
		position: relative;
	}
	#dialog-link span.ui-icon {
		margin: 0 5px 0 0;
		position: absolute;
		left: .2em;
		top: 50%;
		margin-top: -8px;
	}
</style>
<sql:setDataSource var="datas" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://localhost:3306/bookcommunication"
     user="root"  password="Cyn1988"/>

<sql:query dataSource="${datas}" var="order">
Select count(*) as num From orders Where seller_id=${seller.sid} and status='pending'
</sql:query>
<sql:query dataSource="${datas}" var="bookcatalog1">
Select * From book Where seller_id=${seller.sid} 
</sql:query>
<c:if test="${oneorder!=null }">
<sql:query dataSource="${datas}" var="bookordered">
Select b.bid as bid,o.obid as id,b.title as title, b.price as price, o.quantity as quantity From orderbook as o,book as b Where o.book_id=b.bid and o.order_id=${oneorder.oid}
</sql:query>
</c:if>
<title>Second Hand Book Communication</title>
</head>
<body style="background: #675423 url(resources/image/ui-bg_diamond_25_675423_10x8.png) 50% 50% repeat;">
<div id="container" style="width:900px;margin:0 auto;">
<div id="header" 
    style="height: 100px;border: 1px solid #baaa5a;
	background: #261803 url(resources/image/ui-bg_diamond_8_261803_10x8.png) 50% 50% repeat;
	color: #eacd86;
	font-weight: bold;text-align:center">
<h1>Second Hand Book Communication</h1></div>
<div id="left" 
style="width: 300px;float:left;background:#4f4221 url(resources/image/ui-bg_diamond_10_4f4221_10x8.png) 50% 50% repeat;height:1000px;">
<div id="accordion">
	
	<h3>Orders in Pending </h3>
	<div>
	<c:forEach var="order" items="${order.rows}">
	 ${order.num} Order(s) in Pending<br/>
	 </c:forEach>
	<a href="receviedorder?sid=${seller.sid}">view order</a>
	</div>
	<h3>User Information</h3>
	<div>
	<a href="edit?uid=${seller.user.uid}">[edit]</a> <a href="j_spring_security_logout">Logout</a><br/>
	<table>
	<tr><td>User Name:</td><td>${seller.user.username }</td></tr>
    <tr><td>Role:</td><td> Seller</td></tr>
    </table>
    <table>
    <tr><td>Email :</td></tr><tr><td>${seller.user.email}</td></tr>
    </table>
    Address:<br />
    <table>
    <c:forEach var="address" items="${seller.user.addressList }">
    <tr><td>Street:</td><td>${address.street}</td></tr>
    <tr><td>City:</td><td>${address.city}</td></tr>
    <tr><td>State:</td><td>${address.state}</td></tr>
    <tr><td>Zip:</td><td>${address.zip}</td></tr>
    </c:forEach>
    </table>
	</div>
	<h3>About Our Store</h3>
	<div>
  <p>
  Our site is a platform for users to buy or sell the second hand book, and generate the information about second hand book stores.
  </p>
  <p>Contact Information</p>
  <table>
	<tr><td>Phone:</td><td>123-456-7890</td></tr>
	<tr><td>Email:</td><td><a href="mailto:info@secondhandbookcommunication.com?Subject=General%20Question">Send Mail</a></td></tr>
	<tr><td>Address<td><tr>
	<tr><td>Street:</td><td>99 Book Street</td></tr>
    <tr><td>City:</td><td>Boston</td></tr>
    <tr><td>State:</td><td>MA</td></tr>
    <tr><td>Zip:</td><td>02120</td></tr>
	</table>
  </div>
	
</div>

  
</div>
<div id="right" style="width: 600px;float:left;background:#443113 url(resources/image/ui-bg_diamond_8_443113_10x8.png) 50% 50% repeat;height:1000px;">
  <div id="tabs">
	<ul>
		<li><a href="#tabs-1">Search</a></li>
		<li><a href="#tabs-2">Order</a></li>
		<li><a href="#tabs-3">Book Catalog</a></li>
		<li><a href="#tabs-4">Profile</a></li>
	</ul>
	
	<div id="tabs-1">
	<p><strong>SEARCH</strong></p>
	<c:if test="${search=='search'}">
	<form action="searchs" method="post">
	<input type="hidden" value="${seller.sid }" name="sid">
	<input type="hidden" value="1" name="page">
	<input type="text" name="word" value="${word }" class="text ui-widget-content ui-corner-all"/>
	<select name="type">
	<option value="title" ${type=='title'? 'selected':' ' }>By Title</option>
	<option value="author" ${type=='author'? 'selected':' ' }>By Author</option>
	<option value="isbn" ${type=='isbn'? 'selected':' ' }>By ISBN</option>
	</select>
	<input type="submit" value="GO" name="submit"/>
	</form>
	<table>
	<c:forEach var="book" items="${bookcatalog}">
	<tr>
	<td><a href="viewbooks?bid=${book.bid}&sid=${seller.sid}"><img alt="${book.title}" src="image?bid=${book.bid}" width="90" height="120"/></a></td>
	<td>
	<p><a href="viewbooks?bid=${book.bid}&sid=${seller.sid}">${book.title}</a><br/>written by ${book.author} </p>
	</td>
	<tr>
	</c:forEach>
	
	</table>
	<div style="text-align:center;">
	<c:if test="${pages>1}">
	<a href="searchs?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${page-1}&sid=${seller.sid}" class="${page==1? 'current':'normal'}">&lt;&lt; </a>
	<c:forEach var="i" begin="1" end="${pages }">
	<a href="searchs?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${i}&sid=${seller.sid}" class="${page==i? 'current':'normal'}"> <strong>&nbsp;${i}&nbsp;&nbsp;</strong> </a>
	</c:forEach>
	<a href="searchs?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${page+1}&sid=${seller.sid}" class="${page==pages? 'current':'normal'}"> &gt;&gt;</a>
	</c:if>
	</div>
	</c:if>
	<c:if test="${search=='detail'}">
	<div style="width: 600px">
	<a class="buttonlink" href="backsearchs?sid=${seller.sid }" >&lt;&lt; BACK</a>
    </div>
    <div id="dialog-modal" title="Book Front Page">
    <img alt="${book.title}" src="image?bid=${book.bid}" width="480" height="640" align="right"/>
</div>
     <a href="#" id="dialog-link-modal">
    <img alt="${book.title}" src="image?bid=${book.bid}" width="120" height="160" align="right"/></a>
    <div>
    <table>
    <tr><td>Title:</td><td>${book.title}</td></tr>
    <tr><td>Author:</td><td>${book.author}</td></tr>
    <tr><td>ISBN:</td><td>${book.isbn}</td></tr>
    <tr><td>Price:</td><td>${book.price}</td></tr>
    <tr><td>Quantity:</td><td>${book.quantity}</td></tr>
    <tr><td>Description:</td><td>${book.description}</td></tr>
    <tr><td>Seller:</td><td><a href="sellerInfos?bid=${book.bid}&sid=${seller.sid }">${book.seller.user.username}</a></td></tr>
    </table>
    <strong>Related Books</strong>
    <table>
    <tr>
    <td>
    <c:if test="${num>3}">
    <a id="previous" href="#">&lt;</a>
    </c:if>
    </td>
    <td>
    <div id="relatedbooks" style="width: 400px;height:130px;overflow:hidden;position: relative;">
    <c:forEach var="related" items="${relatedbook}">
    <a class="book" href="viewbooks?bid=${related.bid}&sid=${seller.sid}"><img alt="${related.title}" src="image?bid=${related.bid}" width="90" height="120"/></a>
    </c:forEach>
    </div>
    </td>
    <td>
    <c:if test="${num>3}">
    <a id="next" href="#">&gt;</a>
    </c:if>
    </td>
    </tr>
    </table>
    </div>
	</c:if>
	<c:if test="${search=='sellerInfo'}">
	<div>
	<a class="buttonlink" href="backviews?bid=${book.bid}&sid=${seller.sid }">&lt;&lt; BACK</a>
	</div>
	<div>
	<img alt="" src="${map}">
	</div>
	<div>
	<table>
	<tr><td>User Name:</td><td>${seller1.user.username }</td></tr>
    <tr><td style="width: 105px; ">Company Name:</td><td>${seller1.company }</td></tr>
	<tr><td>WebSite<br/>(if any):</td><td><a href="${seller1.website}">${seller1.website }"</a></td></tr>
	<tr><td>Description:</td><td>${seller1.description }"</td></tr>
    </table>
     Store Address:<br />
    <table>
    <c:forEach var="address" items="${seller1.user.addressList }">
    <tr><td>Street:</td><td>${address.street}</td></tr>
    <tr><td>City:</td><td>${address.city}</td></tr>
    <tr><td>State:</td><td>${address.state}</td></tr>
    <tr><td>Zip:</td><td>${address.zip}</td></tr>
    </c:forEach>
    </table>
    </div>
    <hr/>
    <div>
    <h3>${feed.title}</h3>
    <hr>
    <p>${feed.description}</p>
    <c:forEach var="item" items="${feed.items}">
    <p><a href="${item.link}"> ${item.title }</a><br>
    ${item.pubDate}</p>
    </c:forEach>
    </div>
	</c:if>
	
	
	</div>
	<div id="tabs-2">
	<p><strong>RECEIVED ORDER</strong></p>
	
	<c:if test="${buyerorder=='order'}">
	<table border="2">
	<tr>
	<th style="width: 100px; ">Order Id</th>
	<th style="width: 90px; ">status</th>
	<th style="width: 60px; ">Return?</th>
	<th style="width: 150px; ">Tracking No.</th>
	<th style="width: 100px; ">Buyer ID</th>
	</tr>
	<c:forEach var="order" items="${orderlist }">
	<tr>
	<td><a href="orderdetails?sid=${seller.sid}&oid=${order.oid}">&lt; ${order.oid } &gt;</a></td>
	<td>${order.status }</td>
	<td>${order.isreturn}</td>
	<td>${order.tracking}</td>
	<td>${order.buyer.bid }</td>
	</tr>
	</c:forEach>
	</table>
	</c:if>
	<c:if test="${buyerorder=='detail'}">
	<div>
	<a class="buttonlink" href="backorders?sid=${seller.sid }">&lt;&lt; BACK</a>
	</div>
	<p><strong>Order Detail</strong></p>
	<c:if test="${updateorders!=null}">
	<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Hey!</strong> ${updateorders}</p>
	</div>
    </div>
	</c:if>
	<form action="updateorder" method="post">
	<table border="3">
	<tr><td>Order id</td><td>${oneorder.oid}</td>
	<tr><td>Status</td><td><input type="text" name="status" value="${oneorder.status}" class="text ui-widget-content ui-corner-all"></td>
	<tr><td>Return?</td><td><input type="text" name="isreturn" value="${oneorder.isreturn}" class="text ui-widget-content ui-corner-all"></td>
	<tr><td>Tracking No.</td><td><input type="text" name="tracking" value="${oneorder.tracking}" class="text ui-widget-content ui-corner-all"></td>
	<tr><td>Buyer Name</td><td>${oneorder.buyer.user.username}</td>
	</table>
	<input type="hidden" name="sid" value="${seller.sid}"/>
	<input type="hidden" name="oid" value="${oneorder.oid }"/> 
	<input type="submit" value="UPDATE"/>
	</form>
	<table border="3">
	<tr>
	<th style="width: 400px; ">Book Title</th>
	<th style="width: 50px; ">Price</th>
	<th style="width: 100px; ">Quantity</th>
	</tr>
    <c:if test="${oneorder!=null}">
	<c:forEach var="obook" items="${bookordered.rows}">
	<tr>
	<td><a href="viewbooks?bid=${obook.bid}&sid=${seller.sid}"><img alt="${obook.title}" src="image?bid=${obook.bid}" width="60" height="80" align="left"/></a><p align="justify">${obook.title }</p></td>
	<td>${obook.price}</td>
	<td>${obook.quantity}</td>
	<tr>
	</c:forEach>
    </c:if>
	</table>
	<p>Shipping Address</p>
	<table>
    <tr><td>Street:</td><td>${oneorder.address.street}</td></tr>
    <tr><td>City:</td><td>${oneorder.address.city}</td></tr>
    <tr><td>State:</td><td>${oneorder.address.state}</td></tr>
    <tr><td>Zip:</td><td>${oneorder.address.zip}</td></tr>
    </table>
	</c:if>
	

	</div>
	<div id="tabs-3">
	<p><strong>BOOK CATALOG</strong></p>
	<div id="catalog">
    <c:if test="${catalog=='addbook' }">
    <a class="buttonlink" href="backbook?sid=${seller.sid }">&lt;&lt; BACK</a><br/>
    <c:if test="${upload!=null}">
	<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Hey!</strong> ${upload}</p>
	</div>
    </div>
	</c:if>
    <form action="upload" method="post" enctype="multipart/form-data">
    Upload Front Page:<br/>
    <input type="file" name="file">
    <input type="hidden" name="sid" value="${seller.sid }"/>
    <input type="submit" value="UPLOAD"></form>
    <form action="addbook" method="POST">
    <input type="hidden" name="sid" value="${seller.sid }"/>
    <table>
    <tr><td>Title:</td><td><input type="text" name="title" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Author:</td><td><input type="text" name="author" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>ISBN:</td><td><input type="text" name="isbn" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Price:</td><td><input id="spinnerprice"  name="price" value="0.00"/></td></tr>
    <tr><td>Quantity:</td><td><input id="spinner" name="quantity" value="0"/></td></tr>
    <tr><td>Description:</td><td><textarea rows="5" cols="40" name="description" class="textarea ui-widget-content ui-corner-all"></textarea></td></tr>
    </table>
    <input type="submit" name="sumit" value="ADD"/>
    </form>
    </c:if>
    <c:if test="${catalog=='catalog'}">
    <a class="buttonlink" href="addnewbook?sid=${seller.sid }">Add New Book</a>
    <c:if test="${bookcatalog1!=null }">
    <table>
	<c:forEach var="bookc" items="${bookcatalog1.rows}">
	<tr>
	<td><a href="viewbook?bid=${bookc.bid}&sid=${seller.sid}"><img alt="${bookc.title}" src="image?bid=${bookc.bid}" width="90" height="120"/></a></td>
	<td>
	<p><a href="viewbook?bid=${bookc.bid}&sid=${seller.sid}">${bookc.title}</a><br/>written by ${bookc.author} </p>
	</td>
	<tr>
	</c:forEach>
	</table>
    </c:if>
    </c:if>
    <c:if test="${catalog=='viewbook'}">
    <div style="height: 70px; ">
    <a class="buttonlink" href="backbook?sid=${seller.sid }" style="float:left">&lt;&lt; BACK</a>
    <a href="#" id="dialog-link" class="ui-state-default ui-corner-all" style="float:right"><span class="ui-icon ui-icon-newwin"></span>DELETE&gt;&gt;</a>
    
    </div>
    <c:if test="${updatebook!=null}">
	<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Hey!</strong> ${updatebook}</p>
	</div>
    </div>
	</c:if>
	
	<table>
	<tr>
	<td >
    <form action="updatebook" method="POST">
    <input type="hidden" name="sid" value="${seller.sid }"/>
    <input type="hidden" name="bid" value="${book.bid }"/>
    <table>
    <tr><td>Title:</td><td><input type="text" name="title" value="${book.title}" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Author:</td><td><input type="text" name="author"value="${book.author}" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>ISBN:</td><td><input type="text" name="isbn"value="${book.isbn}" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Price:</td><td><input id="spinnerprice" name="price"value="${book.price}" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Quantity:</td><td><input id="spinner" name="quantity" value="${book.quantity}" class="text ui-widget-content ui-corner-all"/></td></tr>
    <tr><td>Description:</td><td><textarea rows="5" cols="20"name="description" class="textarea ui-widget-content ui-corner-all">"${book.description}"</textarea></td></tr>
    </table>
    <input type="submit" name="sumit" value="Update"/>
    </form>
    </td>
    <td style="width: 20px; "></td>
    <td>
    <img alt="${book.title}" src="image?bid=${book.bid}" width="120" height="160" align="top" style="margin-left:50"/><br/>
    <form action="uploadnew" method="post" enctype="multipart/form-data">
    Upload New Front Page:<br/>
    <input type="file" name="file"><br/>
    
    <input type="hidden" name="sid" value="${seller.sid }"/>
    <input type="hidden" name="bid" value="${book.bid }"/>
    <input type="submit" value="UPLOAD"></form>
    </td>
    </tr>
    </table>
    <div id="dialog" title="Delete Book">
	<p>Are you sure to delete this book?</p>
    </div>
    </c:if>
    </div>
	</div>
	
	
	<div id="tabs-4">
	<p><strong>PROFILE</strong></p>
	<c:if test="${editerror!=null }">
	<div class="ui-widget">
	  <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<strong>Alert:</strong> ${editerror}</p>
	  </div>
    </div>
	</c:if>
	<c:if test="${updatesuccess!=null}">
	<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Hey!</strong> ${updatesuccess}</p>
	</div>
    </div>
	</c:if>
	<form action="profile" method="post">
	<input type="hidden" name="uid" value="${seller.user.uid}"/>
	<input type="hidden" name="sid" value="${seller.sid}"/>
	<input type="hidden" name="role" value="${seller.user.role}"/>
	<table>
	<tr><td>Username:</td><td><input type="text" name="username" value="${seller.user.username}" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>Role:</td><td>${seller.user.role}</td></tr>
	<tr><td>Password:</td><td><input type="password" name="password" title="Must be at leat 4 digits" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>ReEnter Password:</td><td><input type="password" name="rpassword" title="Enter the Password Again" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>Email:</td><td><input type="text" name="email" value="${seller.user.email }" title="like:example@gmail.com" class="text ui-widget-content ui-corner-all"/><br/></td></tr>
	</table>
	Address:<br/>
	<c:forEach var="address" items="${seller.user.addressList }">
	<input type="hidden" name="aid" value="${address.aid }"/>
	<table>
	<tr><td>Street:</td><td><input type="text" name="street" value="${address.street }" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>City:</td><td><input type="text" name="city" value="${address.city }" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>State:</td><td><input type="text" name="state" value="${address.state }" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>Zip:</td><td><input type="text" name="zip" value="${address.zip }" class="text ui-widget-content ui-corner-all"/></td></tr>
	</table>
	</c:forEach>
	
	
	<table>
	<tr><td style="width: 163px; ">Book Store Name:</td><td><input type="text" name="company" value="${seller.company }" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>WebSite(if any):</td><td><input type="text" name="website" value="${seller.website }" class="text ui-widget-content ui-corner-all"/></td></tr>
	<tr><td>Description:</td><td><textarea rows="6" cols="42" name="description" class="textarea ui-widget-content ui-corner-all">"${seller.description }"</textarea></td></tr>
	</table>
	<input type="submit" value="UPDATE"/>
	</form>
	</div>
	
	
</div>
</div>
</div>

</body>
</html>