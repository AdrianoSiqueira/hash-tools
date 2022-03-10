#!/usr/bin/env bash

_PATH_TO_JLINK="/usr/lib/jvm/jdk-17.0.2/bin/jlink"
_PATH_TO_JMODS="/usr/lib/jvm/javafx-jmods-17.0.2"

_MODULE_NAME="HashTools"

_SOURCE_DIRECTORY="classes"
_TARGET_DIRECTORY="hashtools-linux"

_LAUNCHER_NAME="hashtools.sh"
_MAIN_CLASS="hashtools.main.Main"

clear

"${_PATH_TO_JLINK}" \
  "--no-header-files" \
  "--no-man-pages" \
  "--strip-debug" \
  "--compress=2" \
  "--module-path" "${_SOURCE_DIRECTORY}:${_PATH_TO_JMODS}" \
  "--add-modules" "${_MODULE_NAME}" \
  "--launcher" "${_LAUNCHER_NAME}=${_MODULE_NAME}/${_MAIN_CLASS}" \
  "--output" "${_TARGET_DIRECTORY}"
