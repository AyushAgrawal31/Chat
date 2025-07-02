'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var startChatButton = document.querySelector('#startChat');

var stompClient = null;
var username = null;
var chatRoom = "general";

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
	
	var fullNameElement = document.querySelector("#fullName");

	if (fullNameElement) {
		username = fullNameElement.textContent.replace("Name: ", "").trim(); // Extract the full name
	}

	if (username) {
		usernamePage.classList.add('hidden');
		chatPage.classList.remove('hidden');

		var socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, onConnected, onError);
	}
	event.preventDefault();
}


function onConnected() {
	stompClient.subscribe('/topic/general', onMessageReceived);

	stompClient.send("/app/general.addUser",
		{},
		JSON.stringify({ sender: username, type: 'JOIN' })
	)

	connectingElement.classList.add('hidden');
}


function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}


function sendMessage(event) {
	var messageContent = messageInput.value.trim();
	if (messageContent && stompClient) {
		var chatMessage = {
			sender: username,
			content: messageInput.value,
			type: 'CHAT'
		};
		stompClient.send("/app/general.sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}


function onMessageReceived(payload) {
	var message = JSON.parse(payload.body);

	var messageElement = document.createElement('li');

	if (message.type === 'JOIN') {
		messageElement.classList.add('event-message');
		message.content = message.sender + ' joined!';
	} else if (message.type === 'LEAVE') {
		messageElement.classList.add('event-message');
		message.content = message.sender + ' left!';
	} else {
		messageElement.classList.add('chat-message');

		var avatarElement = document.createElement('i');
		var avatarText = document.createTextNode(message.sender[0]);
		avatarElement.appendChild(avatarText);
		avatarElement.style['background-color'] = getAvatarColor(message.sender);

		messageElement.appendChild(avatarElement);

		var usernameElement = document.createElement('span');
		var usernameText = document.createTextNode(message.sender);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(message.content);
	textElement.appendChild(messageText);

	messageElement.appendChild(textElement);

	messageArea.appendChild(messageElement);
	messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
	var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}
	var index = Math.abs(hash % colors.length);
	return colors[index];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true)
startChatButton.addEventListener('click', connect);


function exitChat() {
	if (stompClient && stompClient.connected) {
		stompClient.unsubscribe('/topic/general');
		setTimeout(() => {
			stompClient.disconnect();
		}, 100); 
	}

	chatPage.classList.add('hidden');
	usernamePage.classList.remove('hidden');
}

document.querySelector('#exitChat').addEventListener('click', exitChat);

document.addEventListener("DOMContentLoaded", () => {
	const adminIcon = document.getElementById("adminIcon");
	const adminPopup = document.getElementById("adminPopup");
	const closeAdminPopup = document.getElementById("closeAdminPopup");
	const blockBtn = document.getElementById("blockBtn");
	const unblockBtn = document.getElementById("unblockBtn");

	adminIcon.addEventListener("click", (e) => {
		e.stopPropagation();
		adminPopup.style.display = "block";
	});

	closeAdminPopup.addEventListener("click", () => {
		adminPopup.style.display = "none";
	});

	window.addEventListener("click", (event) => {
		if (!adminPopup.contains(event.target) && event.target !== adminIcon) {
			adminPopup.style.display = "none";
		}
	});

	blockBtn.addEventListener("click", () => {
		const username = document.getElementById("blockUsername").value.trim();
		if (username) {
			alert(`Blocked user: ${username}`);
			// TODO: WebSocket/API call here
		}
	});

	unblockBtn.addEventListener("click", () => {
		const username = document.getElementById("blockUsername").value.trim();
		if (username) {
			alert(`Unblocked user: ${username}`);
			// TODO: WebSocket/API call here
		}
	});
});

document.addEventListener("DOMContentLoaded", function () {
	const blockBtn = document.getElementById("blockBtn");
	const unblockBtn = document.getElementById("unblockBtn");
	const regNoInput = document.getElementById("blockUsername");

	blockBtn.addEventListener("click", function () {
		const regNo = regNoInput.value.trim();
		if (regNo) {
			fetch(`/admin/block?regNo=${regNo}`, {
				method: "POST"
			})
			.then(response => response.text())
			.then(data => {
				alert(data);
				regNoInput.value = "";
			})
			.catch(error => {
				console.error("Error blocking user:", error);
				alert("Error blocking user");
			});
		} else {
			alert("Please enter a valid Registration Number.");
		}
	});

	unblockBtn.addEventListener("click", function () {
		const regNo = regNoInput.value.trim();
		if (regNo) {
			fetch(`/admin/unblock?regNo=${regNo}`, {
				method: "POST"
			})
			.then(response => response.text())
			.then(data => {
				alert(data);
				regNoInput.value = "";
			})
			.catch(error => {
				console.error("Error unblocking user:", error);
				alert("Error unblocking user");
			});
		} else {
			alert("Please enter a valid Registration Number.");
		}
	});
});



