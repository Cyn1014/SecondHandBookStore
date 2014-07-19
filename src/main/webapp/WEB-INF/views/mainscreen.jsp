<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	    $('a.current').click(function(){return false;});
	    $("#valified").button();
	    $( "#previous" ).button();
		$( "#next" ).button();
	    $( document ).tooltip();
		$( "#tabs" ).tabs({active:index});
		$( "#accordion" ).accordion();
		$( "input[type=submit],a.buttonlink" ).button();
		$( "#radioset" ).buttonset();
		$("#sendEmail").toggle();
		$("#buttonmail").button()
		                .click(function(){
		                $("#sendEmail").toggle();
		                });
		$("#store").hide();
		var open=false;
		if(index==3)open=true;
		$( "#dialog" ).dialog({
			autoOpen: open,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
				
			]
		});
		$('input:radio[name="role"]').change(
        function(){
               if ($(this).is(':checked') && $(this).val() == 'seller') {
                 $("#store").show();
                 $("#storeh").hide();
                  }
               if ($(this).is(':checked') && $(this).val() == 'buyer') {
                 $("#store").hide();
                  $("#storeh").show();
                 
                  }
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
		$( "#dialog-link" ).click(function( event ) {
			$( "#dialog-modal" ).dialog( "open" );
			event.preventDefault();
		});
		$( "#username" ).blur(
		function(){
		    var username=$(this);
		   	$.ajax({ 
		   	url: "validate",
		   	dataType: "text",
		   	data:"username="+username.val(),
		   	 success: function(text) { 
		   	 
		   	 $("#username").removeClass( "ui-state-error" );
		   	 $( "#usernamev" ).text("")
		   	                  .removeClass("ui-state-highlight");
		   	 $("#usernamed").removeClass("ui-icon-circle-check")
		   	                .removeClass("ui-icon-circle-close");
		   	 if(text=="true"){
		   	 
		     $( "#usernamev" ).text("username exist!")
		                      .addClass("ui-state-highlight");
		     $("#username").addClass( "ui-state-error" );
		     $("#usernamed").addClass("ui-icon-circle-close");
		   
		   }else{
		   var v=true;
		   v=v && checkLength(username,"username",4,16,$( "#usernamev" ));
		   v=v && checkRegexp( username, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." ,$( "#usernamev" ));
		   if(v){
		   
		    $("#usernamed").addClass("ui-icon-circle-check");
		   
		   }else{
		    $("#usernamed").addClass("ui-icon-circle-close");
		   }
		   ;};},error: function(){
		    	$( "p.validateTips" ).html("error");
		    	}
		   	
		   	 });
		   
		});
		$( "#password" ).blur(function(){
		    
		    var password=$(this);
		    $("#passwordv").text("")
		                   .removeClass("ui-state-highlight");
		    $("#passwordd").removeClass("ui-icon-circle-check")
		                   .removeClass("ui-icon-circle-close");
		    password.removeClass( "ui-state-error" );
		    var v=true;
		    v=v && checkLength( password, "password", 4, 16,$("#passwordv") );
		    v=v && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9",$("#passwordv") );
		    if(v){
		    $("#passwordd").addClass("ui-icon-circle-check");
		    }else {
		     $("#passwordd").addClass("ui-icon-circle-close");
		    };
		    
		    });
		$("#rpassword").blur(function(){
		      var rpassword=$(this);
		      
		      $("#rpasswordv").text("")
		                      .removeClass("ui-state-highlight");
		      $("#rpasswordd").removeClass("ui-icon-circle-check")
		                      .removeClass("ui-icon-circle-close");
		      rpassword.removeClass( "ui-state-error" );
		      var v=true;
		      v=v && checkEqual(rpassword,$("#password"),$("#rpasswordv"));
		      if(v){
		    $("#rpasswordd").addClass("ui-icon-circle-check");
		    }else {
		     $("#rpasswordd").addClass("ui-icon-circle-close");
		    };
		
		});
		
		$("#email").blur(function(){
		  var email=$(this);
		   $("#emailv").text("")
		              .removeClass("ui-state-highlight");
		   $("#emaild").removeClass("ui-icon-circle-check")
		              .removeClass("ui-icon-circle-close");
		   email.removeClass( "ui-state-error" );
		   var v=true;
		   // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
		    v=v && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. example@bookstore.com" , $("#emailv"));
		    if(v){
		    $("#emaild").addClass("ui-icon-circle-check");
		    }else {
		     $("#emaild").addClass("ui-icon-circle-close");
		    };
		
		})
		function updateTips(t,o) {
			o.text(t)
			.addClass("ui-state-highlight");
		    }
		function checkLength( o, n, min, max,ot ) {
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-state-error" );
				updateTips(n+" must be between "+ min+ " and " +max+" !",ot);
				return false;
			} else {
				return true;
			}
		}
		function checkRegexp( o, regexp,n,ot) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips(n,ot);
				return false;
			} else {
				return true;
			}
		}
		function checkEqual(o,r,ot){
		    if(o.val()!=r.val()){
		    o.addClass( "ui-state-error" );
		    updateTips("Password does not match!",ot);
		    return false;
		    }else{
		    return true;
		    }
		}
		
		
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

