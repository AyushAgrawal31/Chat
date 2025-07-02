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