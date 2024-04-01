// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here. Other Firebase libraries
// are not available in the service worker.
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');

// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object
firebase.initializeApp({
    apiKey: "AIzaSyCXV04KEJu2dTM8lq4rRKXC022f12NXHfg",
    authDomain: "ibalance-ede22.firebaseapp.com",
    projectId: "ibalance-ede22",
    storageBucket: "ibalance-ede22.appspot.com",
    messagingSenderId: "861999763903",
    appId: "1:861999763903:web:7952c14f6b4c08bc7a510a",
    measurementId: "G-R26BRYFKB7"
});

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();

// Handle incoming messages. Called when:
// - a message is received while the app has focus
// - the user clicks on an app notification created by a service worker
//   `messaging.onBackgroundMessage` handler.

messaging.onBackgroundMessage((payload) => {
  console.log(
    '[firebase-messaging-sw.js] Received background message ',
    payload
  );
  // Customize notification here
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png'
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});



/**
 * Copyright 2018 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// If the loader is already loaded, just stop.
if (!self.define) {
    let registry = {};
  
    // Used for `eval` and `importScripts` where we can't get script URL by other means.
    // In both cases, it's safe to use a global var because those functions are synchronous.
    let nextDefineUri;
  
    const singleRequire = (uri, parentUri) => {
      uri = new URL(uri + ".js", parentUri).href;
      return registry[uri] || (
        
          new Promise(resolve => {
            if ("document" in self) {
              const script = document.createElement("script");
              script.src = uri;
              script.onload = resolve;
              document.head.appendChild(script);
            } else {
              nextDefineUri = uri;
              importScripts(uri);
              resolve();
            }
          })
        
        .then(() => {
          let promise = registry[uri];
          if (!promise) {
            throw new Error(`Module ${uri} didn’t register its module`);
          }
          return promise;
        })
      );
    };
  
    self.define = (depsNames, factory) => {
      const uri = nextDefineUri || ("document" in self ? document.currentScript.src : "") || location.href;
      if (registry[uri]) {
        // Module is already loading or loaded.
        return;
      }
      let exports = {};
      const require = depUri => singleRequire(depUri, uri);
      const specialDeps = {
        module: { uri },
        exports,
        require
      };
      registry[uri] = Promise.all(depsNames.map(
        depName => specialDeps[depName] || require(depName)
      )).then(deps => {
        factory(...deps);
        return exports;
      });
    };
  }
  define(['./dev-dist/workbox-b5f7729d'], (function (workbox) { 'use strict';
  
    self.skipWaiting();
    workbox.clientsClaim();
  
    /**
     * The precacheAndRoute() method efficiently caches and responds to
     * requests for URLs in the manifest.
     * See https://goo.gl/S9QRab
     */
    workbox.precacheAndRoute([{
      "url": "registerSW.js",
      "revision": "3ca0b8505b4bec776b69afdba2768812"
    }, {
      "url": "index.html",
      "revision": "0.hs7uraavoi8"
    }], {});
    workbox.cleanupOutdatedCaches();
    workbox.registerRoute(new workbox.NavigationRoute(workbox.createHandlerBoundToURL("index.html"), {
      allowlist: [/^\/$/]
    }));
  
  }));
  