@echo off

set project_path=../..
set parent_path=../yaoming-parent
set mall_common_path=../yaoming-mall-common

%~d0

cd %~dp0

cd %project_path%
set project_path=%cd%
cd %parent_path%
call mvn clean
call mvn install -Dmaven.test.skip

cd %project_path%
cd %mall_common_path%
call mvn clean
call mvn install -Dmaven.test.skip -Dprofile=shanguoyinyi

cd %project_path%
call mvn clean
call mvn package -Dmaven.test.skip -Dprofile=develop-204

pause