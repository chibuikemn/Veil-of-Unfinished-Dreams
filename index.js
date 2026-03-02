<!DOCTYPE html>
<html>
<head>
  <title>Text Adventure</title>
</head>
<body>
  <h1>Text Adventure Game</h1>
  <div id="output"></div>
  <input id="input" type="text" placeholder="Type command..." />
  
  <script>
    const input = document.getElementById("input");
    const output = document.getElementById("output");

    input.addEventListener("keydown", function(event) {
      if (event.key === "Enter") {
        const command = input.value;
        output.innerHTML += "<p>> " + command + "</p>";
        input.value = "";
      }
    });
  </script>
</body>
</html>