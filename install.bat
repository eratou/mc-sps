rem https://github.com/bell-sw/Liberica/releases/download/17.0.3.1%2B2/bellsoft-jdk17.0.3.1+2-windows-amd64.zip
SET PATH=%~dp0jdk-17.0.3.1\bin;%PATH%
call gradlew genEclipseRuns eclipse
pause