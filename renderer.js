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

inputField.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') {
    const input = inputField.value.trim();
    handleInput(input);
    inputField.value = '';
  }
});

addOutput('Welcome to Veil of Unfinished Dreams');
addOutput('Type "help" for commands');
