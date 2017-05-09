<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<html>
		<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
			<title><spring:message code="default.title" /></title>
			<link
						href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
						rel="stylesheet"
						integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
						crossorigin="anonymous">
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.css"/>
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick-theme.css"/>
			<link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/profile.css"/>" rel="stylesheet">
			
			<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

		</head>
		<body>
			<%@include file="includes/navbar.jsp" %>
			<div class="container">
				<div class="row">
					<div class="col-md-3 profile-info-box">
					
								<div class="row img-row">
									<img class="profile-img" src="<c:url value="/profile/${us.userId}/profilePicture"/>">
								</div>
								<div class="row">
									<h3><c:out value="${us.name}"></c:out></h3>	
								</div>
								<div class="row">
									<h3><c:out value="${us.email}"></c:out></h3>	
								</div>
						
					</div>
					<div class="col-md-8 activity-box">
						<div class="col-md-12 products-title">
							<h2><spring:message code="uploadedProductsTitle"/></h2>
							<div class="col-md-12 product-list">
						<c:choose>
							<c:when test="${products.isEmpty()}">
								<!-- FALTA UNA IMAGEN PARA ZRP, INSERTAR ACA -->
								<h2><c:out value="ZRP"></c:out>
								</h2>
							</c:when>
							<c:otherwise>
								<c:forEach items="${products}" var="product">
									<a href="<c:url value="/product/${product.id}"/>">
										<div class="row product-list-item vertical-align">
											<div class="col-md-3 product-logo">
												<img src="<c:url value="/product/${product.id}/logo"/>">
											</div>
											<div class="col-md-9 product-info-box">
												<div class="row col-md-12">
													<div class="row product-name">
														<sec:authorize access="isAuthenticated()">
															<c:choose>
																<c:when test="${loggedUser.userId == profileUser.userId}">
																	<div class="col-md-10">
																		<p><c:out value="${product.name}"/></p>
																	</div>
																	<div class="col-md-1 col-md-offset-1">
																		<span id="delete${product.id}" class="glyphicon glyphicon-trash delete-product-button"></span>
																	</div>
																</c:when>
																<c:otherwise>
																	<div class="col-md-12">
																		<p><c:out value="${product.name}"/></p>
																	</div>
																</c:otherwise>
															</c:choose>
														</sec:authorize>
														<sec:authorize access="isAnonymous()">
															<div class="col-md-12">
																		<p><c:out value="${product.name}"/></p>
															</div>
														</sec:authorize>
													</div>
													<div class="row product-short-description">
														<div class="col-md-12">
															<p><c:out value="${product.shortDescription}"/></p>
														</div>
													</div>
													<div class="row product-category">
														<div class="col-md-3 categoryTag">
															<p><spring:message code="category.${product.category.lowerName}"/></p>
														</div>
													</div>
												</div>
											</div>
										</div>	
									</a>
									<!-- The Modal -->
									<sec:authorize access="isAuthenticated()">
										<c:if test="${loggedUser.userId == profileUser.userId}">
											<div id="modal${product.id}" class="row modal">
											  <!-- Modal content -->
												  <div class="col-md-4 col-md-offset-4 modal-content">
												    <span id ="closeModal${product.id}" class="close-modal">&times;</span>
												    <div class="row">
												    	<div class="col-md-12">
												    		<p class="modal-text"><spring:message code="productPage.modal.text" /></p>
												  		</div>
												  	</div>
												  	<div class="row modal-buttons-holder">
												  		<div class="col-md-1 col-md-offset-4">
															<c:url value="/delete/product/${product.id}" var="deletePath" />
															<form:form action="${deletePath}" method="post">
																<input type="submit" class="ps-btn btn" value="<spring:message code="Profile.modal.leftButton"/>" />
												  			</form:form>
												  		</div>
												  		<div class="col-md-1 col-md-offset-1">
															<p id="leftModalButton${product.id}" class="ps-btn btn modal-left-button"><spring:message code="Profile.modal.rightButton" /></p>
												  		</div>
												  	</div>
												  </div>
											</div>
										</c:if>
									</sec:authorize>		
								</c:forEach>
							</c:otherwise>
					</c:choose>									
					</div>
					
					</div>
				</div>	
			
			</div>
			<%@include file="includes/footer.jsp"%>
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
			<script
							src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- 	Include all compiled plugins (below), or include individual files as needed -->
			<script
							src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
							integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
							crossorigin="anonymous"></script>
			<script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.min.js"></script>
			<script src="<c:url value="/resources/js/profile.js" />"></script>		
		</body>
		
	</html>