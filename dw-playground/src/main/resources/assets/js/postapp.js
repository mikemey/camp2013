var postModule = angular.module('post-app', []);

postModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/',{ 
    	templateUrl: 'partials/post-list.html',
    	controller: PostListController
    });
//    .otherwise({
//		redirectTo : '/index.html'
//	});
}]);