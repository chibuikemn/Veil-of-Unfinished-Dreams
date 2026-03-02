const outputDiv = document.getElementById('output');
const inputField = document.getElementById('input');
let running = true;

function addOutput(text) {
  const line = document.createElement('div');
  line.textContent = text;
  outputDiv.appendChild(line);
  outputDiv.scrollTop = outputDiv.scrollHeight;
}

function handleInput(input) {
  if (!input) {
    return;
  }

  addOutput('> ' + input);

  switch (input.toLowerCase()) {
    case 'quit':
    case 'exit':
      running = false;
      addOutput('Goodbye.');
      break;
    case 'help':
      addOutput('Commands: help, quit');
      break;
    default:
      addOutput('You said: ' + input);
  }
}

function setTheme(theme) {
  const body = document.body;
  const vines = document.getElementById('vines');
  switch(theme) {
    case 'light':
      body.style.background = 'linear-gradient(135deg, #f5f5f5 0%, #e8e8e8 50%, #f0f0f0 100%)';
      document.documentElement.style.setProperty('--font-family', "'Georgia', serif");
      document.documentElement.style.setProperty('--text-color', '#333');
      document.documentElement.style.setProperty('--border-color', '#999');
      document.documentElement.style.setProperty('--bg-color', 'rgba(255, 255, 255, 0.9)');
      vines.classList.remove('show');
      break;
    case 'medieval':
      body.style.background = 'linear-gradient(135deg, #3d2817 0%, #5c4033 50%, #4a3728 100%)';
      document.documentElement.style.setProperty('--font-family', "'Cinzel', serif");
      document.documentElement.style.setProperty('--text-color', '#d4af37');
      document.documentElement.style.setProperty('--border-color', '#8b7355');
      document.documentElement.style.setProperty('--bg-color', 'rgba(61, 40, 23, 0.9)');
      vines.classList.add('show');
      break;
    case 'scifi':
      body.style.background = 'linear-gradient(135deg, #0a1428 0%, #1a3a52 50%, #0d2438 100%)';
      document.documentElement.style.setProperty('--font-family', "'Space Mono', monospace");
      document.documentElement.style.setProperty('--text-color', '#00ff88');
      document.documentElement.style.setProperty('--border-color', '#00ccff');
      document.documentElement.style.setProperty('--bg-color', 'rgba(10, 20, 40, 0.9)');
      vines.classList.remove('show');
      break;
    default:
      body.style.background = 'linear-gradient(135deg, #0a0e27 0%, #1a0033 50%, #0d1b2a 100%)';
      document.documentElement.style.setProperty('--font-family', "'Georgia', serif");
      document.documentElement.style.setProperty('--text-color', '#d8a5ff');
      document.documentElement.style.setProperty('--border-color', '#8a2be2');
      document.documentElement.style.setProperty('--bg-color', 'rgba(10, 14, 39, 0.8)');
      vines.classList.remove('show');
  }
}

inputField.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') {
    const input = inputField.value.trim();
    handleInput(input);
    inputField.value = '';
  }
});

window.game.onThemeChange((theme) => setTheme(theme));

addOutput('Welcome to Veil of Unfinished Dreams');
addOutput('Type "help" for commands');
