# OnlineIDE Project for Advanced Topics of Software Engineering - Group 2-2
This project is a Spring-based web app which can be used to create and compile software

# Current deployment
We have deployed this project to the Google Cloud using the Gitlab CI pipeline to [http://34.107.65.165/](http://34.107.65.165/).

## Dependencies
- Git
- Java JDK >= 11
- Maven
- Docker
- `docker-compose`
- `gcc` version 8 and `javac` for the compiler
- Node / NPM
- Angular (including `cli`) (`npm install --global @angular/cli`)

## Deployment
Note: This deployment has been tested under Ubuntu 20.04.1 LTS. However, it should also work for other distributions.

To run the software clone the repository, package all micro services and run them (`$PROJECT_ROOT` indicates the root of the cloned repository. To set it use e.g. `export PROJECT_ROOT=/root/ase/onlineide`, it has to be an absolute path):
1. Setup the dependencies
2. `git clone https://gitlab.lrz.de/ase20-group2-2/onlineide`
3. Replace the `clientId` and `clientSecret` in `$PROJECT_ROOT/gateway/src/main/resources/application.yml` with your own. In your application make sure that the callback URL is set correctly to ensure that the login works. For example, ours is set to `http://34.107.65.165/login`. The application needs to have at least the `read_user` and `api` scopes.
4. `cd $PROJECT_ROOT/compiler/compiler && mvn package -Dmaven.test.skip=true`
5. `cd $PROJECT_ROOT/dark-mode/darkmode && mvn package -Dmaven.test.skip=true`
6. `cd $PROJECT_ROOT/project/project && mvn package -Dmaven.test.skip=true`
7. `cd $PROJECT_ROOT/eureka && mvn package -Dmaven.test.skip=true`
8. `cd $PROJECT_ROOT/gateway && mvn package -Dmaven.test.skip=true`
9. `cd $PROJECT_ROOT/frontend/frontend/angular-frontend && npm install`
10. `cd $PROJECT_ROOT/frontend/frontend/angular-frontend/src && ng build --base-href=/ui/`
11. `cd $PROJECT_ROOT/frontend/frontend && mvn package -Dmaven.test.skip=true`
12. `cd $PROJECT_ROOT && docker-compose up`

The website should by default be running on `http://localhost:80`

For a detailed deployment see the [.gitlab-ci.yml](.gitlab-ci.yml) file.