<title>Second Hand Book Communication</title>
</head>
<body style="background: #675423 url(resources/image/ui-bg_diamond_25_675423_10x8.png) 50% 50% repeat;margin:0;">
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
	
	<h3>About Our Store</h3>
	<div>
  <p>
  Our site is a platform for users to buy or sell the second hand book, and generate the information about second hand book stores.
  </p>
  </div>
	
</div>

  
</div>
<div id="right" style="width: 600px;float:left;background:#443113 url(resources/image/ui-bg_diamond_8_443113_10x8.png) 50% 50% repeat;height:1000px;">
<div id="dialog" title="Message Send">
	<p>Message has been send!!</p>
 </div>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Login</a></li>
		<li><a href="#tabs-2">Register</a></li>
		<li><a href="#tabs-3">Search</a></li>
		<li><a href="#tabs-4">Contact</a></li>
	</ul>
	<div id="tabs-1">
	<p><strong>LOGIN</strong></p>
	<c:if test="${loginerror!=null}">
	<div class="ui-widget">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<strong>Alert:</strong> ${loginerror}</p>
	</div>
 </div>
	</c:if>
	<c:if test="${message!=null}">
	<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Hey!</strong>${message}</p>
	</div>
</div>
	</c:if>
	<form action="j_spring_security_check" method="POST">
	<table>
    	<tr><td>USERNAME:</td><td><input type="text" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="text ui-widget-content ui-corner-all"/></td></tr>
    	<tr><td>PASSWORD:</td><td><input type="password" name="j_password" value="" class="text ui-widget-content ui-corner-all"/></td></tr>
    	
		</table>
		<input type="checkbox" name="_spring_security_remember_me" />Remember Me<br/>
		<input type="submit" value="login" id="button">    	
    </form>
	</div>
	<div id="tabs-2">
	<p><strong>REGISTER</strong></p>
	<c:if test="${registerror!=null}">
	<div class="ui-widget">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<strong>Alert:</strong> ${registerror}</p>
	</div>
