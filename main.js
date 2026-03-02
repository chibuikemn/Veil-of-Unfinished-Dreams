const { app, BrowserWindow, Menu } = require('electron');
const path = require('path');

let mainWindow;
let isDarkTheme = true;

app.on('ready', () => {
  mainWindow = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js')
    }
  });

  mainWindow.loadFile('index.html');
  createMenu();
});

function createMenu() {
  const template = [
    {
      label: 'File',
      submenu: [
        {
          label: 'Exit',
          click: () => app.quit()
        }
      ]
    },
    {
      label: 'Theme',
      submenu: [
        {
          label: 'Dark',
          click: () => mainWindow.webContents.send('theme-changed', 'dark')
        },
        {
          label: 'Light',
          click: () => mainWindow.webContents.send('theme-changed', 'light')
        },
        {
          label: 'Medieval',
          click: () => mainWindow.webContents.send('theme-changed', 'medieval')
        },
        {
          label: 'Sci-Fi',
          click: () => mainWindow.webContents.send('theme-changed', 'scifi')
        }
      ]
    }
  ];

  const menu = Menu.buildFromTemplate(template);
  Menu.setApplicationMenu(menu);
}

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});
