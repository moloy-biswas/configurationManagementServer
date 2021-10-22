# configurationManagementServer
Configuration Management Server code

Instruction to Build and Run:
1.	Clean Maven build should be fine to make the build
2.	Refer to pom.xml to know about dependent jars
3.	Configuration Server has been designed to run in poll mode or stand alone mode.
4.	AppLauncher.java is the entry point for this application.
5.	In Poll mode, pass poll mode as true otherwise to retrieve any value keep poll mode false
Sample program argument:
local app1 dev true key1  <store_type> <application_name> <env_type> <poll_mode> <key(optional)>
or
6.	In Stand alone mode, pass poll mode false and pass key and application, env details to retrieve config value as below
local app1 dev false key1  <store_type> <application_name> <env_type> <poll_mode> <key>
7.	Schedule delay has been kep hardcoded but can be made configured.
8.	While retrieving single config, it has been assumed to retrieve config for only single application from any store and env at one point.
9.	While Loading file, it can be more than one application under one env from more than one store can be specified.
10.	Github store testing has been done with single file from repo. Branch name/env name can be configured based on Local store implementation. Due to time constraint, it has not been attempted but will be similar to existing implementation.

  Assumptions or Improvement:
  1. Code is written to make sure it loads configuration for any one of the environment at one point. 
  2. One instannce will run in only one envrironment but it may require config file for multiple applications and multiple sources as on typical Microservice architecture, applications are free to have different source storage.
  3. Logger could have been used but ignored for now.
  4. Actual deployemnt was not done but shown about changed values of config which can be used in application later.
 

Sample Output:

In Poll mode:

vdx.AppLauncher local app1 dev true key1
Successfully connected to Local store
File is modified
Config Values loaded: {app1={key1=value1, key2=value2}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
File is modified
Config Values loaded: {app1={key1=value1, key2=value2, key3=value3}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Successfully connected to Local store
No File is modified since last poll..skipping..
Config Values loaded: {app1={}}
Poller shutdown started
Poller shutdown finished

Process finished with exit code 1
********************************************************************
In Non-Poll mode:
vdx.AppLauncher local app1 dev false key1
Successfully connected to Local store
Configuration value for key key1 is value1
Poller shutdown started
Poller shutdown finished

Process finished with exit code 0
GitHub store:
vdx.AppLauncher git app1 dev false key1
Successfully connected to git store..
File demo-config.properties retrieved from github with download_url https://raw.githubusercontent.com/moloy-biswas/configurationServer/main/demo-config.properties
Content = 
key1=value2
key2 = value1

Configuration value for key key1 is value2
Poller shutdown started
Poller shutdown finished

Process finished with exit code 0

Unit Test evidence:
