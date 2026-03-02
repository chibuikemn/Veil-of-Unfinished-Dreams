const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('game', {
  sendCommand: (command) => ipcRenderer.send('game-command', command),
  onOutput: (callback) => ipcRenderer.on('game-output', (event, data) => callback(data)),
  onThemeChange: (callback) => ipcRenderer.on('theme-changed', (event, theme) => callback(theme))
});
