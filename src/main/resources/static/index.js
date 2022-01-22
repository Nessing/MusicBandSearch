(function ($localStorage) {
    'use strict';

    angular
      .module('app', ['ngRoute', 'ngStorage'])
      .config(config)
      .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    const contextPath = 'http://localhost:8080';

    function run($rootScope,$http,$localStorage){
        if($localStorage.currentUser){
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {

    const contextPath = 'http://localhost:8080';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth/login', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = response.data.token;
                    $localStorage.currentUser = {email: $scope.user.email, token: response.data.token};
                    $scope.currentUserName = $scope.user.email;
                    $scope.user.email = null;
                    $scope.user.password = null;
                    $http.get(contextPath + "/auth/my_hh_token")
                            .then(function(response){
                                $localStorage.hh_access_token = response.data.accessToken;
                            });
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $location.path('/');
        if ($scope.user.email) {
            $scope.user.email = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        delete $localStorage.hh_access_token;
        delete $localStorage.id_summary_array;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };
});