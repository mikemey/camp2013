function MovieListController($http, $scope) {
//	 delete $http.defaults.headers.common['X-Requested-With'];
	$scope.querychange = function() {
		populateScope($http, $scope, $scope.query);
	};
	populateScope($http, $scope, '');
}

function populateScope($http, $scope, query) {
	$http.get('/service/movies', {
		params : { 'q' : query	}
	}).success(function(data) {
		alert(data);
		$scope.movies = data;
	});
}

function MovieDetailController($scope, $routeParams) {
	$scope.movieId = $routeParams.movieId;
}