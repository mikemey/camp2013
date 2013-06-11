function PostListController($http, $scope) {
	$http.get('/service/posts')
		.success(function(data) {
			$scope.posts = data;
	});
}