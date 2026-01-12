// Progressive Web App Installation Handler
let deferredPrompt;
const installPrompt = document.createElement('div');
installPrompt.className = 'install-prompt';
installPrompt.innerHTML = `
  <div class="install-prompt-text">
    <h4>📱 Install NextGenKart App</h4>
    <p>Add to your home screen for a better experience!</p>
  </div>
  <button class="install-btn" id="install-app-btn">Install</button>
  <button class="install-close" id="close-install-prompt">&times;</button>
`;

// Listen for the beforeinstallprompt event
window.addEventListener('beforeinstallprompt', (e) => {
  // Prevent the mini-infobar from appearing on mobile
  e.preventDefault();
  // Stash the event so it can be triggered later
  deferredPrompt = e;
  
  // Show the install prompt after a delay
  setTimeout(() => {
    document.body.appendChild(installPrompt);
    installPrompt.classList.add('show');
  }, 3000);
});

// Handle install button click
document.addEventListener('click', (e) => {
  if (e.target && e.target.id === 'install-app-btn') {
    // Hide the install prompt
    installPrompt.classList.remove('show');
    
    if (deferredPrompt) {
      // Show the install prompt
      deferredPrompt.prompt();
      
      // Wait for the user to respond to the prompt
      deferredPrompt.userChoice.then((choiceResult) => {
        if (choiceResult.outcome === 'accepted') {
          console.log('User accepted the install prompt');
        } else {
          console.log('User dismissed the install prompt');
        }
        deferredPrompt = null;
      });
    }
  }
  
  // Handle close button
  if (e.target && e.target.id === 'close-install-prompt') {
    installPrompt.classList.remove('show');
    setTimeout(() => {
      if (installPrompt.parentNode) {
        installPrompt.parentNode.removeChild(installPrompt);
      }
    }, 300);
  }
});

// Handle app installed event
window.addEventListener('appinstalled', () => {
  console.log('NextGenKart app was installed');
  // Hide the install prompt
  if (installPrompt.parentNode) {
    installPrompt.classList.remove('show');
    setTimeout(() => {
      installPrompt.parentNode.removeChild(installPrompt);
    }, 300);
  }
});

// Register Service Worker
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/service-worker.js')
      .then(registration => {
        console.log('Service Worker registered successfully:', registration.scope);
      })
      .catch(error => {
        console.log('Service Worker registration failed:', error);
      });
  });
}

// Check if app is installed
function isAppInstalled() {
  if (window.matchMedia('(display-mode: standalone)').matches || 
      window.navigator.standalone === true) {
    return true;
  }
  return false;
}

// Show different UI if app is installed
if (isAppInstalled()) {
  console.log('Running as installed PWA');
  document.body.classList.add('pwa-installed');
}

// Handle network status
window.addEventListener('online', () => {
  console.log('Back online');
  const offlineBanner = document.querySelector('.offline-banner');
  if (offlineBanner) {
    offlineBanner.remove();
  }
});

window.addEventListener('offline', () => {
  console.log('Connection lost');
  const offlineBanner = document.createElement('div');
  offlineBanner.className = 'offline-banner';
  offlineBanner.style.cssText = `
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    background: #ff9800;
    color: white;
    padding: 10px;
    text-align: center;
    z-index: 10001;
    font-weight: bold;
  `;
  offlineBanner.textContent = '⚠️ No internet connection. Some features may not work.';
  document.body.insertBefore(offlineBanner, document.body.firstChild);
});

// iOS/Safari specific handling
function isIOS() {
  return /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
}

function isInStandaloneMode() {
  return ('standalone' in window.navigator) && (window.navigator.standalone);
}

// Show iOS installation instructions if on iOS and not installed
if (isIOS() && !isInStandaloneMode() && !sessionStorage.getItem('iosInstallPromptShown')) {
  setTimeout(() => {
    const iosPrompt = document.createElement('div');
    iosPrompt.className = 'install-prompt show';
    iosPrompt.innerHTML = `
      <div class="install-prompt-text">
        <h4>📱 Install on iOS</h4>
        <p>Tap <strong>Share</strong> button, then <strong>Add to Home Screen</strong></p>
      </div>
      <button class="install-close" onclick="this.parentElement.classList.remove('show'); sessionStorage.setItem('iosInstallPromptShown', 'true');">&times;</button>
    `;
    document.body.appendChild(iosPrompt);
  }, 3000);
}
