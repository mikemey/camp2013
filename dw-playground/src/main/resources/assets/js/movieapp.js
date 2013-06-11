var movieModul = angular.module('movie-app', []);

movieModul.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/movies',{ 
    	templateUrl: 'partials/movie-list.html',
    	controller: MovieListController
    }).when('/movies/:phoneId', {
    	templateUrl: 'partials/movie-detail.html', 
    	controller: MovieDetailController
    }).otherwise({redirectTo: '/movies'});
}]);