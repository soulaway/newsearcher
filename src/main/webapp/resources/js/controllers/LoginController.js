'use strict';

var LoginController = function($scope, $http) {
    $scope.user = {};

    $scope.login = function(text) {
        $http.post('login/user.json', text).success(function(text){
            $scope.user = text;
        }).error(function() {
            $scope.setError('Could not login a user');
        });
    };
    
    $scope.register = function(text) {
        $http.post('login/register.json', text).success(function(text){
            $scope.user = text;
        }).error(function() {
            $scope.setError('Could not register a user');
        });
    };
    
    $scope.resetLoginForm = function() {
        $scope.resetError();
        $scope.user = {};
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };
};