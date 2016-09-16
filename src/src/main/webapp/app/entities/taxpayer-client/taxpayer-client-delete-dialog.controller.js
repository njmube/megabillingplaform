(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDeleteController',Taxpayer_clientDeleteController);

    Taxpayer_clientDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_client'];

    function Taxpayer_clientDeleteController($uibModalInstance, entity, Taxpayer_client) {
        var vm = this;

        vm.taxpayer_client = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_client.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
