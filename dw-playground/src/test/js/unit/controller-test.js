describe("Controller Tests", function() {
	
	var posts = [ {
		id : 1,
		title : "First post",
		content : "Content of first post"
	}, {
		id : 2,
		title : "Second post",
		content : "Content of second post"
	} ];
	
	describe("PostCreateController", function(){
		
		var $scope = null;
		var $httpBackend = null;
		var $controller = null;

		beforeEach(module('library.services'));

		beforeEach(inject(function($rootScope, _$controller_, _$httpBackend_) {
			$scope = $rootScope.$new();
			$httpBackend = _$httpBackend_;
			$controller = _$controller_;
		}));
		
		beforeEach(function() {
			this.addMatchers({
				// we need to use toEqualData because the Resource hase extra
				// properties which make simple .toEqual not work.
				toEqualData : function(expect) {
					return angular.equals(expect, this.actual);
				}
			});
		});

		
		it ('shows all posts', function() {
			$httpBackend.expectGET('/service/posts').respond( posts );

			$controller(PostListController, {
				$scope : $scope
			});

			expect($scope.posts).not.toBeDefined();

			$httpBackend.flush();

			expect($scope.posts).toEqualData( posts );

		});
		
	});
	
});