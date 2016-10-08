'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ngRoute', 'autocomplete', 'AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/articles', {
        templateUrl: 'articles/layout',
        controller: ArticlesController
    }).when('/login',{
    	templateUrl: 'login',
    	controller: LoginController
    });

    $routeProvider.otherwise({redirectTo: '/login'});
}]);
