(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Client_addressDeleteController',Client_addressDeleteController);

    Client_addressDeleteController.$inject = ['$uibModalInstance', 'entity', 'Client_address'];

    function Client_addressDeleteController($uibModalInstance, entity, Client_address) {
        var vm = this;

        vm.client_address = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Client_address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