</div>
	</c:if>
	<div class="ui-widget">
	<p class="validateTips">All form fields maked are required.</p>
	<form action="register" method="post" id="rform">
	<table>
	<tr><td style="width: 95px; ">Username*:</td><td style="width: 427px; "><input type="text" name="username" id="username" class="text ui-widget-content ui-corner-all" value="${user.username }"style="float:left"/><span id="usernamed" class="ui-icon" style="float: left; margin-right: .3em;"></span><br /><strong id="usernamev" style="float:left"></strong></td></tr>
	<tr><td>Password*:</td><td><input type="password" name="password" id="password"title="Must be at leat 4 digits" class="text ui-widget-content ui-corner-all"style="float:left"/><span id="passwordd" class="ui-icon" style="float: left; margin-right: .3em;"></span><br /><strong id="passwordv" style="float:left"></strong></td></tr>
	<tr><td>ReEnter Password*:</td><td><input type="password" name="rpassword" id="rpassword"title="Enter the Password Again" class="text ui-widget-content ui-corner-all"style="float:left"/><span id="rpasswordd" class="ui-icon" style="float: left; margin-right: .3em;"></span><br /><strong id="rpasswordv" style="float:left"></strong></td></tr>
	<tr><td>Email*:</td><td><input type="text" name="email" id="email" title="like:example@gmail.com" class="text ui-widget-content ui-corner-all" value="${user.email }"style="float:left"/><span id="emaild" class="ui-icon" style="float: left; margin-right: .3em;"></span><br /><strong id="emailv" style="float:left"></strong><br/></td></tr>
	</table>
	<br/>
	Address:<br/>
	<table>
	<tr><td>Street:</td><td><input type="text" name="street" class="text ui-widget-content ui-corner-all" value="${address.street }"/></td></tr>
	<tr><td>City:</td><td><input type="text" name="city" class="text ui-widget-content ui-corner-all" value="${address.city }"/></td></tr>
	<tr><td>State:</td><td><input type="text" name="state" class="text ui-widget-content ui-corner-all" value="${address.state}"/></td></tr>
	<tr><td>Zip:</td><td><input type="text" name="zip" class="text ui-widget-content ui-corner-all" value="${address.zip}"/></td></tr>
	</table>
	<div id="radioset">
	<input type="radio" name="role" value="buyer" id="radio1"  ${user.role=='buyer'? 'checked':' '} ${user.role==null? 'checked':' '}/><label for="radio1">Buyer</label>
	<input type="radio" name="role" value="seller" id="radio2" ${user.role=='seller'? 'checked':' '}/><label for="radio2">Seller</label>
	</div>
	<div id="store">
	<hr/>
	<p>Flowing only need to be completed by BOOK STORE OWNER!</p>
	<table >
	<tr><td style="width: 198px; ">Book Store Name:</td><td><input type="text" name="company" title="Name of Your Store" value="${seller.company}"class="text ui-widget-content ui-corner-all" style="width: 205px; "/></td></tr>
	<tr><td>WebSite(if any):</td><td><input type="text" name="website" class="text ui-widget-content ui-corner-all"value="${seller.website}" style="width: 372px; "/></td></tr>
	<tr><td>Description:</td><td><textarea rows="6" cols="43" name="description" class="textarea ui-widget-content ui-corner-all" >${seller.description}</textarea></td></tr>
	</table>
	</div>
	<div id="storeh">
	<input type="hidden" name="company" value=""/>
	<input type="hidden" name="website" value=""/>
	<input type="hidden" name="description" value=""/>
	</div>
	<center><input type="submit" value="SUBMIT"/></center>
	</form>
	
	</div>
	
	</div>
	<div id="tabs-3">
	<p><strong>SEARCH</strong></p>
	<c:if test="${search=='search'}">
	<form action="searchm" method="post">
	<input type="text" name="word" value="${word }" class="text ui-widget-content ui-corner-all" />
	<select name="type">
	<option value="title" ${type=='title'? 'selected':' ' }>By Title</option>
	<option value="author" ${type=='author'? 'selected':' ' }>By Author</option>
	<option value="isbn" ${type=='isbn'? 'selected':' ' }>By ISBN</option>
	</select>
	<input type="hidden" name="page" value="1"/>
	<input type="submit" value="GO" name="submit"/>
	</form>
	<table>
	<c:forEach var="book" items="${bookcatalog}">
	<tr>
	<td><a href="viewbookm?bid=${book.bid}"><img alt="${book.title}" src="image?bid=${book.bid}" width="90" height="120"/></a></td>
	<td>
	<p><a href="viewbookm?bid=${book.bid}">${book.title}</a><br/>written by ${book.author} </p>
	</td>
	</tr>
	</c:forEach>
	</table>
	<div style="text-align:center;">
	<c:if test="${pages>1}">
	<a href="searchm?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${page-1}" class="${page==1? 'current':'normal'}">&lt;&lt; </a>
	<c:forEach var="i" begin="1" end="${pages }">
	<a href="searchm?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${i}" class="${page==i? 'current':'normal'}"> <strong>&nbsp;${i}&nbsp;&nbsp;</strong> </a>
	</c:forEach>
	<a href="searchm?word=${word==null?'null': word}&type=${type==null?'title':type}&page=${page+1}" class="${page==pages? 'current':'normal'}"> &gt;&gt;</a>
	</c:if>
	</div>
	</c:if>
	<c:if test="${search=='detail'}">
	<div style="width: 600px">
	<a class="buttonlink" href="backsearchm" >&lt;&lt; BACK</a>
    </div>
    <div id="dialog-modal" title="Book Front Page">
	<img alt="${book.title}" src="image?bid=${book.bid}" width="480" height="640" align="right"/>
