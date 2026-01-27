@echo off
echo Compiling Veil of Unfinished Dreams...
javac src\*.java -d .
if %errorlevel% == 0 (
    echo Compilation successful!
    echo Starting the game...
    java VeilOfUnfinishedDreams
) else (
    echo Compilation failed!
    pause
)