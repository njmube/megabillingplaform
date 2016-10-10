(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDeleteController',Taxpayer_clientDeleteController);

    Taxpayer_clientDeleteController.$inject = ['$uibModalInstance', 'entity', 'Client_address', 'Taxpayer_client'];

    function Taxpayer_clientDeleteController($uibModalInstance, entity, Client_address, Taxpayer_client) {
        var vm = this;

        vm.taxpayer_client = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Client_address.delete({id: vm.taxpayer_client.client_address.id});
            Taxpayer_client.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
