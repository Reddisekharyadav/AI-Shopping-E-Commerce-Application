// Placeholder for animations.js

// JavaScript for profile sidebar functionality

document.addEventListener("DOMContentLoaded", () => {
  const profilePic = document.getElementById("profile-pic");
  const sidebar = document.getElementById("profile-sidebar");
  const closeButton = document.getElementById("close-sidebar");

  profilePic.addEventListener("click", () => {
    sidebar.style.transform = "translateX(0)";
  });

  closeButton.addEventListener("click", () => {
    sidebar.style.transform = "translateX(100%)";
  });
});