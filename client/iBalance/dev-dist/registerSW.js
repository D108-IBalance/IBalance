if('serviceWorker' in navigator) navigator.serviceWorker.register('/firebase-messaging-sw.js', { scope: '/', type: 'classic' })