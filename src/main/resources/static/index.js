angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/mbs';

    $scope.init = function () {
        $http.get(contextPath + '/api/v1/users')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.init();
});