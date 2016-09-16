(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Client_addressDetailController', Client_addressDetailController);

    Client_addressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Client_address', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Client_addressDetailController($scope, $rootScope, $stateParams, entity, Client_address, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.client_address = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:client_addressUpdate', function(event, result) {
            vm.client_address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
