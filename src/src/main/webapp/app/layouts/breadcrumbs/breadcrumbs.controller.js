(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('BreadcrumbsController', BreadcrumbsController);

    BreadcrumbsController.$inject = ['$location', '$state', 'Auth', 'Principal', 'ENV', 'LoginService'];

    function BreadcrumbsController ($location, $state, Auth, Principal, ENV, LoginService) {
        var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;
        vm.$state = $state;
    }
	
})();
