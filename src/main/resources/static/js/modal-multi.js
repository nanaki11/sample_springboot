$(function() {
	$('.notice-modal').on('click', function() {
		var noticeStr = $(this).find('.notice-main').val();
		$('.modal-notice-main').text(noticeStr);
		$('.modal-overlay-notice, .modal-content-notice').fadeIn();
		return false;
	});
	$('.modal-close').on('click', function() {
		$('.modal-overlay-notice, .modal-content-notice').fadeOut();
		return false;
	});

	locateCenter();
	$(window).resize(locateCenter);

	function locateCenter() {
		let width = $('body').width();
		let height = $('body').height();

		let cw = $('.modal-content-notice').outerWidth();
		let ch = $('.modal-overlay-notice').outerHeight();

		$('.modal-content-notice').css({
			'left' : ((width - cw) / 2) + 'px',
			'top' : ((height - ch) / 3) + 'px'
		});
	}
});
$(function() {
	$('.content-modal').on('click', function() {
		var contentStr = $(this).find('.content-main').val();
		$('.modal-content-main').text(contentStr);
		$('.modal-overlay-content, .modal-content-content').fadeIn();
		return false;
	});
	$('.modal-close').on('click', function() {
		$('.modal-overlay-content, .modal-content-content').fadeOut();
		return false;
	});

	locateCenter();
	$(window).resize(locateCenter);

	function locateCenter() {
		let width = $('body').width();
		let height = $('body').height();

		let cw = $('.modal-content-content').outerWidth();
		let ch = $('.modal-overlay-content').outerHeight();

		$('.modal-content-content').css({
			'left' : ((width - cw) / 2) + 'px',
			'top' : ((height - ch) / 3) + 'px'
		});
	}
});



$(function() {
	$(window).on('load', function() {
		var path = location.pathname;
		if (path == "/communitylist/join") {
			var leftPosition = (($(window).width() - $("#community-join").outerWidth(true)) / 2);
			$("#community-join").css({"left" : leftPosition + "px"});
			$("#community-join").show();
		} else if (path == "/communitylist/withdraw") {
			var leftPosition = (($(window).width() - $("#community-withdraw").outerWidth(true)) / 2);
			$("#community-withdraw").css({"left": leftPosition + "px"});
			$("#community-withdraw").show();
		}
	});
	$("[id^=open-confirmation-dialog]").on("click", function() {
		var targetId = "#community-confirmation" + $(this).val();
		var leftPosition = (($(window).width() - $(targetId).outerWidth(true)) / 2);
		$(targetId).css({"left" : leftPosition + "px"});
		$(targetId).show();
	});
	$(".dialog-close").on("click", function() {
		$(this).parents(".dialog").hide();
	});
});