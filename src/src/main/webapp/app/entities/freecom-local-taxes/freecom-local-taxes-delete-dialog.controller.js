(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_taxesDeleteController',Freecom_local_taxesDeleteController);

    Freecom_local_taxesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_local_taxes'];

    function Freecom_local_taxesDeleteController($uibModalInstance, entity, Freecom_local_taxes) {
        var vm = this;

        vm.freecom_local_taxes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_local_taxes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
