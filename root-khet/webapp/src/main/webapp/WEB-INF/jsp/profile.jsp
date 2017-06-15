<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <c:set var="capitalizedUserNameNoEscape" value="${fn:toUpperCase(fn:substring(profileUser.name, 0, 1))}${fn:substring(profileUser.name, 1,fn:length(profileUser.name))}" />

    <c:set var="capitalizedUserName" value="${fn:escapeXml(capitalizedUserNameNoEscape)}" />
    
	<html>
		<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
			<title><spring:message code="Profile.title" arguments="${capitalizedUserName}"/></title>
			<link
						href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
						rel="stylesheet"
						integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
						crossorigin="anonymous">
			<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/profile.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/customizeUser.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/img-upload.css"/>" rel="stylesheet">
      		<link href="<c:url value="/resources/css/modal.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/zrp.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/tabs.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/snackbar.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/product-item.css"/>" rel="stylesheet">
			<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

		</head>
		<script src="<c:url value="/resources/js/upvote.js" />"></script>
		<%@include file="includes/navbar.jsp" %>
		<body>
			<div class="container">
				<div class="row">
					<div class="col-md-3 profile-info-box">
							<sec:authorize access="isAuthenticated()">
								<c:if test="${loggedUser.userId == profileUser.userId}">
									<div class="row settings-row">
										<div class="col-md-offset-10">
											<div class="dropdown">
											  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
											    <span class="glyphicon glyphicon-cog"></span>
											    <span class="caret"></span>
											  </button>
											  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											    <li class="pointer-cursor"><a
											    	data-toggle="modal" data-target="#changePictureModal">
														<spring:message code="Profile.settings.changePicture"/>
													</a></li>
											    <li class="pointer-cursor"><a data-toggle="modal" data-target="#changePassModal">
											    	<spring:message code="Profile.settings.changePassword"/>
											    	</a></li>
											  </ul>
											</div>
										</div>
										</div>
									</c:if>						
							</sec:authorize>
							
								<div class="row img-row">
									<div class="col-md-12">
										<img class="profile-img" src="<c:url value="/profile/${profileUser.userId}/profilePicture"/>">
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="profile-username">
											<span class="glyphicon glyphicon-user"></span>
											<p><c:out value="${capitalizedUserName}"></c:out></p>
										</div>	
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<a class="profile-mail" href="mailto:<c:out value="${profileUser.email}"/>">
											<span class="glyphicon glyphicon-envelope"></span>
											<p><c:out value="${profileUser.email}"></c:out></p>
										</a>	
									</div>
								</div>
						
					</div>
					<div class="col-md-7 col-md-offset-1">
						<div class="row">
							<div class="col-md-10 col-md-offset-1">
								<div class="row tabs-row">
									<div class="">
										<ul class="nav nav-pills nav-justified profile-tabs">
											<c:set var="activeTab" value="${empty products && not empty votedProducts}"></c:set>

											<li role="presentation" class="${!activeTab ? 'active' : 'none' }"><a href="#uploadedProducts-pane" data-toggle="tab"><spring:message code="Profile.Tab.uploadedProducts"/><span class="badge"><c:out value="${fn:length(products)}"/></span></a></li>
											<li role="presentation" class="${activeTab ? 'active' : 'none' }"><a href="#votedProducts-pane" data-toggle="tab"><spring:message code="Profile.Tab.votedProducts"/><span class="badge tab-badge"><c:out value="${fn:length(votedProducts)}"/></span></a></li>
											<li role="presentation" class="none"><a href="#favList-pane" data-toggle="tab"><spring:message code="Profile.Tab.favlist"/><span class="badge tab-badge"><c:out value="${fn:length(favlistSet)}"/></span></a></li>
											
										</ul>
									</div>
								</div>

							<div class="tab-content">
								<div id="uploadedProducts-pane" class="tab-pane fade row result-for-products ${!activeTab ? 'active in' : 'none' }">
									<c:choose>
										<c:when test="${empty products}">
											<div class="zrp" id="user-products-zrp">
												<h2><spring:message code="userZRP.empty"/></h2>
												<h3><spring:message code="userZRP.noProducts" arguments="${capitalizedUserName}"/></h3>
											</div>
										</c:when>
										<c:otherwise>
										<div class="col-md-12 product-list">
											<c:forEach items="${products}" var="product">
												<%@include file="includes/deleteModal.jsp"%></%@include>
												<a href="<c:url value="/product/${product.id}"/>">
													<%@include file="includes/product-item-delete.jsp"%></%@include>
												</a>
											</c:forEach>
										</div>
									</c:otherwise>
									</c:choose>		
								</div>

								<div id="votedProducts-pane" class="tab-pane fade row result-for-products ${activeTab ? 'active in' : 'none' }">
									<c:choose>
											<c:when test="${empty votedProducts}">
												<div class="zrp" id="user-products-zrp">
													<h2><spring:message code="userZRP.empty"/></h2>
													<h3><spring:message code="userZRP.noVotedProducts" arguments="${capitalizedUserName}"/></h3>
												</div>
											</c:when>
											<c:otherwise>
											<div class="col-md-12 product-list">
												<c:forEach items="${votedProducts}" var="product">
													<a href="<c:url value="/product/${product.id}"/>">
														<%@include file="includes/product-item.jsp"%></%@include>
													</a>
												</c:forEach>
											</div>
										</c:otherwise>
									</c:choose>		
								</div>
							
								<div id="favList-pane" class="tab-pane fade row result-for-products none">
									
									<sec:authorize access="isAuthenticated()">
										<c:if test="${loggedUser.userId == profileUser.userId}">
											<button class="voters-popover-btn" rel="popover" data-popover-content="#favlistPopover" data-placement="bottom"  >
												<spring:message code="Profile.createFavLists"></spring:message>
											</button>
											
											<%@include file="includes/FavListPopOver.jsp" %>
										</c:if>
									</sec:authorize>
									
									<c:choose>
										<c:when test="${empty favlistSet}">
											<div class="zrp" id="user-products-zrp">
												<h2><spring:message code="userZRP.empty"/></h2>
												<h3><spring:message code="userZRP.noFavLists" arguments="${capitalizedUserName}"/></h3>
											</div>
											<c:url value="/favlist/create" var="postPath" />										
										</c:when>
										<c:otherwise>
										<div class="col-md-12 product-list">
											<c:forEach items="${favlistSet}" var="favList">
												<a href="<c:url value="/favlist/${favList.id}"/>">
													<c:out value="${favList.name}"></c:out>
												</a>
											</c:forEach>
										</div>
									</c:otherwise>
									</c:choose>	
								</div>
							</div>
						</div>
					</div>
						
			</div>
			</div>
			<%@include file="includes/footer.jsp"%></%@include>
		</div>
		</body>
		<%@include file="includes/changePictureModal.jsp"%>
		<%@include file="includes/changePasswordModal.jsp"%>
    <%@include file="includes/deleteModal.jsp"%>
		<%@include file="includes/scripts.jsp"%>

		<script src="<c:url value="/resources/js/profile.js" />"></script>		
		<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
		<script src="<c:url value="/resources/js/product-list.js" />"></script>
	</html>