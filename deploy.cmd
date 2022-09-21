@echo off



set APP_NAME=demo
set DEPLOY_NAME=demo



set TOMCATFARM_HOME=c:\DEV\TomcatFarms
set SERVER_NAME1=tom4-9-dev



set TOMCATFARM_WEBAPPS1=%TOMCATFARM_HOME%\domain\%SERVER_NAME1%\webapps



set APP_HOME=%TOMCATFARM_HOME%\applications



set DEPLOY_PROP=1
set MAKE_LINK=0



set "CURRENT_DIR=%~dp0"



xml sel -T -N x="http://maven.apache.org/POM/4.0.0" -t -v /x:project/x:version pom.xml > tmpFile
SET /P APP_VERSION= < tmpFile



for %%A in (%*) DO (
    if ""%%A""==""link"" set MAKE_LINK=1
    if ""%%A""==""prop"" set DEPLOY_PROP=1
)



echo [INFO] MAKE_LINK=%MAKE_LINK%
echo [INFO] DEPLOY_PROP=%DEPLOY_PROP%




IF %MAKE_LINK%==0 GOTO nomakelink



:makelink



echo [INFO] --- Make link ---



cd %APP_HOME%
rmdir %APP_NAME%
mkdir %APP_NAME%-%APP_VERSION%
mklink /D %DEPLOY_NAME% %APP_NAME%-%APP_VERSION%



:nomakelink



if %DEPLOY_PROP%==0 GOTO noextractprop



:extractprop
cd %CURRENT_DIR%
echo [INFO] --- Extract properties ---
unzip -o target\GT-WAR-%APP_NAME%-properties-%APP_VERSION%.zip -d %APP_HOME%\%DEPLOY_NAME%\



:noextractprop



@echo on



copy /Y target\GT-WAR-%APP_NAME%-%APP_VERSION%.war %TOMCATFARM_WEBAPPS1%\%DEPLOY_NAME%.war



time /T