</div>
     <a href="#" id="dialog-link">
    <img alt="${book.title}" src="image?bid=${book.bid}" width="120" height="160" align="right"/></a>
    <div>
    <div>
    <table>
    <tr><td>Title:</td><td>${book.title}</td></tr>
    <tr><td>Author:</td><td>${book.author}</td></tr>
    <tr><td>ISBN:</td><td>${book.isbn}</td></tr>
    <tr><td>Price:</td><td>${book.price}</td></tr>
    <tr><td>Quantity:</td><td>${book.quantity}</td></tr>
    <tr><td>Description:</td><td>${book.description}</td></tr>
    <tr><td>Seller:</td><td><a href="sellerInfom?bid=${book.bid}">${book.seller.user.username}</a></td></tr>
    </table>
    </div>
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
    <a class="book" href="viewbookm?bid=${related.bid}"><img alt="${related.title}" src="image?bid=${related.bid}" width="90" height="120"/></a>
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
	<a class="buttonlink" href="backviewm?bid=${book.bid}">&lt;&lt; BACK</a>
	</div>
	<div>
	<img alt="" src="${map}">
	</div>
	<div>
	<table>
	<tr><td>User Name:</td><td>${seller.user.username }</td></tr>
    <tr><td style="width: 105px; ">Company Name:</td><td>${seller.company }</td></tr>
	<tr><td>WebSite<br/>(if any):</td><td><a href="${seller.website}">${seller.website }</a></td></tr>
	<tr><td>Description:</td><td>${seller.description }"</td></tr>
    </table>
     Store Address:<br />
    <table>
    <c:forEach var="address" items="${seller.user.addressList }">
    <tr><td style="width: 82px; ">Street:</td><td>${address.street}</td></tr>
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
	
	<div id="tabs-4">
	<p><strong>CONTACT INFORMATION</strong></p>
	<table>
	<tr><td style="width: 77px; ">Phone:</td><td>123-456-7890</td></tr>
	<tr><td>Email:</td><td><a href="mailto:info@secondhandbookcommunication.com?Subject=General%20Question">Send Mail</a></td></tr>
	<tr><td>Address<td><tr>
	<tr><td>Street:</td><td>99 Book Street</td></tr>
    <tr><td>City:</td><td>Boston</td></tr>
    <tr><td>State:</td><td>MA</td></tr>
    <tr><td>Zip:</td><td>02120</td></tr>
	</table>
	<button id="buttonmail">Leave Message:</button>
	<form action="sendEmail" method="post" id="sendEmail">
	<p>Subject:<input type="text" name="subject" style="width: 413px; " class="text ui-widget-content ui-corner-all"/></p>
	<p>Email:<input type="text" name="email" style="width: 429px; " class="text ui-widget-content ui-corner-all"/></p>
	Message:<textarea rows="5" cols="60" name="message" class="textarea ui-widget-content ui-corner-all"></textarea>
	<input type="submit" value="SEND">
	</form>
	
	</div>
	
</div>
</div>
</div>

</body>
</html>