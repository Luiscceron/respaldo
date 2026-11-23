@echo off
setlocal
cd /d "%~dp0"
set "JAVA_HOME=C:\jdk-25.0.5.1+2"
set "PATH=%JAVA_HOME%\bin;C:\javafx-sdk-25.0.5\bin;%PATH%"

echo Iniciando HotelManager...
"%JAVA_HOME%\bin\java.exe" --module-path "dist\HotelManager.jar;C:\javafx-sdk-25.0.5\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -m HotelManager/hotelmanager.HotelManager %*
