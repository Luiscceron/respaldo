@echo off
setlocal
cd /d "%~dp0"
set "JAVA_HOME=C:\jdk-25.0.5.1+2"
set "ANT_HOME=C:\apache-ant-1.10.18"
set "PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;C:\javafx-sdk-25.0.5\bin;%PATH%"

echo Compilando HotelManager...
call "%ANT_HOME%\bin\ant.bat" jar
if errorlevel 1 (
    echo Error durante la compilacion.
    pause
    exit /b %errorlevel%
)

echo Iniciando HotelManager...
"%JAVA_HOME%\bin\java.exe" --module-path "dist\HotelManager.jar;C:\javafx-sdk-25.0.5\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -m HotelManager/hotelmanager.HotelManager %*
