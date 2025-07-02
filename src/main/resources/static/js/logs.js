function fetchLogs(email = '') {
  let url = '/api/logs';
  if (email) {
    url += `?email=${encodeURIComponent(email)}`;
  }

  fetch(url)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById('logTableBody');
      tbody.innerHTML = '';

      data.forEach(log => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td style="padding: 10px;">${log.id}</td>
          <td style="padding: 10px;">${log.email}</td>
          <td style="padding: 10px;">${new Date(log.loginTime).toLocaleString()}</td>
          <td style="padding: 10px;">${log.logoutTime ? new Date(log.logoutTime).toLocaleString() : '-'}</td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(err => console.error('Failed to load logs:', err));
}

// Called when "Search" button is clicked
function filterLogs() {
  const email = document.getElementById('emailFilter').value.trim();
  fetchLogs(email);
}

// Load all logs initially
document.addEventListener("DOMContentLoaded", () => {
  fetchLogs();
});
