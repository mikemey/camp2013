var postModule = angular.module('library', ['library.services']);

postModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/',{ 
    	templateUrl: 'partials/post-list.html',
    	controller: PostListController
    })
    .when('/posts/create',{
    	templateUrl: 'partials/post-create.html',
    	controller: PostCreateController
    });
//    .otherwise({
//		redirectTo : '/index.html'
//	});
}]);