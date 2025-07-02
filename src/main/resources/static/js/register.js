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

document.querySelector("form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent default form submission

    const formData = new FormData(this); // Get form data

    try {
        const response = await fetch("createUser", {
            method: "POST",
            body: formData
        });

        if (response.ok) {
            // Show success message
            const successMessage = document.getElementById("success-message");
            successMessage.style.display = "block";
            successMessage.innerText = "🎉 Registration successful! Redirecting to Sign In...";

            // Reset form fields
            this.reset();

            // Redirect after 2 seconds
            setTimeout(() => {
                window.location.href = "signin"; // Change URL as per your setup
            }, 2000);
        } else {
            alert("❌ Registration failed! Please try again.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("❌ Server error! Please try again later.");
    }
});
