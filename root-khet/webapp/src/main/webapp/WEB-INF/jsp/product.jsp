<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
			<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title>Product Seek</title>
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
	<link href="<c:url value="/resources/css/product.css" />" rel="stylesheet" />
	<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	<div class="container">
		<div class="row" id="carouselHolder">
			<div class="col-md-6 col-md-offset-3">
				<div class="multiple-items carousel highlighted">
					<c:forEach items="${videos}" var="video">
						<div class="embed-responsive embed-responsive-16by9 video-holder">
							<iframe class="embed-responsive-item video-item" src="//www.youtube.com/embed/${video.videoId}?rel=0" allowfullscreen></iframe>
						</div>
					</c:forEach>
					<c:forEach items="${images}" var="image">
						<div class="image-holder">
							<img class="image-item" src="<c:url value="/product/${product.id}/image/${image}"/>" >
						</div>
					</c:forEach>
				</div>
			</div>
	    </div>
	    <div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="row">
					<div class="col-md-7">
						<div class="row product-item vertical-align highlighted">
							<div class="col-md-3 product-logo">
								<img src="<c:url value="/product/${product.id}/logo"/>">
							</div>
							<div class="col-md-9 product-info-box">
								<div class="row col-md-12">
									<div class="row product-name">
										<div class="col-md-12">
											<p><c:out value="${product.name}"/></p>
										</div>
									</div>
									<div class="row product-short-description">
										<div class="col-md-12">
											<p><c:out value="${product.shortDescription}"/></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-md-offset-1 creator-item highlighted">
						<div class="row">
							<div class="col-md-12">
								<div class="row product-name">
									<div class="col-md-12">
										<p><spring:message code="productPage.creator" /></p>
									</div>
								</div>
								<div class="row product-short-description">
									<div class="col-md-12">
										<p><c:out value="${user.name}"/></p>
									</div>
								</div>
								<div class="row product-short-description">
									<div class="col-md-12">
										<p><c:out value="${user.mail}"/></p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row long-description">
					<div class="col-md-12">
						<p><c:out value="${product.description}" /></p>
						<div class="back-to-products">
							<a href="<c:url value="/"/>"><spring:message code="productPage.backToProducts" /></a>
						</div>
					</div>
				</div>
    	</div>
    </div>
	</div>
	<!-- 	This span is used by javascript in order to know the size of the corousel -->
	<span id="carouselSize" style="display:none"> <c:out value="${carouselSize}" /></span>	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- 	Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.min.js"></script>
    <script src="<c:url value="/resources/js/product.js" />"></script>	
</body>
</html>