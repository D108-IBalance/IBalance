import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import { VitePWA } from "vite-plugin-pwa";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: "autoUpdate",
      manifest: {
        icons: [
          {
            src: "/icon192x192.png",
            type: "image/png",
            sizes: "192x192",
          },
          {
            src: "/icon512x512.png",
            type: "image/png",
            sizes: "512x512",
          },
          {
            src: "/icon-512-maskable.png",
            type: "image/png",
            sizes: "512x512",
            purpose: "maskable",
          },
        ],
        theme_color: "#fff",
        background_color: "#fff",
        display: "fullscreen",
      },
      workbox: {
        runtimeCaching: [
          {
            urlPattern: ({ url }) => {
              return url.pathname.startsWith("/api");
            },
            handler: "NetworkFirst",
          },
        ],
      },
    }),
  ],
});
