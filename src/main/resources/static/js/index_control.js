(function ($localStorage) {
    'use strict';

    angular
      .module('app', ['ngRoute', 'ngStorage'])
      .config(config)
      .run(run);

    function config($routeProvider, $httpProvider) {
    }

    const contextPath = 'http://localhost:5555';

    function run($rootScope,$http,$localStorage){
        if($localStorage.currentUser){
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8080/api/v1';

    $scope.login_in_scripts = function () {
        var login = document.getElementById('login_input').value;
        var pass = document.getElementById('pass_input').value;
        var authDto = {
            email: login,
            password: pass
        };
        $http({
            url: contextPath + '/auth',
            method: 'POST',
            data: {
                "email": login,
                "password": pass
            }
              })
            .then(function (response) {
                if(response.data.token === null)
                    window.location.replace(contextPath + "/login");
                else{
                    $http.defaults.headers.common.Authorization = response.data.token;
                    $localStorage.currentUser = {email: $scope.user.email, token: response.data.token};
                }

            });
    };
});