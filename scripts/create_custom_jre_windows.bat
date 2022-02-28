@echo off

set _PATH_TO_JLINK=C:\Program Files\Java\jdk-17.0.2\bin\jlink
set _PATH_TO_JMODS=C:\Program Files\Java\javafx-jmods-17.0.2

set _MODULE_NAME=HashTools

set _SOURCE_DIRECTORY=classes
set _TARGET_DIRECTORY=hashtools-windows

set _LAUNCHER_NAME=hashtools.bat
set _MAIN_CLASS=hashtools.main.Main

cls

"%_PATH_TO_JLINK%" ^
  "--no-header-files" ^
  "--no-man-pages" ^
  "--strip-debug" ^
  "--compress=2" ^
  "--module-path" "%_SOURCE_DIRECTORY%;%_PATH_TO_JMODS%" ^
  "--add-modules" "%_MODULE_NAME%" ^
  "--launcher" "%_LAUNCHER_NAME%=%_MODULE_NAME%/%_MAIN_CLASS%" ^
  "--output" "%_TARGET_DIRECTORY%"

del "%_TARGET_DIRECTORY%\bin\%_LAUNCHER_NAME%"
