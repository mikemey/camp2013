function PostListController($http, $scope) {
	$http.get('/service/posts')
		.success(function(data) {
			$scope.posts = data;
	});
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