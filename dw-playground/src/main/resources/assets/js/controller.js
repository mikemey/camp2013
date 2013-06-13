function PostListController($http, $scope) {
	$http.get('/service/posts')
		.success(function(data) {
			$scope.posts = data;
	});
}

function PostDeleteController($scope, $location, $routeParams, postService) {
	var id = $routeParams.postId;
	if (!_.isUndefined(id)){
		postService.delete({
			postId : id
		}, function() {
			$location.path('/');
		});
	}
}

function PostCreateController($scope, $location, $routeParams, postService){
	var id = $routeParams.postId;
	if (!_.isUndefined(id)){
		$scope.post = postService.get({
			postId : id
		});
	}
	
	$scope.save = function(post) {
		postService.save(post, function() {
			$location.path('/');
		});
	};
	
	$scope.cancel = function(){
		$location.path('/');
	};

}