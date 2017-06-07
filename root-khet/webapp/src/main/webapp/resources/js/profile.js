function glowObject(object) {
	object.addClass('glow-active');
	setTimeout(function() {
		object.addClass('glow-remove');
	}, 600);
}

$(document).ready(function() {
	if(passError)
		$("#changePassModal").modal("show");

	if(passFeedback)
		$("#feedbackChangePassModal").modal("show");

	if(imgError)
		$("#changePictureModal").modal("show");

	if(productDeleted)
		$("#feedbackDeleteModal").modal("show");

	if(imgFeedback)
		glowObject($('.profile-img'));
	
	// to avoid propagation return false
	$('.delete-product-button').on('click', function() {
		$('#deleteModal' + $(this).data('product-id')).modal('show');
		return false;
	});
	
	$('.upvote-btn').on('click', function() {
		return false;
	});
});
