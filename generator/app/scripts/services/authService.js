'use strict';
define(['productSeek', 'services/sessionService', 'services/restService'], function(productSeek) {


	return productSeek.factory('authService', ['$http', 'url', 'sessionService', '$q', '$rootScope', 'restService', function($http, url, session, $q, $rootScope, restService) {
		var AuthService = {};
		AuthService.loggedUser = session.getUser();

		AuthService.logIn = function(username, password, saveToSession) {
			var credentials = { j_username: username, j_password: password };
			var self = this;
			return $http({
					method: 'POST',
					url: url + '/login',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					transformRequest: function(obj) {
						var str = [];
						for (var p in obj)
							str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data: {j_username: username, j_password: password}
				})
				.then(function(response) {
                    session.setAccessToken(response.headers('X-AUTH-TOKEN'), saveToSession);
                    return $http.get(url + '/user', {headers: {'X-AUTH-TOKEN': session.getAccessToken()}});
                })
				.then(function(response) {
                    return response.data;
                })
				.then(function(data) {
                    session.setUser(data, saveToSession);
                    self.loggedUser = data;
                    console.log(data);                
                    $rootScope.$broadcast('user:updated');
                    return data;
                })
				.catch(function(response) {
                    self.logOut();
                    return $q.reject(response.data);
                });
		};

		AuthService.isLoggedIn = function() {
			return !!this.loggedUser;
		};

		AuthService.logOut = function() {
			session.destroy();
			this.loggedUser = null;
            $rootScope.$broadcast('user:updated');
		};

		AuthService.getLoggedUser = function() {
			return this.loggedUser;
		}

		return AuthService;
	}]);

});