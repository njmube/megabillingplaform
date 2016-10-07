(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDetailController', Taxpayer_clientDetailController);

    Taxpayer_clientDetailController.$inject = ['$scope', '$rootScope', '$uibModalInstance', '$stateParams', 'entity', 'Taxpayer_client', 'Client_address', 'Taxpayer_account'];

    function Taxpayer_clientDetailController($scope, $rootScope, $uibModalInstance, $stateParams, entity, Taxpayer_client, Client_address, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_client = entity;
        vm.client_address = vm.taxpayer_client.client_address;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_clientUpdate', function(event, result) {
            vm.taxpayer_client = result;
        });

        $scope.$on('$destroy', unsubscribe);

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
