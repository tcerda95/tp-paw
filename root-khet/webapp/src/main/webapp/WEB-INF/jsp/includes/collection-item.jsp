<div class="panel panel-default collection-panel">
	<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-${favList.id}" aria-expanded="false" aria-controls="collapse-${favList.id}">
		<div class="panel-heading" role="tab" id="heading-${favList.id}">
			<div class="row">
				<div class="col-md-10">
					<span class="collection-title">
						<c:out value="${favList.name}"></c:out>
					</span>
					<span class="collection-info">
						<c:out value="${fn:length(favList.productList)}"></c:out> productos
					</span>
				</div>
				<div class="col-md-2">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${loggedUser.userId == profileUser.userId}">
							<c:url value="/favlist/delete/${favList.id}" var="delete"/>
							<form:form class="delete-collection-form pull-right" action="${delete}" method="post">
								<button class="btn btn-default collection-delete-btn" type="submit">
									<span class="glyphicon glyphicon-trash"></span>
								</button>
							</form:form>	
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</div>
	</a>
	<div id="collapse-${favList.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-${favList.id}">
		<div class="panel-body">
			<c:forEach items="${favList.productList}" var="product">
				<a class="collection-product-list-item" href="<c:url value="/product/${product.id}"/>" >
					<c:url value="/vote/product/${product.id}" var="vote"/>
					<div class="row center-flex">
						<div class="col-md-2 collection-product-logo center-flex">
							<img src="<c:url value="/product/${product.id}/logo"/>">
						</div>
						<div class="col-md-7">
							<span class="collection-product-name capitalize-firstLetter">
								<c:out value="${product.name}"/>
							</span>
						</div>
						<div class="col-md-3">
							<span class="collection-upvote-info">
								<span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
								<c:out value="${product.votesCount}"/>
							</span>
						</div>
					</div>
				</a>
			</c:forEach>	
		</div>
	</div>
</div>
