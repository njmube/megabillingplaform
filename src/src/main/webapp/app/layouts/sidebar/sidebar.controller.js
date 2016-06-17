(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('SidebarController', SidebarController);

    SidebarController.$inject = ['$location', '$state', 'Auth', 'Principal', 'ENV', 'LoginService'];

    function SidebarController ($location, $state, Auth, Principal, ENV, LoginService) {
        var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;
        vm.$state = $state;
    }
	
})();
