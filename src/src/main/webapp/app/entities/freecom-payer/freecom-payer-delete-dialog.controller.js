(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_payerDeleteController',Freecom_payerDeleteController);

    Freecom_payerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_payer'];

    function Freecom_payerDeleteController($uibModalInstance, entity, Freecom_payer) {
        var vm = this;

        vm.freecom_payer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_payer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
