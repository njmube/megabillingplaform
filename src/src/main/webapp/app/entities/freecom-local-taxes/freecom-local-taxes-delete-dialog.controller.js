(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_taxesDeleteController',Freecom_local_taxesDeleteController);

    Freecom_local_taxesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_local_taxes'];

    function Freecom_local_taxesDeleteController($uibModalInstance, entity, Freecom_local_taxes) {
        var vm = this;
        vm.freecom_local_taxes = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_local_taxes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
