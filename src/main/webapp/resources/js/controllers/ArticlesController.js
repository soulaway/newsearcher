'use strict';

var ArticlesController = function($scope, $http) {
    $scope.articles = {};
    $scope.typeaheadNames = {};

    $scope.fetchNamesList = function() {
        $http.get('articles/getList.json').success(function(list){
            $scope.names = list;
            $scope.search = '';
        });
    };

    $scope.searchNamesList = function(text) {
        $http.post('articles/search.json', text).success(function(list){
            $scope.names = list;
        });
    };

    $scope.autocompleteNames = function(text){
        $http.post('articles/autocomplete.json', text).success(function(list){
            $scope.typeaheadNames = list;
        });
    };

    $scope.addNewName = function(newItem) {
        $scope.resetError();

        $http.post('articles/add', newItem).success(function() {
            $scope.fetchNamesList();
            $scope.name.title = '';
            $scope.name.body = '';
        }).error(function() {
            $scope.setError('Could not add a new name');
        });
    };

    $scope.removeName = function(id) {
        $scope.resetError();

        $http.delete('articles/remove/' + id).success(function() {
            $scope.fetchNamesList();
        }).error(function() {
            $scope.setError('Could not remove article');
        });
        $scope.name.title = '';
        $scope.name.body = '';
    };

    $scope.removeAllNames = function() {
        $scope.resetError();

        $http.delete('articles/removeAll').success(function() {
            $scope.fetchNamesList();
        }).error(function() {
            $scope.setError('Could not remove all articles');
        });
    };

    $scope.resetNamesForm = function() {
        $scope.resetError();
        $scope.names = {};
        $scope.search = '';
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

    $scope.fetchNamesList();

    $scope.predicate = 'id';
};