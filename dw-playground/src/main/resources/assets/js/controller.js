function PostListController($http, $scope) {
	$http.get('/service/posts')
		.success(function(data) {
			$scope.posts = data;
	});
}

function PostCreateController($http, $scope, $location, postService){
	$scope.save = function(post) {
		postService.save(post, function() {
			$location.path('/');
		});
	};
//	var id = $routeParams.postId;
//	if (!_.isUndefined(id))
//		$scope.post = postService.get({
//			postId : id
//		});

}