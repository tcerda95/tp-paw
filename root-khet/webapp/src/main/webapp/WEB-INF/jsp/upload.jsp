<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title><spring:message code="formLabel.title" /></title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/upload-form.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/img-upload.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/snackbar.css"/>" rel="stylesheet">
	</head>

<body>
	<!--TODO: No se pone #fff el navbar-->
<spring:message code="formLabel.namePlaceholder" var="NamePlaceholder"/>
<spring:message code="formLabel.taglinePlaceholder" var="TaglinePlaceholder"/>
<spring:message code="formLabel.descriptionPlaceholder" var="DescriptionPlaceholder"/>
<spring:message code="formLabel.linkPlaceholder" var="LinkPlaceholder"/>
<spring:message code="formLabel.creatorNamePlaceholder" var="CreatorNamePlaceholder"/>
<spring:message code="formLabel.emailPlaceholder" var="EmailPlaceholder"/>
<spring:message code="formLabel.websitePlaceholder" var="WebsitePlaceholder"/>
<spring:message code="formLabel.postButton" var="PostButtonMessage"/>


<%@include file="includes/navbar.jsp" %>
	<div class="form-container container">
		<div class="row">
			<div class="row">
				<c:url value="/upload" var="postPath"/>
				<div class="col-md-10">
					<div class="col-sm-9 col-sm-offset-3">
						<div>
							<h2><spring:message code="formLabel.postProduct"/></h2>
						</div>
						<div>
							<h4><spring:message code="formLabel.giveUsInfo" /></h4>
						</div>
					</div>
					<form:form modelAttribute="uploadForm" action="${postPath}" method="post" enctype="multipart/form-data"
										 class="form-horizontal">
						<div class="row">
							<div class="col-md-9 col-md-offset-3 logo-form">
								<form:input class="image-input" type="file" path="logo" accept="image/*"/>
								<form:label id="logo-label" path="logo" style="text-align:center;">
									<div class="preview-container">
										<img src="#" class="preview-image">
										<span class="add-img-text">
											<span class="glyphicon glyphicon-plus"></span>
											<spring:message code="formLabel.logo"/>
										</span>
										<div class="remove-btn glyphicon glyphicon-remove"></div>
									</div>
								</form:label>
								<form:errors path="logo" cssClass="form-error" element="p"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="name" class="col-sm-3 control-label"><spring:message code="formLabel.productName" /></form:label>
								<div class="col-sm-9">
									<form:input type="text" path="name" class="form-control" placeholder="${NamePlaceholder}"/>
									<form:errors path="name" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="shortDescription" class="col-sm-3 control-label"><spring:message code="formLabel.tagline"/></form:label>
								<div class="col-sm-9">
									<form:textarea type="text" path="shortDescription" placeholder="${TaglinePlaceholder}" class="form-control" rows="2"/>
									<form:errors path="shortDescription" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="description" class="col-sm-3 control-label"><spring:message code="formLabel.description"/></form:label>
								<div class="col-sm-9">
									<form:textarea type="text" path="description" class="form-control" rows="4" placeholder="${DescriptionPlaceholder}"/>
									<form:errors path="description" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="website" class="col-sm-3 control-label"><spring:message code="formLabel.productwebsite"/></form:label>
								<div class="col-sm-9">
									<form:textarea type="text" path="website" class="form-control" rows="1" placeholder="${WebsitePlaceholder}"/>
									<form:errors path="website" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="category" class="col-sm-3 control-label"><spring:message code="formLabel.category"/></form:label>
								<div class="col-sm-9">
									<form:select path="category" class="form-control category-select">
										<c:forEach var="catOpt" items="${categories}">
											<option value="${catOpt}" <c:if test="${catOpt eq 'OTHER'}">selected="true"</c:if>><spring:message code="category.${catOpt.lowerName}"/></option>
										</c:forEach>
									</form:select>									
									<form:errors path="category" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-9 col-sm-offset-3">
								<p class="add-message"><spring:message code="formLabel.addOneImageOrVideo"/></p>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 form-group" id="product-images">
								<div class="col-sm-9 col-sm-offset-3">
									<c:forEach items="${uploadForm.images}" varStatus="status">
										<div class="col-md-3"> 
											<form:input class="image-input" type="file" path="images[${status.index}].file" accept="image/*"/>
											<form:label path="images[${status.index}].file">
												<div class="preview-container">
													<img class="preview-image" src="#">
													<span class="add-img-text">
														<span class="glyphicon glyphicon-plus"></span>
														<spring:message code="formLabel.image"/>
													</span>
													<div class="remove-btn glyphicon glyphicon-remove"></div>
												</div>
											</form:label>
											<form:errors path="images[${status.index}].file" cssClass="form-error" element="p"/>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-9 col-sm-offset-3 video-section">
								<h5><spring:message code="formLabel.videos"/></h5>
								<c:forEach items="${uploadForm.videos}" varStatus="status">
									<div class="col-md-12 form-group video-form">
										<form:label path="videos[${status.index}].url"><spring:message code="formLabel.video" /></form:label>
										<form:input type="url" path="videos[${status.index}].url" class="form-control" placeholder="${LinkPlaceholder}"/>
										<form:errors path="videos[${status.index}].url" cssClass="form-error" class ="form-error" element="p"/>
									</div>
								</c:forEach>
								<div class="col-md-12">
									<form:errors path="videos" cssClass="form-error" element="p"/>
									<form:errors path="images" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						<div class="row row-centered">
							<div class="col-md-12">
								<div class="col-sm-9 col-sm-offset-3">
									<input class="ps-btn-red btn submit-btn" type="submit" value="${PostButtonMessage}" />
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	<%@include file="includes/footer.jsp"%>
	</div>
	<%@include file="includes/scripts.jsp"%>
	<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
	</body>
</html>