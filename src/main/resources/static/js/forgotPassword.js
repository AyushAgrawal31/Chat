function togglePasswordVisibility() {
	const passwordInput = document.getElementById('password');
	const eyeIcon = document.getElementById('togglePassword');

	if (passwordInput.type === 'password') {
		passwordInput.type = 'text'; // Show password
		eyeIcon.textContent = '🙈'; // Change icon to indicate password is visible
	} else {
		passwordInput.type = 'password'; // Hide password
		eyeIcon.textContent = '👁️'; // Change icon back to original
	}
}

function toggleConfirmPasswordVisibility() {
	const passwordInput = document.getElementById('confirmPassword');
	const eyeIcon = document.getElementById('toggleConfirmPassword');

	if (passwordInput.type === 'password') {
		passwordInput.type = 'text'; // Show password
		eyeIcon.textContent = '🙈'; // Change icon to indicate password is visible
	} else {
		passwordInput.type = 'password'; // Hide password
		eyeIcon.textContent = '👁️'; // Change icon back to original
	}
}

document.getElementById("confirmPassword").addEventListener("input", function () {
    const password = document.getElementById("password").value;
    const confirmPassword = this.value;

    if (password !== confirmPassword) {
        this.setCustomValidity("Passwords do not match.");
    } else {
        this.setCustomValidity("");
    }
});
