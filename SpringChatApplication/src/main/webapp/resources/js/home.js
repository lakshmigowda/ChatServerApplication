$(document).ready(function() {
	var self = $(this);
	self.createUser();
	self.sendMessage();
	self.getUsers();
});

(function($) {

	jQuery.fn.getUsers = function() {
		$(".listname")
				.click(
						function() {
							var chatroomName = ($(this).text()); // gets text contents of clicked li

							$
									.ajax({
										url : "/SpringChatApplication/getChatroom?chatroomName="
												+ chatroomName,
										type : "GET",
										success : function(data, textStatus,
												jqXHR) {
											var length = data.users.length;
											var html = "";
											for (var i = 0; i < length; i++) {
												html += "<p>"
														+ data.users[i].name
														+ "</p>"
											}
											$("#listusersforchatroom").html(

											html);
											var messagesLength = data.messages.length;
											var messagesHtml = "";
											for (var j = 0; j < messagesLength; j++) {

												messagesHtml += "<p>"
														+ data.messages[j].text
														+ "</p>"
											}
											$("#content").html(messagesHtml);

											document.cookie = "chatRoom="
													+ chatroomName;
										}
									});
						});
	}

	jQuery.fn.createUser = function() {
		var self = $(this);
		var userName = prompt('Please enter your username.');
		$.get("/SpringChatApplication/createUser?userName=" + userName,
				function(data, status) {
					//$('#result').html(data);
					if (data === "success") {
						document.cookie = "username=" + userName;
					} else {
						self.createUser();
					}
				});
	}

	jQuery.fn.sendMessage = function() {

		$("#send").click(function() {
			var text = $('#text').val();

			var cookies = document.cookie.split(";");
			var userName = '';
			var chatroom = '';
			for (var i = 0; i < cookies.length; i++) {
				var cookieName = cookies[i].split("=")[0].trim();
				if (cookieName === 'username') {
					userName = cookies[i].split("=")[1];
				} else if (cookieName === 'chatRoom') {
					chatroom = cookies[i].split("=")[1];
				}
			}

			$.ajax({
				url : "/SpringChatApplication/createMessage",
				type : "GET",
				success : function(data, textStatus, jqXHR) {

				}
			});
		});
	}

})(jQuery);
