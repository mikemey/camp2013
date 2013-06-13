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
	
	describe("PostListController", function(){
		
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
	
	describe("PostCreateController", function(){
		
		it ('creating new post and return to home page', inject(function($location) {
			spyOn($location, 'path');
			
			$httpBackend.expectPOST('service/posts').respond({});

			$controller(PostCreateController, { 
				$scope: $scope, 
				$routeParams: {}
			});

			$scope.save({
				title : "Title",
				content : "Content",
			});
			
			$httpBackend.flush();
			
			expect($location.path).toHaveBeenCalledWith('/');
			
		}));

		it ('editing existing post', inject(function($location) {
			spyOn($location, 'path');
			
//			var editingPost = posts.get(0);
			$httpBackend.expectGET('/service/posts/1').respond({ posts[0] });
			$httpBackend.expectPOST('/service/posts/1').respond({});
			
			$controller(PostCreateController, { 
				$scope: $scope, 
				$routeParams: { postId : 1 }
			});
			
			$scope.save({
				title : 'edited',
				content : 'edited',
			});
			
			$httpBackend.flush();
			
			expect($location.path).toHaveBeenCalledWith('/');
			
		}));
		
	});
	
	
});