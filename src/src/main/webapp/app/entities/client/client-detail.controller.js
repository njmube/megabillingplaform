(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ClientDetailController', ClientDetailController);

    ClientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Client', 'Client_address'];

    function ClientDetailController($scope, $rootScope, $stateParams, entity, Client, Client_address) {
        var vm = this;
        vm.client = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:clientUpdate', function(event, result) {
            vm.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
